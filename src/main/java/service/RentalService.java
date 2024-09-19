package service;

import model.agency.Agency;
import model.customer.Customer;
import model.rental.Rental;
import model.vehicle.Vehicle;

import java.util.List;

public interface RentalService {

    Rental createRental(Rental rental);

    Rental updateRental(Rental rental);

    boolean deleteRental(Rental rental);

    Rental findRentalById(String id);

    List<Rental> findAllRentals();

    List<Rental> findRentalByCustomer(Customer customer);

    List<Rental> findRentalByAgency(Agency agency);

    List<Rental> findRentalByVehicle(Vehicle vehicle);

    List<Rental> findOpenRentals();

    List<Rental> findClosedRentals();

}
