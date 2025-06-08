package cleancode.studycafe.asis;

import cleancode.studycafe.asis.exception.AppException;
import cleancode.studycafe.asis.io.InputHandler;
import cleancode.studycafe.asis.io.OutputHandler;
import cleancode.studycafe.asis.io.StudyCafeFileHandler;
import cleancode.studycafe.asis.model.StudyCafeLockerPass;
import cleancode.studycafe.asis.model.StudyCafePass;
import cleancode.studycafe.asis.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();
            Optional<StudyCafeLockerPass> lockerPass = selectLockerPass(selectedPass);

            outputHandler.showPassOrderSummary(selectedPass, lockerPass.orElse(null));
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidates = findPassCandidatesBy(studyCafePassType);

        outputHandler.showPassListForSelection(passCandidates);
        StudyCafePass selectedPass = inputHandler.getSelectPass(passCandidates);
        return selectedPass;
    }

    private List<StudyCafePass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();

        return allPasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();

    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {

        if(selectedPass.getPassType() != StudyCafePassType.FIXED) {
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate != null) {
            outputHandler.askLockerPass(lockerPassCandidate);
            boolean lockerSelection = inputHandler.getLockerSelection();

            if (lockerSelection) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }

}
