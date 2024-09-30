package service.rental;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import dto.CreateRentalDTO;
import model.agency.Agency;
import model.customer.Customer;
import model.rental.Rental;
import repository.rental.RentalRepository;

public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
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
        //TODO: acho que precisamos salvar os dados de veículo para garantir integridade
        rentalRepository.save(newRental);
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

        existingRental.getVehicle().setAvailable(true);
        //TODO: acho que precisamos salvar os dados de veículo para garantir integridade
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
