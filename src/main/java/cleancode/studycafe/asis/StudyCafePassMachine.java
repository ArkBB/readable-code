package cleancode.studycafe.asis;

import cleancode.studycafe.asis.exception.AppException;
import cleancode.studycafe.asis.io.StudyCafeFileHandler;
import cleancode.studycafe.asis.io.StudyCafeIOHandler;
import cleancode.studycafe.asis.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.asis.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.asis.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.asis.model.pass.StudyCafePassType;

import cleancode.studycafe.asis.model.pass.StudyCafeSeatPasses;
import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
    private final StudyCafeIOHandler studyCafeIOHandler = new StudyCafeIOHandler();

    public void run() {
        try {
            studyCafeIOHandler.showWelcomeMessage();
            studyCafeIOHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = selectPass();
            Optional<StudyCafeLockerPass> lockerPass = selectLockerPass(selectedPass);

            studyCafeIOHandler.showPassOrderSummary(selectedPass, lockerPass.orElse(null));
        } catch (AppException e) {
            studyCafeIOHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            studyCafeIOHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeSeatPass selectPass() {

        StudyCafePassType studyCafePassType = studyCafeIOHandler.askPassTypeSelecting();
        List<StudyCafeSeatPass> passCandidates = findPassCandidatesBy(studyCafePassType);

        StudyCafeSeatPass selectedPass = studyCafeIOHandler.askPassSelecting(passCandidates);
        return selectedPass;
    }

    private List<StudyCafeSeatPass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        StudyCafeSeatPasses allPasses = studyCafeFileHandler.readStudyCafePasses();

        return allPasses.findPassBy(studyCafePassType);

    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {
        // 고정 좌석 타입이 아닌가?
        // 사물함 옵션을 사용할 수 있는 타입이 아닌가?
        // 고정 좌석 타입인지가 중요한게 아니라, 사물함 좌석을 이용할 수 있는지
        // 없는지가 더 중요한 것이기 때문에 메서드명을 아래와 같이 하는게 더 적합하다.
        if(selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate.isPresent()) {

            StudyCafeLockerPass lockerPass = lockerPassCandidate.get();

            boolean lockerSelection = studyCafeIOHandler.askLockerPass(lockerPass);

            if (lockerSelection) {
                return Optional.of(lockerPass);
            }

        }

        return Optional.empty();
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidateBy(StudyCafeSeatPass selectedPass) {
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.findLockerPassBy(selectedPass);
    }

}
