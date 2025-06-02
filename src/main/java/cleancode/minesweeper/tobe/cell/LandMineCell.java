package cleancode.minesweeper.tobe.cell;

public class LandMineCell extends Cell {

    private static final String LANDMINE_SIGN = "☼";

    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public String getSign() {

        if(isOpened){
            return LANDMINE_SIGN;
        }

        if(isFlagged){
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;

    }
}
