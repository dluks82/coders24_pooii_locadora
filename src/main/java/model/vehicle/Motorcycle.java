package model.vehicle;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Motorcycle extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(50.00);

    public Motorcycle(String id, String plate, String model, String brand, String agencyId) {
        super(id, plate, model, brand, agencyId);
    }

    @Override
    public BigDecimal getDailyRate() {
        return DAILY_RATE;
    }


}
