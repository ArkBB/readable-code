package cleancode.studycafe.tobe.reader;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import java.io.IOException;
import java.util.List;

public interface readable {

    public List<StudyCafePass> readStudyCafePasses(String paths) throws IOException;

    public List<StudyCafeLockerPass> readStudyCafeLockerPasses(String paths) throws IOException;
}
