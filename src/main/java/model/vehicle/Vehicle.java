package model.vehicle;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Vehicle {
    private String id;
    private String plate;
    private String model;
    private String brand;
    private boolean available;
    private String agencyId;


    public Vehicle(String id, String plate, String model, String brand, String agencyId) {
        this.id = id;
        this.plate = plate;
        this.model = model;
        this.brand = brand;
        this.available = true;
        this.agencyId = agencyId;

    }
    public Boolean isAvailable() {
        return available;
    }

    public abstract BigDecimal getDailyRate();

    public final BigDecimal calculateRentalPrice(int rentalDays) {
        if(rentalDays < 0) throw new IllegalArgumentException("Dias alugados não podem ser negativos");

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
    public String getAgencyId() {
        return agencyId;
    }


}
