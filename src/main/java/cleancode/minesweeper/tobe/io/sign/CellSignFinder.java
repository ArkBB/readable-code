package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;
import java.util.List;

public class CellSignFinder {

    public static final List<CellSignProvidable> CELL_SIGN_PROVIDERS = List.of(
            new EmptyCellSignProvidable(),
            new FlagCellSignProvidable(),
            new LandMineCellSignProvidable(),
            new NumberCellSignProvidable(),
            new UncheckedCellSignProvidable()
    );

    public String findCellSignFrom(CellSnapshot snapshot){

        return CELL_SIGN_PROVIDERS.stream()
                .filter(provider -> provider.supports(snapshot))
                .findFirst()
                .map(provider -> provider.provide(snapshot))
                .orElseThrow(()->
                        new IllegalArgumentException("확인할 수 없는 셀입니다."));


    }

}
