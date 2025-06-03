package cleancode.minesweeper.tobe.game;

import cleancode.minesweeper.tobe.io.BoardIndexConverter;
import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;

public class Minesweeper implements GameRunnable, GameInitializable {


    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        this.gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }

    public void run() {

        outputHandler.showGameStartComments();

        while (true) {
            try {
                outputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    outputHandler.showGameClear();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    outputHandler.showGameOver();
                    break;
                }

                CellPosition cellPosition = getCellInputFromUser();
                UserAction userActionInput = getActionInputFromUser();

                actOnCell(cellPosition, userActionInput);
            } catch (GameException e) {
                outputHandler.showErrMessage(e);

            } catch (Exception e) {
                outputHandler.showMessage("프로그램에 문제가 생겼습니다.");
                e.printStackTrace();
            }
        }
    }
    
    private void actOnCell(CellPosition cellPosition, UserAction userActionInput) {


        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flagAt(cellPosition);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMineCellAt(cellPosition)) {
                gameBoard.openAt(cellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCell(cellPosition);
            checkIfGameIsOver();
        }

    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserChooseToOpenCell(UserAction userActionInput) {
        return userActionInput == UserAction.OPEN;
    }

    private boolean doesUserChooseToPlantFlag(UserAction userActionInput) {
        return userActionInput == UserAction.FLAG;
    }

    private UserAction getActionInputFromUser() {
        outputHandler.showUserActionInputComment();
        return inputHandler.getUserActionFromUser();
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCoordinateInputComment();
        CellPosition cellPositionFromUser = inputHandler.getCellPositionFromUser();

        if(gameBoard.isInvalidCellPosition(cellPositionFromUser)){
            throw new IllegalArgumentException("잘못된 좌표를 입력하셨습니다.");
        }

        return cellPositionFromUser;
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