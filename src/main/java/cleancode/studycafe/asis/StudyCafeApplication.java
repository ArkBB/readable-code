package cleancode.studycafe.asis;

import cleancode.studycafe.asis.io.PassReader;
import cleancode.studycafe.asis.io.StudyCafeFileHandler;

public class StudyCafeApplication {

    public static void main(String[] args) {
        PassReader passReader = new StudyCafeFileHandler();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(passReader);
        studyCafePassMachine.run();
    }

}
