package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import java.util.Arrays;
import java.util.Random;

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
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                if(count == 0){
                    continue;
                }

                NumberCell numberCell = new NumberCell(count);
                board[row][col] = numberCell;

            }
        }
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return findCell(selectedRowIndex, selectedColIndex).isLandMine();
    }

    private int countNearbyLandMines(int row, int col) {
        int count = 0;
        int rowSize = getLowSize();
        int colSize = getColSize();

        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1,col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell( row - 1,col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1,col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row,col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell( row,col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1,col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell( row + 1,col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell( row + 1,col + 1)) {
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
        Cell cell = findCell(selectedRowIndex,selectedColIndex);
        cell.flag();
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

            if (isLandMineCell(row,col)) {
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