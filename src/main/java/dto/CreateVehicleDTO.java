package dto;

import enums.VehicleType;
import model.agency.Agency;

public record CreateVehicleDTO(
        VehicleType type,
        String plate,
        String model,
        String brand,
        Agency agency
) {
}
