package repository;

import model.agency.Agency;
import model.vehicle.Vehicle;

import java.util.List;

public interface VehicleRepository extends Repository<Vehicle> {

    Vehicle findByPlate(String plate);

    List<Vehicle> findByModel(String model);

    List<Vehicle> findByBrand(String brand);

    List<Vehicle> findByAgency(Agency agency);

    List<Vehicle> getVehicles();

}
