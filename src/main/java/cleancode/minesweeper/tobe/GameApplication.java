package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.Minesweeper;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.Middle;

public class GameApplication {

    public static void main(String[] args) {

        GameLevel gameLevel = new Middle();

        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.initialize();
        minesweeper.run();
    }

}