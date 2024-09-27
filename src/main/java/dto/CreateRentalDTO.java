package dto;

import java.time.LocalDate;

import model.agency.Agency;
import model.customer.Customer;
import model.vehicle.Vehicle;

public record CreateRentalDTO(
        Customer customer,
        Vehicle vehicle,
        Agency pickUpAgency,
        LocalDate pickUpDate,
        LocalDate estimatedReturnDate
) {
}
