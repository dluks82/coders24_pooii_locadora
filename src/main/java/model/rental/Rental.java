package model.rental;

import java.time.format.DateTimeFormatter;

import java.math.BigDecimal;
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
    private final LocalDate pickUpDate;
    private Agency returnAgency;
    private final LocalDate estimatedReturnDate;
    private LocalDate actualReturnDate;

    //constructor
    public Rental(String id, Customer customer, Vehicle vehicle, Agency pickUpAgency, LocalDate pickUpDate,
                  LocalDate estimatedReturnDate) {
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

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Agency getPickUpAgency() {
        return pickUpAgency;
    }

    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public Agency getReturnAgency() {
        return returnAgency;
    }

    public LocalDate getEstimatedReturnDate() {
        return estimatedReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public void setReturnAgency(Agency returnAgency) {
        this.returnAgency = returnAgency;
    }

    // class methods
    public BigDecimal calculateTotalCost() {
        BigDecimal big;
        LocalDate dataEntrega =
                this.actualReturnDate == null
                        ? this.estimatedReturnDate: this.actualReturnDate;
        Period period = Period.between(this.pickUpDate, dataEntrega);
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

    public String generatePickupReceipt() {

        return "========== RECIBO DE ALUGUEL ==========\n" +
                "Cliente: " + customer.getName() + "\n" +
                "Telefone: " + customer.getPhoneNumber() + "\n" +
                "Documento: " + customer.getDocumentId() + "\n" +
                "Tipo de Cliente: " + customer.getType() + "\n\n" +
                "=== DETALHES DO VEÍCULO ===\n" +
                "Modelo: " + vehicle.getModel() + "\n" +
                "Marca: " + vehicle.getBrand() + "\n" +
                "Placa: " + vehicle.getPlate() + "\n\n" +
                "=== AGÊNCIA DE RETIRADA ===\n" +
                "Nome: " + pickUpAgency.getName() + "\n" +
                "Endereço: " + pickUpAgency.getAddress() + "\n" +
                "Telefone: " + pickUpAgency.getPhone() + "\n" +
                "Data de Retirada: " + pickUpDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "=======================================\n";
    }

    public String generateReturnReceipt() {

        return "========== RECIBO DE DEVOLUÇÃO ==========\n" +
                "Cliente: " + customer.getName() + "\n" +
                "Telefone: " + customer.getPhoneNumber() + "\n" +
                "Documento: " + customer.getDocumentId() + "\n" +
                "Tipo de Cliente: " + customer.getType() + "\n\n" +
                "=== DETALHES DO VEÍCULO ===\n" +
                "Modelo: " + vehicle.getModel() + "\n" +
                "Marca: " + vehicle.getBrand() + "\n" +
                "Placa: " + vehicle.getPlate() + "\n\n" +
                "=== AGÊNCIA DE DEVOLUÇÃO ===\n" +
                "Nome: " + returnAgency.getName() + "\n" +
                "Endereço: " + returnAgency.getAddress() + "\n" +
                "Telefone: " + returnAgency.getPhone() + "\n" +
                "Data Estimada de Devolução: "
                + estimatedReturnDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                "Data Real de Devolução: "
                + (actualReturnDate != null
                ? actualReturnDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A") + "\n\n" +
                "=== VALOR DO ALUGUEL ===\n" +
                "Valor Total: R$ " + calculateTotalCost() + "\n" +
                "==========================================\n";
    }
}
