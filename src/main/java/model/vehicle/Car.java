package model.vehicle;

import enums.VehicleType;
import model.agency.Agency;

import java.math.BigDecimal;

public class Car extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(150.00);

    public Car(String id, String plate, String model, String brand, Agency agency) {
        super(id, plate, model, brand, agency, VehicleType.CAR);
    }

    public BigDecimal getDailyRate() {
        return DAILY_RATE;
    }
}
