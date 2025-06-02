package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.Advanced;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.Middle;

public class GameApplication {

    public static void main(String[] args) {

        GameLevel gameLevel = new Middle();

        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.run();
    }

}