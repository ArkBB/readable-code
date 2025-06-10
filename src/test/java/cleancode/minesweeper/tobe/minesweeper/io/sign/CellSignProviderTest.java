package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CellSignProviderTest {

    @Test
    @DisplayName("빈 셀은 ■ 기호를 반환해야 한다")
    void empty_cell_should_return_filled_square() {
        // given
        CellSnapshot emptyCell = CellSnapshot.of(CellSnapshotStatus.EMPTY, 0);

        // when
        String result = CellSignProvider.findCellSignFrom(emptyCell);

        // then
        Assertions.assertThat(result).isEqualTo("■");
    }

    @Test
    @DisplayName("깃발이 표시된 셀은 ⚑ 기호를 반환해야 한다")
    void flagged_cell_should_return_flag() {
        // given
        CellSnapshot flaggedCell = CellSnapshot.of(CellSnapshotStatus.FLAG, 0);

        // when
        String result = CellSignProvider.findCellSignFrom(flaggedCell);

        // then
        Assertions.assertThat(result).isEqualTo("⚑");
    }

    @Test
    @DisplayName("지뢰가 있는 셀은 ☼ 기호를 반환해야 한다")
    void mine_cell_should_return_mine() {
        // given
        CellSnapshot mineCell = CellSnapshot.of(CellSnapshotStatus.LAND_MINE, 0);

        // when
        String result = CellSignProvider.findCellSignFrom(mineCell);

        // then
        Assertions.assertThat(result).isEqualTo("☼");
    }

    @Test
    @DisplayName("숫자 셀은 주변 지뢰 개수를 문자열로 반환해야 한다")
    void number_cell_should_return_nearby_mine_count() {
        // given
        int nearbyMines = 3;
        CellSnapshot numberCell = CellSnapshot.of(CellSnapshotStatus.NUMBER, nearbyMines);

        // when
        String result = CellSignProvider.findCellSignFrom(numberCell);

        // then
        Assertions.assertThat(result).isEqualTo("3");
    }

    @Test
    @DisplayName("확인하지 않은 셀은 □ 기호를 반환해야 한다")
    void unchecked_cell_should_return_empty_square() {
        // given
        CellSnapshot uncheckedCell = CellSnapshot.of(CellSnapshotStatus.UNCHECKED, 0);

        // when
        String result = CellSignProvider.findCellSignFrom(uncheckedCell);

        // then
        Assertions.assertThat(result).isEqualTo("□");
    }

    @Test
    @DisplayName("지원하지 않는 상태의 셀은 IllegalArgumentException이 발생해야 한다")
    void unsupported_cell_status_should_throw_exception() {
        // given
        CellSnapshot nullStatusCell = CellSnapshot.of(null, 0);

        // when & then
        Assertions.assertThatThrownBy(() -> CellSignProvider.findCellSignFrom(nullStatusCell))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("확인할 수 없는 셀입니다.");
    }
}
