package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    public static final int LAND_MINE_COUNT = 10;

    private Cell[][] board;

    public GameBoard(int rowSize, int colSize){
        board = new Cell[rowSize][colSize];
    }

    public void initializeGame() {

        int rowSize = board.length;
        int colSize =board[0].length;

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            findCell(landMineRow, landMineCol).turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (isLandMineCell(col, row)) {
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                findCell(row, col).updateNearbyLandMineCount(count);
            }
        }
    }

    public boolean isLandMineCell(int selectedColIndex, int selectedRowIndex) {
        return findCell(selectedRowIndex, selectedColIndex).isLandMine();
    }

    private int countNearbyLandMines(int row, int col) {
        int count = 0;
        int rowSize = getLowSize();
        int colSize = getColSize();

        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(col - 1, row - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(col, row - 1)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(col + 1, row - 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(col - 1, row)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell(col + 1, row)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(col - 1, row + 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(col, row + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(col + 1, row + 1)) {
            count++;
        }
        return count;
    }

    public int getLowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(int rowIndex, int colIndex) {

        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public void flag(int selectedRowIndex, int selectedColIndex) {
        Cell flagCell = findCell(selectedRowIndex,selectedColIndex);
        flagCell.flag();
    }

    public void open(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex,selectedColIndex);
        cell.open();
    }

    public boolean isAllCellChecked() {
            return Arrays.stream(board)
                    .flatMap(Arrays::stream)
                    .allMatch(Cell::isChecked);
    }
    
    public void openSurroundedCell(int row, int col) {

            if (row < 0 || row >= getLowSize() || col < 0 || col >= getColSize()) {
                return;
            }
            if (isOpenedCell(row, col)) {
                return;
            }

            if (isLandMineCell(row, col)) {
                return;
            }

            open(row,col);

            if (doesCellHaveLandMineCount(row, col)) {
                return;
                // (지뢰찾기 게임에서 숫자가 있는 칸을 열면 그 칸만 열리고 주변 빈 칸은 더 이상 열리지 않음)
            }

            openSurroundedCell(row - 1, col - 1);
            openSurroundedCell(row - 1, col);
            openSurroundedCell(row - 1, col + 1);
            openSurroundedCell(row, col - 1);
            openSurroundedCell(row, col + 1);
            openSurroundedCell(row + 1, col - 1);
            openSurroundedCell(row + 1, col);
            openSurroundedCell(row + 1, col + 1);

    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }
}
