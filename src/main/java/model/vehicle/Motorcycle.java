package model.vehicle;

import java.math.BigDecimal;

public class Motorcycle extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(50.00);

    @Override
    public BigDecimal calculateRentalPrice(BigDecimal daysRented) {
        return DAILY_RATE.multiply(daysRented);
    }

}
