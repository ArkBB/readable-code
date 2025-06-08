package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.calculate.PriceCalculator;
import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.io.StudyCafeIOHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import cleancode.studycafe.tobe.reader.FileReader;
import java.util.List;
import java.util.function.Predicate;

public class StudyCafePassMachine {

    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler(new FileReader());
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler(new PriceCalculator());
    private final StudyCafeIOHandler studyCafeIOHandler = new StudyCafeIOHandler(inputHandler, outputHandler);

    public void run() {
        try {
            studyCafeIOHandler.showWelcomeMessage();
            studyCafeIOHandler.showAnnouncement();

            StudyCafePassType studyCafePassType = selectStudyCafePassType();
            studyCafePassType.execute(outputHandler,inputHandler,studyCafeFileHandler);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePassType selectStudyCafePassType() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
        return studyCafePassType;
    }

}
