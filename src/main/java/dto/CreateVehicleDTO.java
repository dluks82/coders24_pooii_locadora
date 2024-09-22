package dto;

public record CreateVehicleDTO(
        String type,
        String id,
        String plate,
        String model,
        String brand,
        String agencyId
) {
}
