package model.vehicle;

import java.math.BigDecimal;

public class Car extends Vehicle {
    final BigDecimal DAILY_RATE = BigDecimal.valueOf(120.00);

    @Override
    public BigDecimal calculateRentalPrice(BigDecimal daysRented) {
        return DAILY_RATE.multiply(daysRented);
    }

}
