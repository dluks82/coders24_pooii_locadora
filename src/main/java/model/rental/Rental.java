package model.rental;

import java.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Period;

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

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }

    // class methods
    public BigDecimal calculateTotalCost() {
        BigDecimal big = BigDecimal.ZERO;

        LocalDate dataSaida = this.pickUpDate.toLocalDate();
        LocalDate dataEntrega = this.actualReturnDate==null ? this.estimatedReturnDate.toLocalDate() : this.actualReturnDate.toLocalDate();
        Period periodo = Period.between(dataSaida, dataEntrega);
        int dias = periodo.getDays();
        int totalDias = periodo.getYears() * 365 + periodo.getMonths() * 30 + dias; // Aproximação

        if(this.getVehicle().getType().equals("CAR")) {
            if(this.customer.getType().equals("INDIVIDUAL")) {
                if (totalDias > 3) {
                    big = BigDecimal.valueOf(150).multiply(BigDecimal.valueOf(totalDias)).multiply(BigDecimal.valueOf(0.95));
                    return big;
                } else {
                    big = BigDecimal.valueOf(150).multiply(BigDecimal.valueOf(totalDias));
                    return big;
                }
            } else {
                if (totalDias > 5) {
                    big = BigDecimal.valueOf(150).multiply(BigDecimal.valueOf(totalDias)).multiply(BigDecimal.valueOf(0.90));
                    return big;
                } else {
                    big = BigDecimal.valueOf(150).multiply(BigDecimal.valueOf(totalDias));
                    return big;
                }
            }
        } else if (this.getVehicle().getType().equals("MOTORCYCLE")) {
            big = BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(totalDias));
            return big;
        } else if (this.getVehicle().getType().equals("TRUCK")) {
            big = BigDecimal.valueOf(200).multiply(BigDecimal.valueOf(totalDias));
            return big;
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
