package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.board.GameBoard;
import cleancode.minesweeper.tobe.GameException;

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
