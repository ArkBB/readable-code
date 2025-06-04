package cleancode.studycafe.tobe.calculate;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;

public class PriceCalculator {

    public int getDiscountPrice(StudyCafePass selectedPass) {
        double discountRate = selectedPass.getDiscountRate();
        return (int) (selectedPass.getPrice() * discountRate);
    }

    public int getTotalPrice(int discountPrice, StudyCafePass selectedPass, StudyCafeLockerPass lockerPass) {
        return selectedPass.getPrice() - discountPrice + (lockerPass != null ? lockerPass.getPrice() : 0);
    }

}
