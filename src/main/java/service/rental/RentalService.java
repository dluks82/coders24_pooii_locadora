package service.rental;

import model.agency.Agency;
import model.customer.Customer;
import model.rental.Rental;

import java.time.LocalDate;
import java.util.List;

import dto.CreateRentalDTO;

public interface RentalService {

    Rental createRental(CreateRentalDTO rentalDto);

    Rental closeRental(Rental rentalToClose, Agency returnAgency, LocalDate actualReturnDate);

    Rental updateRental(Rental rental);

    Rental findRentalById(String id);

    List<Rental> findAllRentals();

    List<Rental> findRentalByCustomer(Customer customer);

    List<Rental> findOpenRentals();

    List<Rental> findClosedRentals();

}
