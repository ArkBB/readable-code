package cleancode.studycafe.asis.model.pass.locker;

import cleancode.studycafe.asis.model.pass.StudyCafePass;
import cleancode.studycafe.asis.model.pass.StudyCafePassType;

public class StudyCafeLockerPass implements StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;

    private StudyCafeLockerPass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerPass(passType, duration, price);
    }

    public boolean passTypeEqualTo(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean durationEqualTo(int duration) {
        return this.duration == duration;
    }

    @Override
    public StudyCafePassType getPassType() {
        return passType;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getPrice() {
        return price;
    }




}
