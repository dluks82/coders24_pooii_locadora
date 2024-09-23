package service;

import dto.CreateVehicleDTO;
import model.vehicle.Car;
import model.vehicle.Motorcycle;
import model.vehicle.Truck;
import model.vehicle.Vehicle;
import repository.InMemoryVehicleRepository;
import repository.VehicleRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        if (vehicleDTO.type().equalsIgnoreCase("car")) {
            newVehicle = new Car(vehicleDTO.id(),
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agencyId()
            );
        }else if (vehicleDTO.type().equalsIgnoreCase("truck")) {
            newVehicle = new Truck(vehicleDTO.id(),
                    vehicleDTO.plate(),
                    vehicleDTO.model(),
                    vehicleDTO.brand(),
                    vehicleDTO.agencyId());
        }else if (vehicleDTO.type().equalsIgnoreCase("motorcycle")) {
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

        if (vehicle.getId() == null) {
            System.out.println("Veículo ID, " + vehicle.getId() + " não localizado.");
            return null;
        }else {
            Scanner stdin = new Scanner(System.in);
            for(Vehicle searchVehicle: vehicleRepository.getVehicles()){
                if (searchVehicle.getId().equals(vehicle.getId())){
                    System.out.println("Digite o tipo: ");
                    searchVehicle.setType(stdin.nextLine());
                    System.out.println("Digite a placa: ");
                    searchVehicle.setPlate(stdin.nextLine());
                    System.out.println("Digite o modelo: ");
                    searchVehicle.setModel(stdin.nextLine());
                    System.out.println("Digite a marca: ");
                    searchVehicle.setBrand(stdin.nextLine());
                    searchVehicle.setAgencyId(vehicle.getAgencyId());
                    System.out.println("Está disponível? S/N");
                    String availability = stdin.nextLine();
                    boolean isAvailable = true;
                    boolean done = true;
                    while (done) {
                        if (availability.equalsIgnoreCase("s")) {
                            searchVehicle.setAvailable(isAvailable);
                            done = false;
                        }else if (availability.equalsIgnoreCase("n")) {
                            searchVehicle.setAvailable(!isAvailable);
                            done = false;
                        }else{
                            //throw new IllegalArgumentException("Apenas 's' ou 'n'.");
                            System.out.println("Apenas 's' ou 'n'.");
                        }
                    }
                    System.out.println("Veículo ID, " + vehicle.getId() + " atualizado.");
                    return searchVehicle;
                }
            }
            stdin.close();
        }
        return null;
    }

    @Override
    public boolean deleteVehicle(Vehicle vehicle) {
        return false;
    }

    @Override
    public Vehicle findVehicleById(String id) {
        for (Vehicle vehicle : vehicleRepository.getVehicles()) {
            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return List.of();
    }

    @Override
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle vehicle : vehicleRepository.getVehicles()) {
            if (vehicle.getPlate().equals(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> findVehicleByModel(String model) {
        List<Vehicle> vehiclesFound = new ArrayList<>();
        for (Vehicle vehicle : vehicleRepository.getVehicles()) {
            if (vehicle.getModel().equalsIgnoreCase(model)) {
                vehiclesFound.add(vehicle);
            }
        }
        return vehiclesFound;
    }

    @Override
    public List<Vehicle> findVehicleByBrand(String brand) {
        List<Vehicle> vehiclesFound = new ArrayList<>();
        for (Vehicle vehicle : vehicleRepository.getVehicles()) {
            if (vehicle.getBrand().equalsIgnoreCase(brand)) {
                vehiclesFound.add(vehicle);
            }
        }
        return vehiclesFound;
    }

    @Override
    public List<Vehicle> findVehicleByAgencyId(String agencyId) {
        List<Vehicle> vehiclesFound = new ArrayList<>();
        for (Vehicle vehicle : vehicleRepository.getVehicles()) {
            if (vehicle.getAgencyId().equalsIgnoreCase(agencyId)) {
                vehiclesFound.add(vehicle);
            }
        }
        return vehiclesFound;
    }
}
