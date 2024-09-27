package ui.screens.rental;

import model.agency.Agency;
import model.rental.Rental;
import service.agency.AgencyService;
import service.rental.RentalService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.screens.agency.AgencyListScreen;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RentalCloseScreen extends Screen {
    private final Scanner scanner;

    private final AgencyService agencyService;
    private final RentalService rentalService;

    private Rental rentalToClose;
    private Agency returnAgency;

    private LocalDate returnDate;

    private int currentField = 0;

    private boolean isRentalSelectionListCalled = false;
    private boolean isAgencySelectionListCalled = false;

    public RentalCloseScreen(FlowController flowController,
                             Scanner scanner, AgencyService agencyService,
                             RentalService rentalService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
        this.rentalService = rentalService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();

            ScreenUtils.showHeader("Encerrar Locação");

            if (rentalToClose != null) {

                displayRentalClose();

                Output.info("'V' para voltar campo, 'C' para cancelar o cadastro.");
            }

            switch (currentField) {
                case 0 -> {
                    if (!isRentalSelectionListCalled) {
                        isRentalSelectionListCalled = true;

                        RentalListScreen rentalListScreen = new RentalListScreen(flowController, scanner, rentalService, true);
                        flowController.goTo(rentalListScreen);

                        rentalToClose = rentalListScreen.getSelectedRental();

                        flowController.goBack();
                    }

                    if (rentalToClose == null) {
                        Output.error("Você precisa selecionar uma locação válida!");

                        handleFieldSelectionError();
                        break;
                    }
                    currentField = 1;
                }
                case 1 -> {
                    if (!isAgencySelectionListCalled) {
                        isAgencySelectionListCalled = true;

                        AgencyListScreen agencyListScreen = new AgencyListScreen(flowController, scanner, agencyService, true);
                        flowController.goTo(agencyListScreen);

                        returnAgency = agencyListScreen.getSelectedAgency();

                        flowController.goBack();
                    }

                    if (returnAgency == null) {
                        Output.error("Você precisa selecionar uma agência válida!");

                        handleFieldSelectionError();
                        break;
                    }
                    currentField = 2;
                }
                case 2 -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    returnDate = null;

                    while (returnDate == null) {
                        String returnDateInput = Input.getAsString(scanner, "Data de retorno (dd/MM/yyyy): ", false, false);
                        try {
                            returnDate = LocalDate.parse(returnDateInput, formatter);
                        } catch (Exception e) {
                            Output.error("Data inválida! Tente novamente.");
                        }
                    }

                    currentField = 3;
                }

                case 3 -> confirmClose();
            }
        } while (true);
    }

    private void displayRentalClose() {

        String agencyPrompt = "Agência de Retirada: ";
        String agencyValue = rentalToClose.getPickUpAgency().getName();
        String vehiclePrompt = "Veículo: ";
        String vehicleValue = rentalToClose.getVehicle().getModel();
        String customerPrompt = "Cliente: ";
        String customerValue = rentalToClose.getCustomer().getName();
        String startDatePrompt = "Data de início: ";
        String startDateValue = rentalToClose.getPickUpDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String estimatedEndDatePrompt = "Data estimada de término: ";
        String estimatedEndDateValue = rentalToClose.getEstimatedReturnDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String rentalCostPrompt = "Valor da locação: ";
        String rentalCostValue = rentalToClose.getVehicle().calculateRentalPrice(5).toString();

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

    private void confirmClose() {
        String input = Input.getAsString(scanner, "Confirma o enceramento? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de encerramento

            try {
                rentalService.closeRental(rentalToClose, returnAgency, returnDate);
            } catch (Exception e) {
                Output.error(e.getMessage());
                System.out.println("... cancelado.");
                scanner.nextLine();
                return;
            }

            System.out.println("Locação encerrada com sucesso!");

            // GERAR o RECIBO
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
                if (currentField == 1) isAgencySelectionListCalled = false;
            }
            case 3 -> cancelRegistration();
        }
    }

}
