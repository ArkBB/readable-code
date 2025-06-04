package cleancode.studycafe.tobe.reader;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader implements readable {


    @Override
    public List<StudyCafePass> readStudyCafePasses(String paths) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(paths));
        List<StudyCafePass> studyCafePasses = new ArrayList<>();
        for (String line : lines) {
            String[] values = line.split(",");
            StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
            int duration = Integer.parseInt(values[1]);
            int price = Integer.parseInt(values[2]);
            double discountRate = Double.parseDouble(values[3]);

            studyCafePasses.add(StudyCafePass.of(studyCafePassType, duration, price, discountRate));
        }
        return studyCafePasses;
    }

    @Override
    public List<StudyCafeLockerPass> readStudyCafeLockerPasses(String paths) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(paths));
        List<StudyCafeLockerPass> lockerPasses = new ArrayList<>();
        for (String line : lines) {
            String[] values = line.split(",");
            StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
            int duration = Integer.parseInt(values[1]);
            int price = Integer.parseInt(values[2]);

            StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(studyCafePassType, duration, price);
            lockerPasses.add(lockerPass);
        }
        return lockerPasses;
    }

}
