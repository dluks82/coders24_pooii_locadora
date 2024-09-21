package repository;

import model.agency.Agency;
import model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class InMemoryVehicleRepository implements VehicleRepository {

    private List<Vehicle> vehicles;

    public InMemoryVehicleRepository() {
        this.vehicles = new ArrayList<>();
    }

    @Override
    public Vehicle save(Vehicle entity) {
        vehicles.add(entity);

        return entity;
    }

    @Override
    public Vehicle update(Vehicle entity) {
        return null;
    }

    @Override
    public boolean delete(Vehicle entity) {
        return false;
    }

    @Override
    public Vehicle findById(String id) {
        return null;
    }

    @Override
    public List<Vehicle> findAll() {
        return List.of();
    }

    @Override
    public Vehicle findByPlate(String plate) {
        for (Vehicle vehicle : vehicles) {
            if(vehicle.getPlate().equals(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> findByModel(String model) {
        return List.of();
    }

    @Override
    public List<Vehicle> findByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Vehicle> findByAgency(Agency agency) {
        return List.of();
    }
}
