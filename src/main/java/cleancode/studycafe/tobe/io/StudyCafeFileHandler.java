package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import cleancode.studycafe.tobe.reader.readable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeFileHandler {

    private static final String PATH_TO_PASS_LIST_CSV = "src/main/resources/cleancode/studycafe/pass-list.csv";
    private static final String PATH_TO_LOCKER_LIST_CSV = "src/main/resources/cleancode/studycafe/locker.csv";
    private readable reader;

    public StudyCafeFileHandler(readable reader) {
        this.reader = reader;
    }

    public List<StudyCafePass> readStudyCafePasses() {
        try {
            List<StudyCafePass> studyCafePasses = reader.readStudyCafePasses(PATH_TO_PASS_LIST_CSV);
            return studyCafePasses;
        }
        catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    public List<StudyCafeLockerPass> readLockerPasses() {
        try {
            List<StudyCafeLockerPass> lockerPasses = reader.readStudyCafeLockerPasses(PATH_TO_LOCKER_LIST_CSV);
            return lockerPasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

}
