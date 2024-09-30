package service.rental;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import dto.CreateRentalDTO;
import model.agency.Agency;
import model.customer.Customer;
import model.rental.Rental;
import repository.rental.RentalRepository;
import repository.vehicle.VehicleRepository;

public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, VehicleRepository vehicleRepository) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Rental createRental(CreateRentalDTO rentalDTO) {
        String rentalId = UUID.randomUUID().toString();
        Rental newRental = null;

        // verificar se o cliente já tem uma locação em aberto
        List<Rental> openRentals = rentalRepository.findOpenRentals();
        for (Rental rental : openRentals) {
            if (rental.getCustomer().getId().equals(rentalDTO.customer().getId())) {
                throw new IllegalArgumentException("Cliente já possui uma locação em aberto!");
            }
        }

        newRental = new Rental(rentalId, rentalDTO.customer(), rentalDTO.vehicle(), rentalDTO.pickUpAgency(), rentalDTO.pickUpDate(), rentalDTO.estimatedReturnDate());
        newRental.getVehicle().setAvailable(false);
        rentalRepository.save(newRental);

        // chamar o saveData do repositório de veículos
        vehicleRepository.saveData();

        return newRental;
    }

    @Override
    public Rental closeRental(Rental rentalToClose, Agency returnAgency, LocalDateTime actualReturnDate) {
        Rental existingRental = rentalRepository.findById(rentalToClose.getId());
        if (existingRental == null) throw new IllegalArgumentException("Locacao nao existe!");

        if (existingRental.getActualReturnDate() != null) {
            throw new IllegalArgumentException("Locacao ja foi fechada!");
        }

        // verificar se a data é maior que a data da locação
        if (actualReturnDate.isBefore(existingRental.getPickUpDate())) {
            throw new IllegalArgumentException("Data de devolução não pode ser menor que a data de locação!");
        }

        existingRental.setReturnAgency(returnAgency);
        existingRental.setActualReturnDate(actualReturnDate);

        // Atualizar estado do veículo
        existingRental.getVehicle().setAgency(returnAgency);
        existingRental.getVehicle().setAvailable(true);
        vehicleRepository.saveData();

        return rentalRepository.update(existingRental);
    }


    @Override
    public Rental updateRental(Rental rental) {
        Rental existingRental = rentalRepository.findById(rental.getId());
        if (existingRental == null) throw new IllegalArgumentException("Locacao nao existe!");
        rentalRepository.update(rental);
        return rental;
    }

    @Override
    public Rental findRentalById(String id) {
        if (id.isEmpty()) {
            throw new IllegalArgumentException("ID vazio ou nulo");
        }
        return rentalRepository.findById(id);
    }

    @Override
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public List<Rental> findRentalByCustomer(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Custumer is null");
        return rentalRepository.findByCustomer(customer);
    }

    @Override
    public List<Rental> findOpenRentals() {
        return rentalRepository.findOpenRentals();
    }

    @Override
    public List<Rental> findClosedRentals() {
        return rentalRepository.findClosedRentals();
    }
}
