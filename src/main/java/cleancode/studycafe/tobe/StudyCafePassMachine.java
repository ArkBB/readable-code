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

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

            List<StudyCafePass> Passes = getStudyCafePasses(studyCafePasses,studyCafePassType);
            outputHandler.showPassListForSelection(Passes);
            StudyCafePass selectedPass = inputHandler.getSelectPass(Passes);

            if (studyCafePassType == StudyCafePassType.FIXED) {
                List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                        .filter(option ->
                                option.getPassType() == selectedPass.getPassType()
                                        && option.getDuration() == selectedPass.getDuration()
                        )
                        .findFirst()
                        .orElse(null);

                boolean lockerSelection = false;
                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    lockerSelection = inputHandler.getLockerSelection();
                }

                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                    return;
                }
            }
            outputHandler.showPassOrderSummary(selectedPass, null);


        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private static StudyCafeLockerPass getStudyCafeLockerPass(List<StudyCafeLockerPass> lockerPasses,
                                                              StudyCafePass selectedPass) {
        StudyCafeLockerPass lockerPass = lockerPasses.stream()
            .filter(option ->
                option.getPassType() == selectedPass.getPassType()
                    && option.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);
        return lockerPass;
    }

    private static List<StudyCafePass> getStudyCafePasses(List<StudyCafePass> studyCafePasses,
                                                          StudyCafePassType studyCafePassType) {
        List<StudyCafePass> hourlyPasses = studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
            .toList();
        return hourlyPasses;
    }

}
