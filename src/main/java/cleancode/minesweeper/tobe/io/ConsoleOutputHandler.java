package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;
import cleancode.minesweeper.tobe.position.CellPosition;
import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler {

    private static final String EMPTY_SIGN = "■"; // 열었는데 비어있는 셀
    private static final String LANDMINE_SIGN = "☼";
    static final String UNCHECKED_SIGN = "□"; // 아직 확인하지 않은 셀
    static final String FLAG_SIGN = "⚑";

    @Override
    public void showBoard(GameBoard board) {

        String joinAlphabets = generateColAlphabets(board);

        System.out.println("    " + joinAlphabets);

        for (int row = 0; row < board.getLowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {

                CellSnapshot snapshot = board.getSnapShot(CellPosition.of(row, col));
                String cellSign = decideCellSignFrom(snapshot);

                System.out.print(cellSign + " ");

            }
            System.out.println();
        }
        System.out.println();
    }

    private String decideCellSignFrom(CellSnapshot snapshot) {
        CellSnapshotStatus status = snapshot.getStatus();
        if (status == CellSnapshotStatus.EMPTY){
            return EMPTY_SIGN;
        }
        if (status == CellSnapshotStatus.FLAG){
            return FLAG_SIGN;
        }
        if (status == CellSnapshotStatus.LANDMINE){
            return LANDMINE_SIGN;
        }
        if (status == CellSnapshotStatus.NUMBER){
            return String.valueOf(snapshot.getNearByLandMineCount());
        }
        if (status == CellSnapshotStatus.UNCHECKED){
            return UNCHECKED_SIGN;
        }

        throw new IllegalArgumentException("확인할 수 없는 셀입니다.");

    }

    private String generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())
                .mapToObj(index -> (char) ('a' + index))
                .map(Object::toString)
                .toList();

        String joinAlphabets = String.join(" ", alphabets);
        return joinAlphabets;
    }

    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showGameClear() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void showGameOver() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showCoordinateInputComment() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void showUserActionInputComment(){
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showErrMessage(GameException E) {
        System.out.println(E.getMessage());
    }

    @Override
    public void showMessage(String message){
        System.out.println(message);
    }
}
