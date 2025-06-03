package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.Cells;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;
import java.util.List;

public class GameBoard {


    private Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel){

        board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];

        this.landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {

        CellPositions cellPositions = CellPositions.from(board);

        initializeEmptyCells(cellPositions);

        List<CellPosition> landMineCellPositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMineCellPositions);

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(cellPositions.getPositions(),landMineCellPositions);
        initializeNumberCells(numberPositionCandidates);

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

    public String getSign(CellPosition cellPosition) {

        Cell cell = findCell(cellPosition);
        return cell.getSign();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public void openAt(CellPosition cellPosition) {
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

    public void openSurroundedCell(CellPosition cellPosition) {

            if (isOpenedCell(cellPosition)) {
                return;
            }

            if (isLandMineCellAt(cellPosition)) {
                return;
            }

            openAt(cellPosition);

            if (doesCellHaveLandMineCount(cellPosition)) {
                return;
                // (지뢰찾기 게임에서 숫자가 있는 칸을 열면 그 칸만 열리고 주변 빈 칸은 더 이상 열리지 않음)
            }

        List<CellPosition> surroundedPositions = calculateSurroundedPosition(cellPosition, getLowSize(), getColSize());
        surroundedPositions
                    .forEach(this::openSurroundedCell);


    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }


}