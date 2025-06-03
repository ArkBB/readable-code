package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;

public class UncheckedCellSignProvidable implements CellSignProvidable {

    static final String UNCHECKED_SIGN = "□"; // 아직 확인하지 않은 셀

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.UNCHECKED);
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return UNCHECKED_SIGN;
    }

}
