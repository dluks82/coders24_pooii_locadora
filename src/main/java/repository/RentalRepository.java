package repository;

import model.customer.Customer;
import model.rental.Rental;

import java.util.List;

public interface RentalRepository extends Repository<Rental> {

    List<Rental> findByCustomer(Customer customer);

    List<Rental> findOpenRentals();

}
