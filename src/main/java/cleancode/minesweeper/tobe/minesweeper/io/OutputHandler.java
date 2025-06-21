package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import cleancode.minesweeper.tobe.minesweeper.exception.GameException;

public interface OutputHandler {
    void showBoard(GameBoard board);

    void showGameStartComments();

    void showGameClear();

    void showGameOver();

    void showCoordinateInputComment();

    void showUserActionInputComment();

    void showErrMessage(GameException E);

    void showMessage(String message);
}
