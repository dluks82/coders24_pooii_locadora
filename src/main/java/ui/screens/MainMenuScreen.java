package ui.screens;

import exceptions.DataInputInterruptedException;
import repository.agency.InFileAgencyRepositoryImpl;
import repository.customer.InFileCustomerRepositoryImpl;
import repository.rental.InFileRentalRepository;
import repository.vehicle.InFileVehicleRepository;
import service.agency.AgencyService;
import service.agency.AgencyServiceImpl;
import service.customer.CustomerService;
import service.customer.CustomerServiceImpl;
import service.rental.RentalService;
import service.rental.RentalServiceImpl;
import service.vehicle.VehicleService;
import service.vehicle.VehicleServiceImpl;
import ui.utils.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.screens.agency.AgencyMenuScreen;
import ui.screens.customer.CustomerMenuScreen;
import ui.screens.rental.RentalMenuScreen;
import ui.screens.vehicle.VehicleMenuScreen;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class MainMenuScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 65;
    private final Scanner scanner;

    private String errorMessage = "";

    private final AgencyService agencyService;
    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final RentalService rentalService;

    public MainMenuScreen(FlowController flowController, Scanner scanner) {
        super(flowController);
        this.scanner = scanner;

        agencyService = createAgencyService();
        vehicleService = createVehicleService();
        customerService = createCustomerService();
        rentalService = createRentalService();
    }

    private AgencyService createAgencyService() {
        return new AgencyServiceImpl(InFileAgencyRepositoryImpl.getInstance());
    }

    private VehicleService createVehicleService() {
        return new VehicleServiceImpl(InFileVehicleRepository.getInstance());
    }

    private CustomerService createCustomerService() {
        return new CustomerServiceImpl(InFileCustomerRepositoryImpl.getInstance());
    }

    private RentalService createRentalService() {
        return new RentalServiceImpl(InFileRentalRepository.getInstance(), InFileVehicleRepository.getInstance());
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

    private void displayMenuOptions() {
        Header.show("Menu Principal", null);
        String[] menuOptions = {
                "[ 1 ] - Agências",
                "[ 2 ] - Veículos",
                "[ 3 ] - Clientes",
                "[ 4 ] - Locações",
                "[ 0 ] - Sair"
        };

        String emptyLine = "║    " + " ".repeat(MAX_LINE_LENGTH) + "    ║";
        String bottomLine = "╚════" + "═".repeat(MAX_LINE_LENGTH) + "════╝";

        System.out.println(emptyLine);
        for (String option : menuOptions) {
            System.out.printf("║    %-65s    ║%n", option);
        }
        System.out.println(emptyLine);
        System.out.println(bottomLine);
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

    private void handleMenuOption(int option) {
        switch (option) {
            case 1 -> navigateTo(new AgencyMenuScreen(flowController, scanner, agencyService));
            case 2 -> navigateTo(new VehicleMenuScreen(flowController, scanner, agencyService, vehicleService));
            case 3 -> navigateTo(new CustomerMenuScreen(flowController, scanner, customerService));
            case 4 -> navigateTo(new RentalMenuScreen(
                    flowController, scanner, agencyService, customerService, vehicleService, rentalService));
            case 0 -> exitApp();
            default -> errorMessage = "Opção inválida! Por favor, informe uma opção do menu...";
        }
    }

    private void navigateTo(Screen screen) {
        flowController.goTo(screen);
    }

    private void exitApp() {
        ScreenUtils.clearScreen();
        Output.info("Obrigado por utilizar o sistema de locação de veículos!");
        scanner.close();
        System.exit(0);
    }
}
