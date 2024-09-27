package model.vehicle;

import enums.VehicleType;
import model.agency.Agency;

import java.math.BigDecimal;

public class Truck extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(200.00);

    public Truck(String id, String plate, String model, String brand, Agency agency) {
        super(id, plate, model, brand, agency, VehicleType.TRUCK);
    }

    public BigDecimal getDailyRate() {
        return DAILY_RATE;
    }
}
