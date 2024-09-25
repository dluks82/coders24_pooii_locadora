//import java.time.LocalDateTime;
//
//import dto.CreateRentalDTO;
//import dto.CreateVehicleDTO;
//import model.agency.Agency;
//import model.customer.Customer;
//import model.customer.Individual;
//import model.vehicle.Car;
//import model.vehicle.Vehicle;
//import repository.rental.InMemoryRentalRepository;
//import repository.vehicle.InMemoryVehicleRepository;
//import repository.rental.RentalRepository;
//import repository.vehicle.VehicleRepository;
//import service.rental.RentalService;
//import service.rental.RentalServiceImpl;
//import service.vehicle.VehicleService;
//import service.vehicle.VehicleServiceImpl;
//
//public class Main {
//    public static void main(String[] args) {
//
//        VehicleRepository vehicleRepository = new InMemoryVehicleRepository();
//
//        VehicleService vehicleService = new VehicleServiceImpl(vehicleRepository);
//
//        String id = "id";
//        String type = "car";
//        String plate = "AAA-999";
//        String model = "Gol";
//        String brand = "Volkswagem";
//        String agencyId = "Batata";
//
//        CreateVehicleDTO createVehicleDTO = new CreateVehicleDTO(
//                type, id, plate, model, brand, agencyId
//        );
//
//        try {
//            vehicleService.createVehicle(createVehicleDTO);
//            vehicleService.createVehicle(createVehicleDTO);
//        } catch (IllegalArgumentException exception) {
//            System.out.println(exception.getMessage());
//        }
//        //-----------------------------------------------
//        RentalRepository rentalRepository =  new InMemoryRentalRepository();
//
//        RentalService rentalService = new RentalServiceImpl(rentalRepository);
//
//        String rentalId = "ID";
//        //
//        String costumerId = "costumerID";
//        String costumerName = "costumerName";
//        String costumerNumberPhone = "costumerPhone";
//        String costumerDocumentId = "costumerDocumentId";
//        Customer rentalCustomer = new Individual(costumerId, costumerName, costumerNumberPhone, costumerDocumentId);
//        //
//        String vehicleId = "vehicleId";
//        String vehicleType = "car";
//        String vehiclePlate = "BBB-888";
//        String vehicleModel = "Onix";
//        String vehicleBrand = "Chevrolet";
//        String vehicleAgencyId = "agencyId";
//        Vehicle rentalVehicle = new Car(vehicleId, vehiclePlate, vehicleModel, vehicleBrand, vehicleAgencyId);
//        //
//        String agencyAgencyId = "agencyId";
//        String agencyName = "agencyName";
//        String agencyAddress = "agencyAddress";
//        String agencyPhone = "agencyPhone";
//        Agency agencyRentalPickUpAgency = new Agency(agencyAgencyId, agencyName, agencyAddress, agencyPhone);
//        //
//        LocalDateTime rentalPickUpDate = LocalDateTime.of(2024, 9, 20, 15, 30);
//        //
//        Agency rentalReturnAgency = new Agency(agencyAgencyId, agencyName, agencyAddress, agencyPhone);
//        //
//        LocalDateTime rentalEstimatedReturnDate = LocalDateTime.of(2024, 9, 25, 15, 30);
//        //
//        LocalDateTime rentalActualReturnDate = LocalDateTime.now();
//
//        CreateRentalDTO createRentalDTO =  new CreateRentalDTO(rentalId, rentalCustomer, rentalVehicle, agencyRentalPickUpAgency, rentalPickUpDate, rentalReturnAgency, rentalEstimatedReturnDate, rentalActualReturnDate);
//
//        try{
//            rentalService.createRental(createRentalDTO);
//            rentalService.createRental(createRentalDTO);
//        }catch(IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//}
