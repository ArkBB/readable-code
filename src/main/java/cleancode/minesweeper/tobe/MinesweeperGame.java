package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final int LAND_MINE_COUNT = 10;
    public static final String CLOSED_CELL_SIGN = "■";
    public static final String OPENED_CELL_SIGN = "□";
    public static final String FLAG_SIGN = "⚑";
    public static final String LANDMINE_SIGN = "☼";
    public static final Scanner SCANNER = new Scanner(System.in);
    private static Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static boolean[][] landMines = new boolean[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배


    public static void main(String[] args) {
        showGameStartComments();
        initializeGame();

        while (true) {
            try {
                showBoard();

                if (doesUserWinTheGame()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    break;
                }
                if (doesUserLoseTheGame()) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (AppException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                System.out.println("프로그램에 문제가 생겼습니다.");
                e.printStackTrace();
            }
        }
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void actOnCell(String input, String userActionInput) {
        int selectedColIndex = extractSelectedColIndex(input);
        int selectedRowIndex = extractSelectedRowIndex(input);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex] = Cell.of(FLAG_SIGN);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedColIndex, selectedRowIndex)) {
                BOARD[selectedRowIndex][selectedColIndex] = Cell.of(LANDMINE_SIGN);
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
        }
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedColIndex, int selectedRowIndex) {
        return landMines[selectedRowIndex][selectedColIndex];
    }

    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static String getActionInputFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        String userActionInput = SCANNER.nextLine();
        return userActionInput;
    }

    private static String getCellInputFromUser() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        String input = SCANNER.nextLine();
        return input;
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellOpened();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellOpened() {
        return Arrays.stream(BOARD)
                .flatMap(stringArr -> Arrays.stream(stringArr))
                .noneMatch(cell -> cell.equalsSign(OPENED_CELL_SIGN));
    }

    private static int convertRowFrom(char cellInputRow) {
        int selectedRowIndex = Character.getNumericValue(cellInputRow) - 1;
        if(selectedRowIndex >= BOARD_ROW_SIZE){
            throw new IllegalArgumentException("잘못된 ROW 입력입니다.");
        }

        return selectedRowIndex;
    }

    private static int extractSelectedColIndex(String input) {
        return convertColFrom(input.charAt(0));
    }

    private static int extractSelectedRowIndex(String input) {
        return convertRowFrom(input.charAt(1));
    }

    private static int convertColFrom(char cellInputCol) {
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

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                System.out.print(BOARD[row][col].getSign() + " ");
                //여기서는 Getter를 안 쓰는게 이상하다.
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = Cell.of(CLOSED_CELL_SIGN);
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            landMines[row][col] = true;
        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                if (isLandMineCell(col, row)) {
                    NEARBY_LAND_MINE_COUNTS[row][col] = 0;
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                NEARBY_LAND_MINE_COUNTS[row][col] = count;

            }
        }
    }

    private static int countNearbyLandMines(int row, int col) {
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

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        if (BOARD[row][col].doesNotEqualSign(CLOSED_CELL_SIGN)) {
            return;
        }
        if (isLandMineCell(col, row)) {
            return;
        }
        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) {
            BOARD[row][col] = Cell.of(String.valueOf(NEARBY_LAND_MINE_COUNTS[row][col]));
            return;
        } else {
            BOARD[row][col] = Cell.of(OPENED_CELL_SIGN);
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
