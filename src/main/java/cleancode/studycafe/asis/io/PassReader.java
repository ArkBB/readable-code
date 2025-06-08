package cleancode.studycafe.asis.io;

import cleancode.studycafe.asis.model.pass.StudyCafeSeatPasses;
import cleancode.studycafe.asis.model.pass.locker.StudyCafeLockerPasses;

public interface PassReader {

    StudyCafeSeatPasses readStudyCafePasses();
    StudyCafeLockerPasses readStudyCafeLockerPasses();

}
