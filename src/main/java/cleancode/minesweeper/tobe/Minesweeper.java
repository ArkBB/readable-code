package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import java.util.Arrays;
import java.util.Random;

public class Minesweeper {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final int LAND_MINE_COUNT = 10;
    private Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];

    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public void run() {

        consoleOutputHandler.showGameStartComments();
        initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(BOARD);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameClear();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameOver();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (GameException e) {
                consoleOutputHandler.printErrMessage(e);

            } catch (Exception e) {
                consoleOutputHandler.printMessage("프로그램에 문제가 생겼습니다.");
                e.printStackTrace();
            }
        }
    }



    private void actOnCell(String input, String userActionInput) {
        int selectedColIndex = extractSelectedColIndex(input);
        int selectedRowIndex = extractSelectedRowIndex(input);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex].flag();
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedColIndex, selectedRowIndex)) {
                BOARD[selectedRowIndex][selectedColIndex].open();
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
        }

    }


    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean isLandMineCell(int selectedColIndex, int selectedRowIndex) {
        return BOARD[selectedRowIndex][selectedColIndex].isLandMine();
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private String getActionInputFromUser() {
        consoleOutputHandler.printUserActionInputComment();
        return consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCoordinateInputComment();
        return consoleInputHandler.getUserInput();
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private void checkIfGameIsOver() {
        boolean isAllChecked = isAllCellChecked();
        if (isAllChecked) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private boolean isAllCellChecked() {
        return Arrays.stream(BOARD)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    private int convertRowFrom(char cellInputRow) {
        int selectedRowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (selectedRowIndex >= BOARD_ROW_SIZE) {
            throw new IllegalArgumentException("잘못된 ROW 입력입니다.");
        }

        return selectedRowIndex;
    }

    private int extractSelectedColIndex(String input) {
        return convertColFrom(input.charAt(0));
    }

    private int extractSelectedRowIndex(String input) {
        return convertRowFrom(input.charAt(1));
    }

    private int convertColFrom(char cellInputCol) {
        int selectedColIndex;
        switch (cellInputCol) {
            case 'a':
                selectedColIndex = 0;
                break;
            case 'b':
                selectedColIndex = 1;
                break;
            case 'c':
                selectedColIndex = 2;
                break;
            case 'd':
                selectedColIndex = 3;
                break;
            case 'e':
                selectedColIndex = 4;
                break;
            case 'f':
                selectedColIndex = 5;
                break;
            case 'g':
                selectedColIndex = 6;
                break;
            case 'h':
                selectedColIndex = 7;
                break;
            case 'i':
                selectedColIndex = 8;
                break;
            case 'j':
                selectedColIndex = 9;
                break;
            default:
                throw new IllegalArgumentException("잘못된 COL 입력입니다.");
        }
        return selectedColIndex;
    }



    private void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            BOARD[row][col].turnOnLandMine();
        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                if (isLandMineCell(col, row)) {
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                BOARD[row][col].updateNearbyLandMineCount(count);
            }
        }
    }

    private int countNearbyLandMines(int row, int col) {
        int count = 0;

        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(col - 1, row - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(col, row - 1)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && isLandMineCell(col + 1, row - 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(col - 1, row)) {
            count++;
        }
        if (col + 1 < BOARD_COL_SIZE && isLandMineCell(col + 1, row)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col - 1 >= 0 && isLandMineCell(col - 1, row + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && isLandMineCell(col, row + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col + 1 < BOARD_COL_SIZE && isLandMineCell(col + 1, row + 1)) {
            count++;
        }
        return count;
    }

    private void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        if (BOARD[row][col].isOpened()) {
            return;
        }

        if (isLandMineCell(col, row)) {
            return;
        }

        BOARD[row][col].open();

        if (BOARD[row][col].hasLandMineNearBy()) {
            return;
        }

        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}