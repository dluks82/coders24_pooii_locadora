package model.vehicle;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Truck extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(300.00);

    public Truck(String id, String plate, String model, String brand, String agencyId) {
        super(id, plate, model, brand, agencyId);
    }

    public BigDecimal getDailyRate() {
        return DAILY_RATE;
    }



}
