package model.rental;

import java.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Period;

import enums.CustomerType;
import enums.VehicleType;
import model.agency.Agency;
import model.customer.Customer;
import model.vehicle.Vehicle;

public class Rental {

    private final String id;
    private final Customer customer;
    private final Vehicle vehicle;
    private final Agency pickUpAgency;
    private final LocalDateTime pickUpDate;
    private Agency returnAgency;
    private final LocalDateTime estimatedReturnDate;
    private LocalDateTime actualReturnDate;

    //constructor
    public Rental(String id, Customer customer, Vehicle vehicle, Agency pickUpAgency, LocalDateTime pickUpDate,
                  LocalDateTime estimatedReturnDate) {
        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.pickUpAgency = pickUpAgency;
        this.pickUpDate = pickUpDate;
        this.estimatedReturnDate = estimatedReturnDate;
    }

    //getters and setters
    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Agency getPickUpAgency() {
        return pickUpAgency;
    }

    public LocalDateTime getPickUpDate() {
        return pickUpDate;
    }

    public Agency getReturnAgency() {
        return returnAgency;
    }

    public LocalDateTime getEstimatedReturnDate() {
        return estimatedReturnDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public void setReturnAgency(Agency returnAgency) {
        this.returnAgency = returnAgency;
    }

    // class methods
    public BigDecimal calculateTotalCost() {
        BigDecimal big = BigDecimal.ZERO;

        LocalDate dataSaida = this.pickUpDate.toLocalDate();
        LocalDate dataEntrega =
                this.actualReturnDate == null
                        ? this.estimatedReturnDate.toLocalDate() : this.actualReturnDate.toLocalDate();
        Period period = Period.between(dataSaida, dataEntrega);
        int dias = period.getDays();
        int totalDias = period.getYears() * 365 + period.getMonths() * 30 + dias; // Aproximação

        if (this.getVehicle().getType() == VehicleType.CAR) {
            if (this.customer.getType() == CustomerType.INDIVIDUAL) {
                if (totalDias > 3) {
                    big = this.getVehicle().getDailyRate().multiply(BigDecimal.valueOf(totalDias))
                            .multiply(BigDecimal.valueOf(0.95));
                } else {
                    big = this.getVehicle().getDailyRate().multiply(BigDecimal.valueOf(totalDias));
                }
            } else {
                if (totalDias > 5) {
                    big = this.getVehicle().getDailyRate().multiply(BigDecimal.valueOf(totalDias))
                            .multiply(BigDecimal.valueOf(0.90));
                } else {
                    big = this.getVehicle().getDailyRate().multiply(BigDecimal.valueOf(totalDias));
                }
            }
            return big;
        } else {
            big = this.getVehicle().getDailyRate().multiply(BigDecimal.valueOf(totalDias));
        }
        return big;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) { 
        this.actualReturnDate = actualReturnDate;
    }

    public String generatePickupReceipt() {
        StringBuilder recibo = new StringBuilder();

        recibo.append("========== RECIBO DE ALUGUEL ==========\n")
              .append("Cliente: ").append(customer.getName()).append("\n")
              .append("Telefone: ").append(customer.getNumberPhone()).append("\n")
              .append("Documento: ").append(customer.getDocumentId()).append("\n")
              .append("Tipo de Cliente: ").append(customer.getType()).append("\n\n")
              .append("=== DETALHES DO VEÍCULO ===\n")
              .append("Modelo: ").append(vehicle.getModel()).append("\n")
              .append("Marca: ").append(vehicle.getBrand()).append("\n")
              .append("Placa: ").append(vehicle.getPlate()).append("\n\n")
              .append("=== AGÊNCIA DE RETIRADA ===\n")
              .append("Nome: ").append(pickUpAgency.getName()).append("\n")
              .append("Endereço: ").append(pickUpAgency.getAddress()).append("\n")
              .append("Telefone: ").append(pickUpAgency.getPhone()).append("\n")
              .append("Data de Retirada: ").append(pickUpDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n")
              .append("=======================================\n");

        return recibo.toString();
    }

    public String generateReturnReceipt() {
        StringBuilder recibo = new StringBuilder();

        recibo.append("========== RECIBO DE DEVOLUÇÃO ==========\n")
              .append("Cliente: ").append(customer.getName()).append("\n")
              .append("Telefone: ").append(customer.getNumberPhone()).append("\n")
              .append("Documento: ").append(customer.getDocumentId()).append("\n")
              .append("Tipo de Cliente: ").append(customer.getType()).append("\n\n")
              .append("=== DETALHES DO VEÍCULO ===\n")
              .append("Modelo: ").append(vehicle.getModel()).append("\n")
              .append("Marca: ").append(vehicle.getBrand()).append("\n")
              .append("Placa: ").append(vehicle.getPlate()).append("\n\n")
              .append("=== AGÊNCIA DE DEVOLUÇÃO ===\n")
              .append("Nome: ").append(returnAgency.getName()).append("\n")
              .append("Endereço: ").append(returnAgency.getAddress()).append("\n")
              .append("Telefone: ").append(returnAgency.getPhone()).append("\n")
              .append("Data Estimada de Devolução: ").append(estimatedReturnDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n")
              .append("Data Real de Devolução: ").append(actualReturnDate != null ? actualReturnDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/A").append("\n")
              .append("==========================================\n");

        return recibo.toString();
    }
}
