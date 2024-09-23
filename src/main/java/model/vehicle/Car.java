package model.vehicle;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Car extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(150.00);

    public Car(String id, String plate, String model, String brand, String agencyId) {
        super(id, plate, model, brand, agencyId);
    }

    public BigDecimal getDailyRate() {
        return DAILY_RATE;
    }


}


