package cleancode.minesweeper.tobe.minesweeper.board;

import static org.assertj.core.api.Assertions.*;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.Beginner;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.VeryBeginner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class GameBoardTest {

    @Test
    @DisplayName("VeryBeginner 레벨로 보드가 초기화 된다.")
    void initialize_with_very_beginner_level() {
        //given
        GameLevel gameLevel = new VeryBeginner();

        //when
        GameBoard gameBoard = new GameBoard(gameLevel);

        //then
        assertThat(gameBoard.getRowSize()).isEqualTo(gameLevel.getRowSize());
        assertThat(gameBoard.getColSize()).isEqualTo(gameLevel.getColSize());
        assertThat(gameBoard.isInProgress()).isTrue();

    }

    @Test
    @DisplayName("보드 크기를 벗어난 위치는 유효하지 않은 위치로 판단한다")
    void should_identify_invalid_position() {
        // given
        GameBoard gameBoard = new GameBoard(new VeryBeginner());
        gameBoard.initializeGame();

        // when then
        CellPosition invalidPosition = CellPosition.of(4, 5);
        assertThat(gameBoard.isInvalidCellPosition(invalidPosition)).isTrue();
    }




//    @Test
//    @DisplayName("지뢰셀 선택 시 게임에서 패배해야 한다.")
//    void should_lose_when_mineCell_selected(){
//        //given
//        GameBoard gameBoard = new GameBoard(new Beginner());
//        gameBoard.initializeGame();
//        CellPosition minePosition = findMinePosition(gameBoard);
//
//        //when
//        gameBoard.openAt(minePosition);
//
//        //then
//        assertThat(gameBoard.isLoseStatus()).isTrue();
//
//    }
//
//
//    private CellPosition findMinePosition(GameBoard gameBoard) {
//        for (int row = 0; row < gameBoard.getRowSize(); row++) {
//            for (int col = 0; col < gameBoard.getColSize(); col++) {
//                CellPosition position = CellPosition.of(row, col);
//                CellSnapshot snapshot = gameBoard.getSnapshot(position);
//
//                if (gameBoard.isL) {
//                    return position;
//                }
//            }
//        }
//        throw new IllegalStateException("지뢰를 찾을 수 없습니다.");
//    }


}
