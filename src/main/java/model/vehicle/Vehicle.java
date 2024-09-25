package model.vehicle;
import enums.VehicleType;
import java.math.BigDecimal;

public abstract class Vehicle {
    private String id;
    private String plate;
    private String model;
    private String brand;
    private boolean available;
    private String agencyId;
    private VehicleType type;


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

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public VehicleType getType() {
        return type;
    }
}
