package service;

import model.customer.Customer;
import model.rental.Rental;

import java.util.List;

public interface RentalService {

    Rental createRental(Rental rental);

    Rental updateRental(Rental rental);

    boolean deleteRental(Rental rental);

    Rental findRentalById(String id);

    List<Rental> findAllRentals();

    List<Rental> findRentalByCustomer(Customer customer);

    List<Rental> findOpenRentals();

}
