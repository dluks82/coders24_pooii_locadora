package service;

import dto.CreateVehicleDTO;
import model.vehicle.Car;
import model.vehicle.Motorcycle;
import model.vehicle.Truck;
import model.vehicle.Vehicle;
import repository.VehicleRepository;
import utils.VehicleType;
import java.util.ArrayList;
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

        if (vehicleDTO.type()== VehicleType.CAR) {
            newVehicle = new Car(vehicleDTO.id(),
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agencyId()
            );
        }else if (vehicleDTO.type() == VehicleType.TRUCK) {
            newVehicle = new Truck(vehicleDTO.id(),
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agencyId());
        }else if (vehicleDTO.type() == VehicleType.MOTORCYCLE) {
            newVehicle = new Motorcycle(vehicleDTO.id(),
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agencyId());
        }

        if (newVehicle != null) {
            vehicleRepository.save(newVehicle);
            return newVehicle;
        }

        return null;
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        Vehicle vehicleFoundByPlate = vehicleRepository.findByPlate(vehicle.getPlate());
        if(vehicleFoundByPlate != null && !vehicleFoundByPlate.getId().equals(vehicle.getId())) {
            throw new IllegalArgumentException("Placa já existe!");
        }
        return vehicleRepository.update(vehicle);
    }

    @Override
    public boolean deleteVehicle(Vehicle vehicle) {

        return false;
    }

    @Override
    public Vehicle findVehicleById(String id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle findVehicleByPlate(String plate) {
            return vehicleRepository.findByPlate(plate);
    }

    @Override
    public List<Vehicle> findVehicleByModel(String model) {
        return vehicleRepository.findByModel(model);
    }

    @Override
    public List<Vehicle> findVehicleByBrand(String brand) {
        //quantidade de letras
        return vehicleRepository.findByBrand(brand);
    }

    @Override
    public List<Vehicle> findVehicleByAgencyId(String agencyId) {
        return vehicleRepository.findByAgencyId(agencyId);
    }
}
