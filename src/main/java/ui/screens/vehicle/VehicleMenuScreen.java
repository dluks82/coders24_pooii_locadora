package ui.screens.vehicle;

import service.agency.AgencyService;
import service.vehicle.VehicleService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class VehicleMenuScreen extends Screen {
    private final Scanner scanner;

    private final AgencyService agencyService;
    private final VehicleService vehicleService;

    public VehicleMenuScreen(FlowController flowController,
                             Scanner scanner, AgencyService agencyService, VehicleService vehicleService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
        this.vehicleService = vehicleService;
    }

    @Override
    public void show() {
        int option;

        do {
            ScreenUtils.clearScreen();

            ScreenUtils.showHeader("Veículos");

            System.out.println("1 - Adicionar Veículo");
            System.out.println("2 - Listar Veículos");
            System.out.println("3 - Atualizar Veículo");
            System.out.println("0 - Voltar");

            option = Input.getAsInt(scanner, "Escolha uma opção: ", false);

            switch (option) {
                case 1:
                    flowController.goTo(
                            new VehicleCreateScreen(flowController, scanner, agencyService, vehicleService));
                    break;
                case 2:
                    flowController.goTo(
                            new VehicleListScreen(flowController, scanner, vehicleService, false));
                    break;
                case 3:
                    System.out.println("Opção 3");
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
