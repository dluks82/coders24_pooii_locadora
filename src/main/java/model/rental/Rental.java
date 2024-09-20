package model.rental;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import model.agency.Agency;
import model.customer.Customer;
import model.vehicle.Car;
import model.vehicle.Vehicle;
import repository.RentalRepository;

public class Rental implements RentalRepository{

    private String id;
    private Customer customer;
    private Vehicle vehicle;
    private Agency pickUpAgency;
    private LocalDateTime pickUpDate;
    private Agency returnAgency;
    private LocalDateTime returnDate;
    private int rentalDays;

    // getters ans setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Agency getPickUpAgency() {
        return pickUpAgency;
    }
    public void setPickUpAgency(Agency pickUpAgency) {
        this.pickUpAgency = pickUpAgency;
    }

    public LocalDateTime getPickUpDate() {
        return pickUpDate;
    }
    public void setPickUpDate(LocalDateTime pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Agency getReturnAgency() {
        return returnAgency;
    }
    public void setReturnAgency(Agency returnAgency) {
        this.returnAgency = returnAgency;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public int getRentalDays() {
        return rentalDays;
    }
    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }
    
    // class methods
    public BigDecimal calculateTotalCost() {
        BigDecimal big = new BigDecimal(0);
        return big;
    } 

    public void seiActualReturnDate(LocalDateTime actualReturnDate) { }

    public String generatePickupReceipt() {
        return "retorno";
    }

    public String generateReturnReceipt() {
        return "retorno";
    }

    // interface methods
    @Override
    public Rental save(Rental entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
    @Override
    public Rental update(Rental entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public boolean delete(Rental entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    @Override
    public Rental findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    @Override
    public List<Rental> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    @Override
    public List<Rental> findByCustomer(Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCustomer'");
    }
    @Override
    public List<Rental> findByCar(Car car) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCar'");
    }
    @Override
    public List<Rental> findOpenRentals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOpenRentals'");
    }
    
}
