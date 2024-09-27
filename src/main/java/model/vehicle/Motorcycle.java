package model.vehicle;

import enums.VehicleType;
import model.agency.Agency;

import java.math.BigDecimal;

public class Motorcycle extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(100.00);

    public Motorcycle(String id, String plate, String model, String brand, Agency agency) {
        super(id, plate, model, brand, agency, VehicleType.MOTORCYCLE);
    }

    @Override
    public BigDecimal getDailyRate() {
        return DAILY_RATE;
    }
}
