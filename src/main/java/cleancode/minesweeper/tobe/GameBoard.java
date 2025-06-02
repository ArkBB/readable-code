package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.RelativePosition;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class GameBoard {


    private Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel){

        board = new Cell[gameLevel.getRowSize()][gameLevel.getColSize()];

        this.landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {

        int rowSize = getLowSize();
        int colSize = getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = new EmptyCell();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);

            LandMineCell landMineCell = new LandMineCell();
            board[landMineRow][landMineCol] = landMineCell;
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {

                CellPosition cellPosition = CellPosition.of(row, col);

                if (isLandMineCellAt(cellPosition)) {
                    continue;
                }
                long count = countNearbyLandMines(cellPosition);
                if(count == 0){
                    continue;
                }

                NumberCell numberCell = new NumberCell((int)count);
                board[row][col] = numberCell;

            }
        }
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

//        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCellAt(row - 1,col - 1)) {
//            count++;
//        }
//        if (row - 1 >= 0 && isLandMineCellAt( row - 1,col)) {
//            count++;
//        }
//        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCellAt(row - 1,col + 1)) {
//            count++;
//        }
//        if (col - 1 >= 0 && isLandMineCellAt(row,col - 1)) {
//            count++;
//        }
//        if (col + 1 < colSize && isLandMineCellAt( row,col + 1)) {
//            count++;
//        }
//        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCellAt(row + 1,col - 1)) {
//            count++;
//        }
//        if (row + 1 < rowSize && isLandMineCellAt( row + 1,col)) {
//            count++;
//        }
//        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCellAt( row + 1,col + 1)) {
//            count++;
//        }
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
            return Arrays.stream(board)
                    .flatMap(Arrays::stream)
                    .allMatch(Cell::isChecked);
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

//            for (RelativePosition relativePosition : RelativePosition.SURROUNDED_POSITIONS) {
//                if (cellPosition.canCalculatePositionBy(relativePosition)) {
//                    CellPosition newCellPosition = cellPosition.calculatePositionBy(relativePosition);
//                    openSurroundedCell(newCellPosition);
//                }
//            }

    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }


}