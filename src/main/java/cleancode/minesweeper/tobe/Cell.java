package cleancode.minesweeper.tobe;

public class Cell {

    private static final String UNCHECKED_SIGN = "□"; // 아직 확인하지 않은 셀
    private static final String EMPTY_SIGN = "■"; // 열었는데 비어있는 셀
    private static final String FLAG_SIGN = "⚑";
    private static final String LANDMINE_SIGN = "☼";

    private int nearbyLandMineCount;
    private boolean isLandMine;
    private boolean isFlagged;
    private boolean isOpened;

    // Cell이 가진 속성 : 근처 지뢰 숫자, 지뢰 여부
    // Cell의 상태 : 깃발 유무, 열렸다/닫혔다, 사용자가 확인함(닫혀있지만 사용자가 깃발을 꽂음)

    private Cell(int nearbyLandMineCount, boolean isLandMine, boolean isFlagged, boolean isOpened) {
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    public static Cell of(int nearbyLandMineCount, boolean isLandMine, boolean isFlagged,
                          boolean isOpened) {
        return new Cell(nearbyLandMineCount, isLandMine, isFlagged, isOpened);
    }

    public static Cell create() {
        return of(0, false,false,false);
    }

    public void updateNearbyLandMineCount(int count) {
        this.nearbyLandMineCount = count;
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public void flag() {
        this.isFlagged = true;
    }

    public boolean isChecked() {
        return isOpened || isFlagged;
    }

    public boolean isLandMine() {
        return isLandMine;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean hasLandMineNearBy() {
        return nearbyLandMineCount > 0;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public String getSign() {
        if (isOpened) {
            if (isLandMine) {
                return LANDMINE_SIGN;
            }
            return nearbyLandMineCount > 0 ? String.valueOf(nearbyLandMineCount) : EMPTY_SIGN;
        }
        if (isFlagged) {
            return FLAG_SIGN;
        }
        return UNCHECKED_SIGN;
    }
}
