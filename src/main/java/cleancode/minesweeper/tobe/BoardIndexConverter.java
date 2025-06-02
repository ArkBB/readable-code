package cleancode.minesweeper.tobe;


public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int extractSelectedColIndex(String cellInput) {
        return convertColFrom(cellInput.charAt(0));
    }

    public int extractSelectedRowIndex(String cellInput) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow);
    }

    public int convertColFrom(char cellInputCol) {
        int selectedColIndex = cellInputCol - BASE_CHAR_FOR_COL;

        if(selectedColIndex < 0) {
            throw new GameException("잘못된 COL 입력입니다.");
        }
        return selectedColIndex;
    }

    public int convertRowFrom(String cellInputRow) {
        int selectedRowIndex = Integer.parseInt(cellInputRow) - 1;
        if (selectedRowIndex <0) {
            throw new GameException("잘못된 ROW 입력입니다.");
        }

        return selectedRowIndex;
    }

}
