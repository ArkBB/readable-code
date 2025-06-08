package cleancode.studycafe.asis.model.order;

import cleancode.studycafe.asis.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.asis.model.pass.locker.StudyCafeLockerPass;
import java.util.Optional;

public class StudyCafePassOrder {

    private final StudyCafeSeatPass seatPass;
    private final StudyCafeLockerPass lockerPass;


    public StudyCafePassOrder(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        this.seatPass = seatPass;
        this.lockerPass = lockerPass;
    }

    public static StudyCafePassOrder of(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        return new StudyCafePassOrder(seatPass, lockerPass);
    }

    public StudyCafeSeatPass getSeatPass() {
        return seatPass;
    }

    public Optional<StudyCafeLockerPass> getLockerPass() {
        return Optional.ofNullable(lockerPass);
    }

    public int getDiscountPrice() {
        return seatPass.getDiscountPrice();
    }

    public int getTotalPrice() {
        int lockerPrice = lockerPass == null ? 0 : lockerPass.getPrice();
        return seatPass.getPrice() - getDiscountPrice() + lockerPrice;
    }
}


