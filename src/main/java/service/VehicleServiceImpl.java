package service;

import dto.CreateVehicleDTO;
import model.vehicle.Car;
import model.vehicle.Vehicle;
import repository.VehicleRepository;

import java.math.BigDecimal;
import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle createVehicle(CreateVehicleDTO vehicleDTO) {

        Vehicle existVehicle = vehicleRepository.findByPlate(vehicleDTO.plate());

        if (existVehicle != null) throw new IllegalArgumentException("Veículo já existe!");

        Vehicle newVehicle = null;

        if (vehicleDTO.type().equals("car")) {
            newVehicle = new Car(vehicleDTO.id(),
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agencyId()
            );
        }

        if (newVehicle != null) {
            vehicleRepository.save(newVehicle);
            return newVehicle;
        }

        return null;
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        return null;
    }

    @Override
    public boolean deleteVehicle(Vehicle vehicle) {
        return false;
    }

    @Override
    public Vehicle findVehicleById(String id) {
        return null;
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return List.of();
    }

    @Override
    public Vehicle findVehicleByPlate(String plate) {
        return null;
    }

    @Override
    public List<Vehicle> findVehicleByModel(String model) {
        return List.of();
    }

    @Override
    public List<Vehicle> findVehicleByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Vehicle> findVehicleByAgencyId(String agencyId) {
        return List.of();
    }
}
