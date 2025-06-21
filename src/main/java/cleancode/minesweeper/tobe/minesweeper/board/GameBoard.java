package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.board.cell.Cell;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.Cells;
import cleancode.minesweeper.tobe.minesweeper.board.cell.EmptyCell;
import cleancode.minesweeper.tobe.minesweeper.board.cell.LandMineCell;
import cleancode.minesweeper.tobe.minesweeper.board.cell.NumberCell;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;
import java.util.List;
import java.util.Stack;

public class GameBoard {


    private final Cell[][] board;
    private final int landMineCount;
    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel){

        board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];

        this.landMineCount = gameLevel.getLandMineCount();

        this.gameStatus = GameStatus.IN_PROGRESS;
    }

    public void initializeGame() {

        initializeGameStatus();
        CellPositions cellPositions = CellPositions.from(board);

        initializeEmptyCells(cellPositions);

        List<CellPosition> landMineCellPositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMineCellPositions);

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(cellPositions.getPositions(),landMineCellPositions);
        initializeNumberCells(numberPositionCandidates);

    }

    private void initializeGameStatus() {
        gameStatus = GameStatus.IN_PROGRESS;
    }

    private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
        for(CellPosition cellPosition : numberPositionCandidates){
            long count = countNearbyLandMines(cellPosition);
            if(count == 0){
                continue;
            }
            updateCellAt(cellPosition,new NumberCell((int)count));
        }
    }

    private void initializeLandMineCells(List<CellPosition> landMineCellPositions) {
        for (CellPosition cellPosition : landMineCellPositions) {
            updateCellAt(cellPosition, new LandMineCell());
        }
    }

    private void initializeEmptyCells(CellPositions cellPositions) {
        List<CellPosition> allPositions = cellPositions.getPositions();
        for (CellPosition cellPosition : allPositions) {
            updateCellAt(cellPosition, new EmptyCell());
        }
    }

    private void updateCellAt(CellPosition cellPosition,Cell cell) {
        board[cellPosition.getRowIndex()][cellPosition.getColIndex()] = cell;
    }

    public boolean isLandMineCellAt(CellPosition cellPosition) {
        return findCell(cellPosition).isLandMine();
    }

    private long countNearbyLandMines(CellPosition cellPosition) {
        long count = 0;
        int rowSize = getLowSize();
        int colSize = getColSize();

        count = calculateSurroundedPosition(cellPosition, rowSize, colSize)
                .stream()
                .filter(this::isLandMineCellAt)
                .count();

        return count;
    }

    private List<CellPosition> calculateSurroundedPosition(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThen(rowSize))
                .filter(position -> position.isColIndexLessThen(colSize))
                .toList();
    }

    public int getLowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public CellSnapshot getSnapShot(CellPosition cellPosition){
        Cell cell = findCell(cellPosition);
        return cell.getSnapShot();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();

        checkIfGameIsOver();

    }

    public void checkIfGameIsOver() {
        if (isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = GameStatus.WIN;
    }

    public void openOneCellAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    public boolean isAllCellChecked() {
        Cells cells = Cells.from(board);

        return cells.isAllChecked();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

    public boolean isInvalidCellPosition(CellPosition cellPositionFromUser) {

        int rowSize = getLowSize();
        int colSize = getColSize();

        return cellPositionFromUser.isRowIndexMoreThanOrEqual(rowSize)
                || cellPositionFromUser.isColIndexMoreThanOrEqual(colSize);

    }
    
    private void openSurroundedCell(CellPosition cellPosition) {
        Stack<CellPosition> stack = new Stack<>();
        stack.push(cellPosition);

        while (!stack.isEmpty()) {
            OpenAndPushCellAt(stack);
        }
    }

    private void OpenAndPushCellAt(Stack<CellPosition> stack) {

        CellPosition cellPosition = stack.pop();

        if (isOpenedCell(cellPosition)) {
            return;
        }

        if (isLandMineCellAt(cellPosition)) {
            return;
        }

        openOneCellAt(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
            // (지뢰찾기 게임에서 숫자가 있는 칸을 열면 그 칸만 열리고 주변 빈 칸은 더 이상 열리지 않음)
        }

        List<CellPosition> surroundedPositions = calculateSurroundedPosition(cellPosition, getLowSize(), getColSize());
        surroundedPositions
                .forEach(this::openSurroundedCell);

        surroundedPositions.forEach(stack::push);

    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }


    public boolean isInProgress() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }

    public void openAt(CellPosition cellPosition) {

        if (isLandMineCellAt(cellPosition)) {
            openOneCellAt(cellPosition);
            changeGameStatusToLose();
            return;
        }

        openSurroundedCell(cellPosition);
        checkIfGameIsOver();
    }

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }

    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;

    }

    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }
}