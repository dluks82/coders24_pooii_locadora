package model.vehicle;

import java.math.BigDecimal;

public class Truck extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(300.00);

    @Override
    public BigDecimal calculateRentalPrice(BigDecimal daysRented) {
        return DAILY_RATE.multiply(daysRented);
    }

}
