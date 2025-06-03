package cleancode.minesweeper.tobe.cell;

public interface Cell {

    // Cell이 가진 속성 : 근처 지뢰 숫자, 지뢰 여부
    // Cell의 상태 : 깃발 유무, 열렸다/닫혔다, 사용자가 확인함(닫혀있지만 사용자가 깃발을 꽂음)

    boolean isLandMine();

    boolean hasLandMineCount();

    void flag();

    boolean isChecked();

    void open();

    boolean isOpened();

    CellSnapshot getSnapShot();
}
