package cleancode.minesweeper.tobe.cell;

public class CellSnapshot {

    private final CellSnapshotStatus status;
    private final int nearByLandMineCount;

    public CellSnapshot(CellSnapshotStatus status, int nearByLandMineCount) {
        this.status = status;
        this.nearByLandMineCount = nearByLandMineCount;
    }

    public static CellSnapshot of(CellSnapshotStatus status, int nearByLandMineCount) {
        return new CellSnapshot(status, nearByLandMineCount);
    }

    public static CellSnapshot ofEmpty(){
        return CellSnapshot.of(CellSnapshotStatus.EMPTY, 0);
    }

    public static CellSnapshot ofFlag(){
        return CellSnapshot.of(CellSnapshotStatus.FLAG, 0);
    }

    public static CellSnapshot ofLandMine(){
        return CellSnapshot.of(CellSnapshotStatus.LANDMINE, 0);
    }

    public static CellSnapshot ofNumber(int nearByLandMineCount){
        return CellSnapshot.of(CellSnapshotStatus.NUMBER, nearByLandMineCount);
    }


    public static CellSnapshot ofUnchecked() {
        return CellSnapshot.of(CellSnapshotStatus.UNCHECKED, 0);
    }

    public CellSnapshotStatus getStatus() {
        return status;
    }

    public int getNearByLandMineCount() {
        return nearByLandMineCount;
    }
}
