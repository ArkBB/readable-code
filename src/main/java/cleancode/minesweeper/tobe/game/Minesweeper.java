package cleancode.minesweeper.tobe.game;

import cleancode.minesweeper.tobe.board.GameStatus;
import cleancode.minesweeper.tobe.io.BoardIndexConverter;
import cleancode.minesweeper.tobe.board.GameBoard;
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

        while (gameBoard.isInProgress()) {
            try {
                outputHandler.showBoard(gameBoard);

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

        outputHandler.showBoard(gameBoard);

        if (gameBoard.isWinStatus()) {
            outputHandler.showGameClear();
        }
        if (gameBoard.isLoseStatus()) {
            outputHandler.showGameOver();
        }
    }
    
    private void actOnCell(CellPosition cellPosition, UserAction userActionInput) {

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flagAt(cellPosition);
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            gameBoard.openAt(cellPosition);
            return;
        }
        throw new GameException("잘못된 번호를 선택하셨습니다.");
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

}