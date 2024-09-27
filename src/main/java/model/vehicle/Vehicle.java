package model.vehicle;

import enums.VehicleType;
import model.agency.Agency;

import java.math.BigDecimal;

public abstract class Vehicle {
    private final String id;
    private final String plate;
    private final String model;
    private final String brand;
    private boolean available;
    private final Agency agency;
    private final VehicleType type;

    public Vehicle(String id, String plate, String model, String brand, Agency agency, VehicleType type) {
        this.id = id;
        this.plate = plate;
        this.model = model;
        this.brand = brand;
        this.available = true;
        this.agency = agency;
        this.type = type;
    }

    public Boolean isAvailable() {
        return available;
    }

    public abstract BigDecimal getDailyRate();

    public final BigDecimal calculateRentalPrice(int rentalDays) {
        if (rentalDays < 0) throw new IllegalArgumentException("Dias alugados não podem ser negativos");

        return getDailyRate().multiply(new BigDecimal(rentalDays));
    }

    public String getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public Agency getAgencyId() {
        return agency;
    }

    public VehicleType getType() {
        return type;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
