package cleancode.minesweeper.tobe.minesweeper.board.position;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CellPositionsTest {

    @DisplayName("지정된 개수만큼의 랜덤한 위치 목록을 반환한다.")
    @Test
    void extractRandomPositions() {


        // given
        int rowSize = 3;
        int colSize = 3;

        List<CellPosition> cellPositionList = new ArrayList<>();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositionList.add(cellPosition);
            }
        }

        CellPositions cellPositions = CellPositions.of(cellPositionList);

        //when
        List<CellPosition> extractedPositions = cellPositions.extractRandomPositions(3);
        List<CellPosition> extractedPositions2 = cellPositions.extractRandomPositions(3);



        //then
        assertThat(extractedPositions).hasSize(3);
        assertThat(extractedPositions2).hasSize(3);

        assertThat(cellPositionList).containsAll(extractedPositions);
        assertThat(cellPositionList).containsAll(extractedPositions2);

    }

    @Test
    @DisplayName("전체 셀 중, 지뢰 위치를 제외한 나머지 위치들이 반환되어야 한다.")
    void subtract() {
        //given
        int rowSize = 3;
        int colSize = 3;
        int landMineCount = 2;

        List<CellPosition> cellPositionList = new ArrayList<>();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositionList.add(cellPosition);
            }
        }

        CellPositions cellPositions = CellPositions.of(cellPositionList);

        // 지뢰 위치
        List<CellPosition> minePositions = generateRandomMinePositions(cellPositionList, landMineCount);

        //when
        List<CellPosition> subtractedPositions = cellPositions.subtract(minePositions);

        //then
        assertThat(subtractedPositions).hasSize(rowSize * colSize - 2)
            .doesNotContainAnyElementsOf(minePositions);

    }


    private List<CellPosition> generateRandomMinePositions(List<CellPosition> allPositions, int landMineCount) {
        List<CellPosition> positions = new ArrayList<>(allPositions);
        List<CellPosition> minePositions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < landMineCount && !positions.isEmpty(); i++) {
            int randomIndex = random.nextInt(positions.size());
            minePositions.add(positions.remove(randomIndex));
        }

        return minePositions;
    }

}
