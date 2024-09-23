package dto;

import utils.VehicleType;

public record CreateVehicleDTO(
        VehicleType type,
        String id,
        String plate,
        String model,
        String brand,
        String agencyId
) {
}
