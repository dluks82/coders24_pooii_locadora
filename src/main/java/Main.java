import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dto.CreateRentalDTO;
import dto.CreateVehicleDTO;
import model.agency.Agency;
import model.customer.Customer;
import model.customer.Individual;
import model.vehicle.Car;
import model.vehicle.Vehicle;
import repository.InMemoryRentalRepository;
import repository.InMemoryVehicleRepository;
import repository.RentalRepository;
import repository.VehicleRepository;
import service.RentalService;
import service.RentalServiceImpl;
import service.VehicleService;
import service.VehicleServiceImpl;
import utils.VehicleType;

public class Main {
    public static void main(String[] args) {
        //Scanner stdin = new Scanner(System.in);

        VehicleRepository vehicleRepository = new InMemoryVehicleRepository();

        VehicleService vehicleService = new VehicleServiceImpl(vehicleRepository);

        String id = "id";
        VehicleType type = VehicleType.CAR;
        String plate = "AAA-999";
        String model = "Gol";
        String brand = "LAMBO";
        String agencyId = "Potato";


        CreateVehicleDTO createVehicleDTO = new CreateVehicleDTO(type, id, plate, model, brand, agencyId);

        id = "id1FOUNDED";
        plate = "AAA-998";
        model = "Golew";
        brand = "Volkswagem";
        agencyId = "Batata";

        CreateVehicleDTO createVehicleDTO1 = new CreateVehicleDTO(type, id, plate, model, brand, agencyId);

        vehicleService.createVehicle(createVehicleDTO);
        vehicleService.createVehicle(createVehicleDTO1);

/*
        -----------------------------------------------

    }
    RentalRepository rentalRepository =  new InMemoryRentalRepository();

        RentalService rentalService = new RentalServiceImpl(rentalRepository);

        String rentalId = "ID";
        //
        String costumerId = "costumerID";
        String costumerName = "costumerName";
        String costumerNumberPhone = "costumerPhone";
        String costumerDocumentId = "costumerDocumentId";
        Customer rentalCustomer = new Individual(costumerId, costumerName, costumerNumberPhone, costumerDocumentId);
        //
        String vehicleId = "vehicleId";
        String vehicleType = "car";
        String vehiclePlate = "BBB-888";
        String vehicleModel = "Onix";
        String vehicleBrand = "Chevrolet";
        String vehicleAgencyId = "agencyId";
        Vehicle rentalVehicle = new Car(vehicleId, vehiclePlate, vehicleModel, vehicleBrand, vehicleAgencyId);
        //
        String agencyAgencyId = "agencyId";
        String agencyName = "agencyName";
        String agencyAddress = "agencyAddress";
        String agencyPhone = "agencyPhone";
        Agency agencyRentalPickUpAgency = new Agency(agencyAgencyId, agencyName, agencyAddress, agencyPhone);
        //
        LocalDateTime rentalPickUpDate = LocalDateTime.of(2024, 9, 20, 15, 30);
        //
        Agency rentalReturnAgency = new Agency(agencyAgencyId, agencyName, agencyAddress, agencyPhone);
        //
        LocalDateTime rentalEstimatedReturnDate = LocalDateTime.of(2024, 9, 25, 15, 30);
        //
        LocalDateTime rentalActualReturnDate = LocalDateTime.now();

        CreateRentalDTO createRentalDTO =  new CreateRentalDTO(rentalId, rentalCustomer, rentalVehicle, agencyRentalPickUpAgency, rentalPickUpDate, rentalReturnAgency, rentalEstimatedReturnDate, rentalActualReturnDate);

        try{
            rentalService.createRental(createRentalDTO);
            rentalService.createRental(createRentalDTO);
        }catch(IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

 */

    }
}
