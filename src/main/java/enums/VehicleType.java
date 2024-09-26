package enums;

public enum VehicleType {
    CAR("Carro"),
    TRUCK("Caminhão"),
    MOTORCYCLE("Moto");

    private final String description;

    VehicleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
