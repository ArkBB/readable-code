package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;
import java.util.Arrays;

public enum CellSignProvider implements CellSignProvidable{

    EMPTY(CellSnapshotStatus.EMPTY){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return EMPTY_SIGN;
        }
    },
    FLAG(CellSnapshotStatus.FLAG){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return FLAG_SIGN;
        }
    },
    NUMBER(CellSnapshotStatus.NUMBER){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return String.valueOf(cellSnapshot.getNearByLandMineCount());
        }
    },
    UNCHECKED(CellSnapshotStatus.UNCHECKED){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return UNCHECKED_SIGN;
        }
    },
    LANDMINE(CellSnapshotStatus.LANDMINE){
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return LANDMINE_SIGN;
        }
    };

    private final CellSnapshotStatus cellSnapshotStatus;

    static final String EMPTY_SIGN = "■"; // 열었는데 비어있는 셀
    static final String FLAG_SIGN = "⚑";
    static final String LANDMINE_SIGN = "☼";
    static final String UNCHECKED_SIGN = "□"; // 아직 확인하지 않은 셀


    CellSignProvider(CellSnapshotStatus cellSnapshotStatus) {
        this.cellSnapshotStatus = cellSnapshotStatus;
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(cellSnapshotStatus);
    }


    public static String findCellSignFrom(CellSnapshot snapshot){
        CellSignProvider cellSignProvider = Arrays.stream(values())
                .filter(provider -> provider.supports(snapshot))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));

        return cellSignProvider.provide(snapshot);

    }
}
