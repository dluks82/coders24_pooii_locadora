package repository;

import model.customer.Customer;
import model.rental.Rental;
import model.vehicle.Car;

import java.util.List;

public interface RentalRepository extends Repository<Rental> {

    List<Rental> findByCustomer(Customer customer);

    List<Rental> findByCar(Car car);

    List<Rental> findOpenRentals();

}
