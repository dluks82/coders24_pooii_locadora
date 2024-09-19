package service;

import model.vehicle.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle createVehicle(Vehicle vehicle);

    Vehicle updateVehicle(Vehicle vehicle);

    boolean deleteVehicle(Vehicle vehicle);

    Vehicle findVehicleById(String id);

    List<Vehicle> findAllVehicles();

    List<Vehicle> findVehicleByPlate(String plate);

    List<Vehicle> findVehicleByModel(String model);

    List<Vehicle> findVehicleByBrand(String brand);

    List<Vehicle> findVehicleByAgency(String agency);

}
