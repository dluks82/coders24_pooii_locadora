package repository.vehicle;

import model.vehicle.Vehicle;
import repository.Repository;

import java.util.List;

public interface VehicleRepository extends Repository<Vehicle> {

    Vehicle findByPlate(String plate);

    List<Vehicle> findByModel(String model);

    List<Vehicle> findByBrand(String brand);

    List<Vehicle> findByAgencyId(String agency);


}
