package cleancode.studycafe.tobe.model;

import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;

public interface StudyCafePassStrategy {
    void execute(OutputHandler outputHandler, InputHandler inputHandler, StudyCafeFileHandler fileHandler);
}
