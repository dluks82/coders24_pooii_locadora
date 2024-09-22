package model.rental;

import java.math.BigDecimal;
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
    private LocalDateTime estimatedReturnDate;
    private LocalDateTime actualReturnDate;

    //constructor
    public Rental(String id, Customer customer, Vehicle vehicle, Agency pickUpAgency, LocalDateTime pickUpDate,
            Agency returnAgency, LocalDateTime estimatedReturnDate, LocalDateTime actualReturnDate) {
        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.pickUpAgency = pickUpAgency;
        this.pickUpDate = pickUpDate;
        this.returnAgency = returnAgency;
        this.estimatedReturnDate = estimatedReturnDate;
        this.actualReturnDate = actualReturnDate;
    }   

    //getters and setters
    public String getId() {
        return id;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    // class methods
    public BigDecimal calculateTotalCost() {
        BigDecimal big = new BigDecimal(0);
        return big;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) { }

    public String generatePickupReceipt() {
        return "retorno";
    }

    public String generateReturnReceipt() {
        return "retorno";
    }
    
}
