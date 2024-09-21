import dto.CreateVehicleDTO;
import repository.InMemoryVehicleRepository;
import repository.VehicleRepository;
import service.VehicleService;
import service.VehicleServiceImpl;

public class Main {
    public static void main(String[] args) {

        VehicleRepository vehicleRepository = new InMemoryVehicleRepository();

        VehicleService vehicleService = new VehicleServiceImpl(vehicleRepository);

        String id = "id";
        String type = "car";
        String plate = "AAA-999";
        String model = "Gol";
        String brand = "Volkswagem";
        String agencyId = "Batata";

        CreateVehicleDTO createVehicleDTO = new CreateVehicleDTO(
                type, id, plate, model, brand, agencyId
        );

        try {
            vehicleService.createVehicle(createVehicleDTO);
            vehicleService.createVehicle(createVehicleDTO);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

    }
}
