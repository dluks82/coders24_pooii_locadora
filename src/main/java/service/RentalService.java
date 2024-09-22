package service;

import model.customer.Customer;
import model.rental.Rental;

import java.util.List;

import dto.CreateRentalDTO;

public interface RentalService {

    Rental createRental(CreateRentalDTO rentalDto);

    Rental updateRental(Rental rental);

    boolean deleteRental(Rental rental);

    Rental findRentalById(String id);

    List<Rental> findAllRentals();

    List<Rental> findRentalByCustomer(Customer customer);

    List<Rental> findOpenRentals();

}
