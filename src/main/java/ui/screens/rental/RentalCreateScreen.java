package ui.screens.rental;

import dto.CreateRentalDTO;
import model.agency.Agency;
import model.customer.Customer;
import model.vehicle.Vehicle;
import service.agency.AgencyService;
import service.customer.CustomerService;
import service.rental.RentalService;
import service.vehicle.VehicleService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.screens.agency.AgencyListScreen;
import ui.screens.customer.CustomerListScreen;
import ui.screens.vehicle.VehicleListScreen;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class RentalCreateScreen extends Screen {
    private final Scanner scanner;

    private final AgencyService agencyService;
    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final RentalService rentalService;

    private Agency selectedAgency;
    private Customer selectedCustomer;
    private Vehicle selectedVehicle;

    private LocalDateTime startDate;
    private LocalDateTime estimatedEndDate;

    private int rentalDays;

    private int currentField = 0;

    private boolean isAgencySelectionListCalled = false;
    private boolean isCustomerSelectionListCalled = false;
    private boolean isVehicleSelectionListCalled = false;

    public RentalCreateScreen(FlowController flowController,
                              Scanner scanner, AgencyService agencyService,
                              VehicleService vehicleService,
                              CustomerService customerService, RentalService rentalService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
        this.vehicleService = vehicleService;
        this.customerService = customerService;
        this.rentalService = rentalService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();

            ScreenUtils.showHeader("Cadastro de Locação");

            displayRentalRegistration();

            Output.info("'V' para voltar campo, 'C' para cancelar o cadastro.");

            switch (currentField) {
                case 0 -> {
                    if (!isAgencySelectionListCalled) {
                        isAgencySelectionListCalled = true;

                        AgencyListScreen agencyListScreen = new AgencyListScreen(flowController, scanner, agencyService, true);
                        flowController.goTo(agencyListScreen);

                        selectedAgency = agencyListScreen.getSelectedAgency();

                        flowController.goBack();
                    }

                    if (selectedAgency == null) {
                        Output.error("Você precisa selecionar uma agência válida!");

                        handleFieldSelectionError();
                        break;
                    }
                    currentField = 1;
                }
                case 1 -> {
                    if (!isVehicleSelectionListCalled) {
                        isVehicleSelectionListCalled = true;

                        VehicleListScreen vehicleListScreen = new VehicleListScreen(flowController, scanner, vehicleService, true);
                        flowController.goTo(vehicleListScreen);

                        selectedVehicle = vehicleListScreen.getSelectedVehicle();

                        flowController.goBack();
                    }

                    if (selectedVehicle == null) {
                        Output.error("Você precisa selecionar uma veículo válido!");


                        handleFieldSelectionError();
                        break;
                    }
                    currentField = 2;
                }
                case 2 -> {
                    if (!isCustomerSelectionListCalled) {
                        isCustomerSelectionListCalled = true;

                        CustomerListScreen customerListScreen = new CustomerListScreen(flowController, scanner, customerService, true);
                        flowController.goTo(customerListScreen);

                        selectedCustomer = customerListScreen.getSelectedCustomer();

                        flowController.goBack();
                    }

                    if (selectedVehicle == null) {
                        Output.error("Você precisa selecionar um cliente válido!");

                        handleFieldSelectionError();
                        break;
                    }
                    currentField = 3;
                }
                case 3 -> {
                    Result<Integer> rentalDaysInput = Input.getAsInt(scanner, "Dias de locação: ", false);
                    if (rentalDaysInput.getValue() <= 0) {
                        Output.error("O número de dias de locação deve ser maior que zero!");
                        break;
                    }

                    rentalDays = rentalDaysInput.getValue();

                    startDate = LocalDateTime.now();
                    estimatedEndDate = startDate.plusDays(rentalDays);

                    Output.info(String.format("Data de início: %s", startDate));
                    Output.info(String.format("Data estimada de término: %s", estimatedEndDate));
                    Output.info(String.format("Valor estimado: R$ %.2f", selectedVehicle.getDailyRate().multiply(BigDecimal.valueOf(rentalDays))));

                    currentField = 5;
                }

                case 5 -> confirmRegistration();
            }
        } while (true);
    }

    private void displayRentalRegistration() {

        String agencyPrompt = "Agência: ";
        String agencyValue = selectedAgency != null ? selectedAgency.getName() : "";
        String vehiclePrompt = "Veículo: ";
        String vehicleValue = selectedVehicle != null ? selectedVehicle.getModel() : "";
        String customerPrompt = "Cliente: ";
        String customerValue = selectedCustomer != null ? selectedCustomer.getName() : "";
        String startDatePrompt = "Data de início: ";
        String startDateValue = startDate != null ? startDate.toString() : "";
        String estimatedEndDatePrompt = "Data estimada de término: ";
        String estimatedEndDateValue = estimatedEndDate != null ? estimatedEndDate.toString() : "";
        String rentalCostPrompt = "Custo estimado da locação: ";
        String rentalCostValue = (selectedVehicle != null && estimatedEndDate != null)
                ? "R$ " + selectedVehicle.getDailyRate().multiply(BigDecimal.valueOf(rentalDays))
                : "Não definido";

        int maxLineLength = 47; // Ajuste conforme necessário

//        String topLine = "╔" + "═".repeat(maxLineLength) + "╗";
        String emptyLine = "║" + " ".repeat(maxLineLength) + "║";
        String bottomLine = "╚" + "═".repeat(maxLineLength) + "╝";

//        System.out.println(topLine);
        System.out.println(emptyLine);
        System.out.printf("║   %-43s ║%n", agencyPrompt + agencyValue);
        System.out.printf("║   %-43s ║%n", vehiclePrompt + vehicleValue);
        System.out.printf("║   %-43s ║%n", customerPrompt + customerValue);
        System.out.printf("║   %-43s ║%n", startDatePrompt + startDateValue);
        System.out.printf("║   %-43s ║%n", estimatedEndDatePrompt + estimatedEndDateValue);
        System.out.printf("║   %-43s ║%n", rentalCostPrompt + rentalCostValue);

        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void confirmRegistration() {
        String input = Input.getAsString(scanner, "Confirma o cadastro? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de cadastro

            CreateRentalDTO createRentalDTO = new CreateRentalDTO(
                    selectedCustomer, selectedVehicle, selectedAgency, startDate, estimatedEndDate
            );

            rentalService.createRental(createRentalDTO);

            System.out.println("Locação realizada com sucesso!");
        } else {
            System.out.println("Cancelada.");
        }
        scanner.nextLine();
        flowController.goBack();
    }

    private boolean processInputCommands(String input) {
        if (input.equalsIgnoreCase("v")) {
            if (currentField > 0) {
                currentField--;
            }
            return true;
        } else if (input.equalsIgnoreCase("c")) {
            cancelRegistration();
            return true;
        }
        return false;
    }

    private void cancelRegistration() {
        flowController.goBack();
    }

    private void handleFieldSelectionError() {
        System.out.println("1 - Voltar para o campo anterior");
        System.out.println("2 - Tentar novamente");
        System.out.println("3 - Cancelar");

        Result<Integer> option = Input.getAsInt(scanner, "Escolha uma opção: ", false);
        switch (option.getValue()) {
            case 1 -> {
                if (currentField > 0) {
                    currentField--;
                }
            }
            case 2 -> {
                if (currentField == 0) isAgencySelectionListCalled = false;
                if (currentField == 1) isVehicleSelectionListCalled = false;
                if (currentField == 2) isCustomerSelectionListCalled = false;
            }
            case 3 -> cancelRegistration();
        }
    }

}
