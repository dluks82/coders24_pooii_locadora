package ui.screens.vehicle;

import exceptions.DataInputInterruptedException;
import service.agency.AgencyService;
import service.vehicle.VehicleService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.screens.rental.RentalCreateScreen;
import ui.screens.rental.RentalListScreen;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class VehicleMenuScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 47;
    private final Scanner scanner;

    private final AgencyService agencyService;
    private final VehicleService vehicleService;

    private String errorMessage = "";

    public VehicleMenuScreen(FlowController flowController,
                             Scanner scanner, AgencyService agencyService, VehicleService vehicleService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
        this.vehicleService = vehicleService;
    }

    @Override
    public void show() {
        Result<Integer> option;
        do {
            ScreenUtils.clearScreen();
            displayMenuOptions();
            displayPendingMessages();

            option = getUserOption();

            if (option.isFailure()) {
                errorMessage = option.getErrorMessage();
                continue;
            }

            handleMenuOption(option.getValue());

            if (option.getValue() == 0) break;
        } while (true);
    }

    private void handleMenuOption(int option) {
        switch (option) {
            case 1 -> navigateTo(new VehicleCreateScreen(flowController, scanner, agencyService, vehicleService));
            case 2 -> navigateTo(new VehicleListScreen(flowController, scanner, vehicleService, false));
            case 3 -> navigateTo(new VehicleUpdateScreen(flowController, scanner, vehicleService));
            case 0 -> flowController.goBack();
            default -> errorMessage = "Opção inválida! Por favor, informe uma opção do menu...";
        }
    }

    private void displayPendingMessages() {
        if (!errorMessage.isEmpty()) {
            Output.error(errorMessage);
            errorMessage = "";
        }
    }

    private Result<Integer> getUserOption() {
        try {
            return Input.getAsInt(scanner, "Escolha uma opção: ", false);
        } catch (DataInputInterruptedException e) {
            return Result.fail(e.getMessage());
        }
    }

    private void displayMenuOptions() {
        ScreenUtils.showHeader("Veículos");

        String emptyLine = "║" + " ".repeat(MAX_LINE_LENGTH) + "║";
        String bottomLine = "╚" + "═".repeat(MAX_LINE_LENGTH) + "╝";

        System.out.println(emptyLine);
        System.out.printf("║   %-43s ║%n", "[ 1 ] - Adicionar");
        System.out.printf("║   %-43s ║%n", "[ 2 ] - Listar");
        System.out.printf("║   %-43s ║%n", "[ 3 ] - Editar");
        System.out.printf("║   %-43s ║%n", "[ 0 ] - Voltar");
        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void navigateTo(Screen screen) {
        flowController.goTo(screen);
    }
}
