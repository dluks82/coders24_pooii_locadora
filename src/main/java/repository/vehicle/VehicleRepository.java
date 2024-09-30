package repository.vehicle;

import model.vehicle.Vehicle;
import repository.Repository;

import java.util.List;

public interface VehicleRepository extends Repository<Vehicle> {

    void saveData();

    Vehicle findByPlate(String plate);

    List<Vehicle> findByModel(String model);

    List<Vehicle> findByAgencyId(String agencyId);

    List<Vehicle> findAvailableVehiclesByAgencyId(String agencyId);
}
