package cleancode.studycafe.tobe.model;

import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import java.util.List;
import java.util.function.Predicate;

public enum StudyCafePassType implements StudyCafePassStrategy {
    
    FIXED("1인 고정석") {

        @Override
        public void execute(OutputHandler outputHandler, InputHandler inputHandler, StudyCafeFileHandler fileHandler) {
            StudyCafePass selectedPass = selectPass(this, outputHandler, inputHandler, fileHandler);
            StudyCafeLockerPass lockerPass = findMatchingLockerPass(selectedPass, fileHandler);

            boolean useLocker = false;

            if (lockerPass != null) {
                useLocker = askUseLocker(outputHandler, inputHandler, lockerPass);
            }

            outputHandler.showPassOrderSummary(selectedPass, useLocker ? lockerPass : null);
        }
    },
    WEEKLY("주 단위 이용권"),
    HOURLY("시간 단위 이용권");

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }

    @Override
    public void execute(OutputHandler outputHandler, InputHandler inputHandler, StudyCafeFileHandler fileHandler) {
        StudyCafePass selectedPass = selectPass(this, outputHandler, inputHandler, fileHandler);
        outputHandler.showPassOrderSummary(selectedPass, null);
    }

    private static StudyCafePass selectPass(
            StudyCafePassType passType,
            OutputHandler outputHandler,
            InputHandler inputHandler,
            StudyCafeFileHandler fileHandler
    ) {
        List<StudyCafePass> passes = fileHandler.readStudyCafePasses().stream()
                .filter(studyCafePass -> studyCafePass.passTypeEqualTo(passType))
                .toList();

        outputHandler.showPassListForSelection(passes);
        return inputHandler.getSelectPass(passes);
    }

    private static StudyCafeLockerPass findMatchingLockerPass(
            StudyCafePass selectedPass,
            StudyCafeFileHandler fileHandler
    ) {
        return fileHandler.readLockerPasses().stream()
                .filter(getStudyCafeLockerPassPredicate(selectedPass)
                ).findFirst().orElse(null);
    }

    private static Predicate<StudyCafeLockerPass> getStudyCafeLockerPassPredicate(StudyCafePass selectedPass) {
        return locker ->
                locker.passTypeEqualTo(selectedPass.getPassType())
                        && locker.durationEqualTo(selectedPass.getDuration());
    }

    private static boolean askUseLocker(
            OutputHandler outputHandler,
            InputHandler inputHandler,
            StudyCafeLockerPass lockerPass
    ) {
        outputHandler.askLockerPass(lockerPass);
        return inputHandler.getLockerSelection();
    }


}

