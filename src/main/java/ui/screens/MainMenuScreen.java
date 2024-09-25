package ui.screens;

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
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class MainMenuScreen extends Screen {
    private final Scanner scanner;

    private final AgencyService agencyService;
    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final RentalService rentalService;

    public MainMenuScreen(FlowController flowController,
                          Scanner scanner) {
        super(flowController);
        this.scanner = scanner;

        AgencyRepository agencyRepository = new InMemoryAgencyRepositoryImpl();
        VehicleRepository vehicleRepository = new InMemoryVehicleRepository();
        CustomerRepository customerRepository = new InMemoryCustomerRepositoryImpl();
        RentalRepository rentalRepository = new InMemoryRentalRepository();

        agencyService = new AgencyServiceImpl(agencyRepository);
        vehicleService = new VehicleServiceImpl(vehicleRepository);
        customerService = new CustomerServiceImpl(customerRepository);
        rentalService = new RentalServiceImpl(rentalRepository);

    }

    @Override
    public void show() {
        int option;

        do {
            ScreenUtils.clearScreen();

            System.out.println("Coders Rental App");
            System.out.println("1 - Agencias");
            System.out.println("2 - Veículos");
            System.out.println("3 - Clientes");
            System.out.println("4 - Locações");
            System.out.println("0 - Exit");

            option = Input.getAsInt(scanner, "Escolha uma opção: ", false);

            switch (option) {
                case 1:
                    flowController.goTo(
                            new AgencyMenuScreen(flowController, scanner, agencyService));
                    break;
                case 2:
                    flowController.goTo(
                            new VehicleMenuScreen(flowController, scanner, agencyService, vehicleService));
                    break;
                case 3:
                    flowController.goTo(
                            new CustomerMenuScreen(flowController, scanner, customerService));
                    break;
                case 4:
                    flowController.goTo(
                            new RentalMenuScreen(flowController, scanner, agencyService, customerService, vehicleService, rentalService));
                    break;
                case 0:
                    Output.info("Encerrando App...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    Output.error("Opção inválida!");
                    scanner.nextLine();
                    break;
            }
        } while (true);
    }
}
