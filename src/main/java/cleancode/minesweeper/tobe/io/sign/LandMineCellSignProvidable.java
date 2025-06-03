package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;

public class LandMineCellSignProvidable implements CellSignProvidable {

    private static final String LANDMINE_SIGN = "☼";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.LANDMINE);
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return LANDMINE_SIGN;
    }
}
