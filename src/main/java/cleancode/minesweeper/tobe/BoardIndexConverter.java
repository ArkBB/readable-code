package cleancode.minesweeper.tobe;


public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int extractSelectedColIndex(String cellInput,int colSize) {
        return convertColFrom(cellInput.charAt(0),colSize);
    }

    public int extractSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow,rowSize);
    }

    public int convertColFrom(char cellInputCol,int coLSize) {
        int selectedColIndex = cellInputCol - BASE_CHAR_FOR_COL;

        if(selectedColIndex < 0 || selectedColIndex >= coLSize) {
            throw new GameException("잘못된 COL 입력입니다.");
        }
        return selectedColIndex;
    }

    public int convertRowFrom(String cellInputRow,int rowSize) {
        int selectedRowIndex = Integer.parseInt(cellInputRow) - 1;
        if (selectedRowIndex <0 || selectedRowIndex >= rowSize) {
            throw new GameException("잘못된 ROW 입력입니다.");
        }

        return selectedRowIndex;
    }

}
