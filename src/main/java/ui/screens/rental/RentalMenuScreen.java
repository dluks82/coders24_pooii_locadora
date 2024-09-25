package ui.screens.rental;

import service.agency.AgencyService;
import service.customer.CustomerService;
import service.rental.RentalService;
import service.vehicle.VehicleService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class RentalMenuScreen extends Screen {
    private final Scanner scanner;

    private final AgencyService agencyService;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final RentalService rentalService;


    public RentalMenuScreen(FlowController flowController,
                            Scanner scanner, AgencyService agencyService, CustomerService customerService, VehicleService vehicleService, RentalService rentalService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.rentalService = rentalService;
    }

    @Override
    public void show() {
        int option;

        do {
            ScreenUtils.clearScreen();

            System.out.println("Menu de Locações");
            System.out.println("1 - Iniciar Locação");
            System.out.println("2 - Finalizar Locação");
            System.out.println("3 - Visualizer Locações Ativas");
            System.out.println("0 - Voltar");

            option = Input.getAsInt(scanner, "Escolha uma opção: ", false);

            switch (option) {
                case 1:
                    flowController.goTo(
                            new RentalCreateScreen(flowController, scanner, agencyService, vehicleService, customerService, rentalService));
                    break;
                case 2:
                    System.out.println("Opção 2");
                    break;
                case 3:
                    flowController.goTo(
                            new RentalListScreen(flowController, scanner, rentalService, false));
                    break;
                case 0:
                    flowController.goBack();
                    break;
                default:
                    Output.error("Opção inválida!");
                    scanner.nextLine();
                    break;
            }
        } while (option != 0);

    }

}
