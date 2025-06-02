package cleancode.minesweeper.tobe.game;

import cleancode.minesweeper.tobe.BoardIndexConverter;
import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class Minesweeper implements GameRunnable, GameInitializable {


    private final GameBoard gameBoard;
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel) {
        this.gameBoard = new GameBoard(gameLevel);
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }

    public void run() {

        consoleOutputHandler.showGameStartComments();

        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);

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
        int selectedColIndex = boardIndexConverter.extractSelectedColIndex(input,gameBoard.getColSize());
        int selectedRowIndex = boardIndexConverter.extractSelectedRowIndex(input,gameBoard.getLowSize());

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flag(selectedRowIndex,selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex,selectedColIndex)) {
                gameBoard.openSurroundedCell(selectedRowIndex,selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCell(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
        }

    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
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

    public void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }


}