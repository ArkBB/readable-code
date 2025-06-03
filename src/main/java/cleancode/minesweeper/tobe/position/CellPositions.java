package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {

    private final List<CellPosition> positions;

    private CellPositions(List<CellPosition> positions){
        this.positions = positions;
    }

    public static CellPositions of(List<CellPosition> positions){
        return new CellPositions(positions);
    }

    public static CellPositions from(Cell[][] board) {

        List<CellPosition> cellPositions = new ArrayList<>();

        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                cellPositions.add(CellPosition.of(row, col));
            }
        }

        return CellPositions.of(cellPositions);
    }

    // 일급 컬렉션 주의점
    public List<CellPosition> getPositions() {
        return new ArrayList<>(positions);
    }

    public List<CellPosition> extractRandomPositions(int count) {

        ArrayList<CellPosition> cellPositions = new ArrayList<>(positions);

        Collections.shuffle(cellPositions);

        return cellPositions.subList(0, count);

    }

    public List<CellPosition> subtract(List<CellPosition> positions, List<CellPosition> landMinePositions) {

        List<CellPosition> landMineCellPositions = new ArrayList<>(landMinePositions);
//        return cellPositions.stream()
//                .filter(position -> !positions.contains(position))
//                .toList();

        positions.removeAll(landMineCellPositions);
        return positions;
    }
}
