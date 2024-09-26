package ui.screens;

import exceptions.DataInputInterruptedException;
import repository.agency.AgencyRepository;
import repository.agency.InMemoryAgencyRepositoryImpl;
import repository.customer.CustomerRepository;
import repository.customer.InMemoryCustomerRepositoryImpl;
import repository.rental.InMemoryRentalRepository;
import repository.rental.RentalRepository;
import repository.vehicle.InMemoryVehicleRepository;
import repository.vehicle.VehicleRepository;
import service.agency.AgencyService;
import service.agency.AgencyServiceImpl;
import service.customer.CustomerService;
import service.customer.CustomerServiceImpl;
import service.rental.RentalService;
import service.rental.RentalServiceImpl;
import service.vehicle.VehicleService;
import service.vehicle.VehicleServiceImpl;
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
        AgencyRepository agencyRepository = InMemoryAgencyRepositoryImpl.getInstance();
        return new AgencyServiceImpl(agencyRepository);
    }

    private VehicleService createVehicleService() {
        VehicleRepository vehicleRepository = new InMemoryVehicleRepository();
        return new VehicleServiceImpl(vehicleRepository);
    }

    private CustomerService createCustomerService() {
        CustomerRepository customerRepository = new InMemoryCustomerRepositoryImpl();
        return new CustomerServiceImpl(customerRepository);
    }

    private RentalService createRentalService() {
        RentalRepository rentalRepository = new InMemoryRentalRepository();
        return new RentalServiceImpl(rentalRepository);
    }

    @Override
    public void show() {
        Result<Integer> option;

        do {
            ScreenUtils.clearScreen();
            displayMenuOptions();
            displayPendingMessages();

            try {
                option = Input.getAsInt(scanner, "Escolha uma opção: ", false);
            } catch (DataInputInterruptedException e) {
                errorMessage = e.getMessage();
                continue;
            }

            if (option.isFailure()) {
                errorMessage = option.getErrorMessage();
                continue;
            }
            handleMenuOption(option.getValue());

            if (option.getValue() == 0)
                break;

        } while (true);
    }

    private void displayMenuOptions() {
        ScreenUtils.showHeader("Menu Principal");

        int maxLineLength = 47; // Ajuste conforme necessário

//        String topLine = "╔" + "═".repeat(maxLineLength) + "╗";
        String emptyLine = "║" + " ".repeat(maxLineLength) + "║";
        String bottomLine = "╚" + "═".repeat(maxLineLength) + "╝";

//        System.out.println(topLine);
        System.out.println(emptyLine);
        System.out.printf("║   %-43s ║%n", "[ 1 ] - Agencias");
        System.out.printf("║   %-43s ║%n", "[ 2 ] - Veículos");
        System.out.printf("║   %-43s ║%n", "[ 3 ] - Clientes");
        System.out.printf("║   %-43s ║%n", "[ 4 ] - Locações");
        System.out.printf("║   %-43s ║%n", "[ 0 ] - Sair");
        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void displayPendingMessages() {
        if (!errorMessage.isEmpty()) {
            Output.error(errorMessage);
            errorMessage = "";
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
