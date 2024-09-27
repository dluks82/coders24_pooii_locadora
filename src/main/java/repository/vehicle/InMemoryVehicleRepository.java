package repository.vehicle;

import model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        for(int i=0; i<vehicles.size(); i++){
            if(Objects.equals(vehicles.get(i).getId(), entity.getId())){
                vehicles.set(i, entity);
                return entity;
            }
        }
        return null;
    }

    @Override
    public Vehicle findById(String id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicles;
    }

    @Override
    public Vehicle findByPlate(String plate) {
        for (Vehicle vehicle : vehicles) {
            if(vehicle.getPlate().equalsIgnoreCase(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> findByModel(String model) {
        List<Vehicle> vehiclesFoundByModel = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if(vehicle.getModel().toLowerCase().contains(model.toLowerCase())) {
                vehiclesFoundByModel.add(vehicle);
            }
        }
        return vehiclesFoundByModel;
    }

    @Override
    public List<Vehicle> findByAgencyId(String agencyId) {
        List<Vehicle> vehiclesFound = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getAgency().getId().equalsIgnoreCase(agencyId)) {
                vehiclesFound.add(vehicle);
            }
        }
        return vehiclesFound;
    }
}
