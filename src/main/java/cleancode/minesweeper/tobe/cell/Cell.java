package cleancode.minesweeper.tobe.cell;

public abstract class Cell {

    protected static final String UNCHECKED_SIGN = "□"; // 아직 확인하지 않은 셀
    protected static final String FLAG_SIGN = "⚑";

    protected boolean isFlagged;
    protected boolean isOpened;

    // Cell이 가진 속성 : 근처 지뢰 숫자, 지뢰 여부
    // Cell의 상태 : 깃발 유무, 열렸다/닫혔다, 사용자가 확인함(닫혀있지만 사용자가 깃발을 꽂음)

    public abstract boolean isLandMine();

    public abstract boolean hasLandMineCount();

    public abstract String getSign();

    public void flag() {
        this.isFlagged = true;
    }

    public boolean isChecked() {
        return isOpened || isFlagged;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isOpened() {
        return isOpened;
    }


}
