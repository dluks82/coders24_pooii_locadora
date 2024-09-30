package service.vehicle;

import dto.CreateVehicleDTO;
import model.vehicle.Car;
import model.vehicle.Motorcycle;
import model.vehicle.Truck;
import model.vehicle.Vehicle;
import repository.vehicle.VehicleRepository;
import enums.VehicleType;

import java.util.List;
import java.util.UUID;

public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle createVehicle(CreateVehicleDTO vehicleDTO) {

        Vehicle existVehicle = vehicleRepository.findByPlate(vehicleDTO.plate());

        if (existVehicle != null) throw new IllegalArgumentException("Veículo já existe!");

        Vehicle newVehicle = null;

//        String newVehicleId = vehicleRepository.generateId();
        String newVehicleId = UUID.randomUUID().toString();

        if (vehicleDTO.type() == VehicleType.CAR) {
            newVehicle = new Car(newVehicleId,
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agency()
            );
        } else if (vehicleDTO.type() == VehicleType.TRUCK) {
            newVehicle = new Truck(newVehicleId,
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agency());
        } else if (vehicleDTO.type() == VehicleType.MOTORCYCLE) {
            newVehicle = new Motorcycle(newVehicleId,
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agency());
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
        if (vehicleFoundByPlate != null && !vehicleFoundByPlate.getId().equals(vehicle.getId())) {
            throw new IllegalArgumentException("Placa já existe!");
        }
        return vehicleRepository.update(vehicle);
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
    public List<Vehicle> findVehicleByAgencyId(String agencyId) {
        return vehicleRepository.findByAgencyId(agencyId);
    }

    @Override
    public List<Vehicle> findAvailableVehiclesByAgencyId(String agencyId) {
        return vehicleRepository.findAvailableVehiclesByAgencyId(agencyId);
    }
}
