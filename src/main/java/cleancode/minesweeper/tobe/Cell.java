package cleancode.minesweeper.tobe;

public class Cell {

    private static final String CLOSED_CELL_SIGN = "■";
    private static final String OPENED_CELL_SIGN = "□";
    private static final String FLAG_SIGN = "⚑";
    private static final String LANDMINE_SIGN = "☼";

    private final String sign;
    private final int nearbyLandMineCount;
    private final boolean isLandMine;

    // Cell이 가진 속성 : 근처 지뢰 숫자, 지뢰 여부
    // Cell의 상태 : 깃발 유무, 열렸다/닫혔다, 사용자가 확인함(닫혀있지만 사용자가 깃발을 꽂음)

    private Cell(String sign, int nearbyLandMineCount, boolean isLandMine) {
        this.sign = sign;
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
    }

    public static Cell of(String sign,int nearbyLandMineCount, boolean isLandMine){
        return new Cell(sign,nearbyLandMineCount,isLandMine);
    }

    public static Cell ofFlag(){
        return of(FLAG_SIGN,0,false);
    }

    public static Cell ofLandMine(){
        return of(LANDMINE_SIGN);
    }

    public static Cell ofClosed() {
        return of(CLOSED_CELL_SIGN);
    }

    public static Cell ofOpened() {
        return of(OPENED_CELL_SIGN);
    }

    public static Cell ofNearbyLandMineCount(int count) {
        return of(String.valueOf(count));
    }

    public boolean equalsSign(String sign) {
        return this.sign.equals(sign);
    }

    public String getSign() {
        return sign;
    }

    public boolean isClosed() {
        return equalsSign(CLOSED_CELL_SIGN);
    }

    public boolean isNotClosed() {
        return !isClosed();
    }
}
