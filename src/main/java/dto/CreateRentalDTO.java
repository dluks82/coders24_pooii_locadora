package dto;

import java.time.LocalDateTime;

import model.agency.Agency;
import model.customer.Customer;
import model.vehicle.Vehicle;

public record CreateRentalDTO (
        String id,
        Customer customer,
        Vehicle vehicle,
        Agency pickUpAgency,
        LocalDateTime pickUpDate,
        Agency returnAgency,
        LocalDateTime estimatedReturnDate,
        LocalDateTime actualReturnDate
)
{ }