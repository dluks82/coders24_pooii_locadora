package model.rental;

import java.time.LocalDateTime;

import model.agency.Agency;
import model.customer.Customer;
import model.vehicle.Vehicle;

public class Rental {

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
}
