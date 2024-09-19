package model.vehicle;

import java.math.BigDecimal;

public abstract class Vehicle {
    private String id;
    private String plate;
    private String model;
    private String brand;
    private Boolean isAvailable;
    private String agencyId;

    public abstract BigDecimal calculateRentalPrice(BigDecimal daysRented);

}
