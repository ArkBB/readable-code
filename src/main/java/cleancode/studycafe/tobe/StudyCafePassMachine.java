package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import cleancode.studycafe.tobe.reader.FileReader;
import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler(new FileReader());
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePassType studyCafePassType = selectStudyCafePassType();
            StudyCafePass selectedPass = getSelectedPass(studyCafePassType);
            StudyCafeLockerPass lockerPass = findMatchingLockerPass(selectedPass);

            if (lockerPass != null && askUseLocker(lockerPass)) {
                outputHandler.showPassOrderSummary(selectedPass, lockerPass);
            }
            else {
                outputHandler.showPassOrderSummary(selectedPass, null);
            }

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private boolean askUseLocker(StudyCafeLockerPass lockerPass) {

        outputHandler.askLockerPass(lockerPass);
        return inputHandler.getLockerSelection();

    }

    private StudyCafeLockerPass findMatchingLockerPass(StudyCafePass pass) {
        if (pass.getPassType() != StudyCafePassType.FIXED) {
            return null;
        }

        return studyCafeFileHandler.readLockerPasses().stream()
                .filter(locker -> locker.getPassType() == pass.getPassType()
                        && locker.getDuration() == pass.getDuration())
                .findFirst()
                .orElse(null);

    }

    private StudyCafePass getSelectedPass(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        List<StudyCafePass> passes = getStudyCafePasses(studyCafePasses, studyCafePassType);
        outputHandler.showPassListForSelection(passes);
        StudyCafePass selectedPass = inputHandler.getSelectPass(passes);
        return selectedPass;
    }

    private StudyCafePassType selectStudyCafePassType() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
        return studyCafePassType;
    }


    private static List<StudyCafePass> getStudyCafePasses(List<StudyCafePass> studyCafePasses,
                                                          StudyCafePassType studyCafePassType) {
        List<StudyCafePass> hourlyPasses = studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
            .toList();
        return hourlyPasses;
    }

}
