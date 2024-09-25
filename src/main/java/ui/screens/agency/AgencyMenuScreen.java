package ui.screens.agency;

import service.agency.AgencyService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class AgencyMenuScreen extends Screen {
    private final Scanner scanner;
    private final AgencyService agencyService;

    public AgencyMenuScreen(FlowController flowController,
                            Scanner scanner, AgencyService agencyService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
    }

    @Override
    public void show() {
        int option;

        do {
            ScreenUtils.clearScreen();

            System.out.println("Menu de Agências");
            System.out.println("1 - Adicionar Agência");
            System.out.println("2 - Listar Agências");
            System.out.println("3 - Atualizar Agência");
            System.out.println("0 - Voltar");

            option = Input.getAsInt(scanner, "Escolha uma opção: ", false);

            switch (option) {
                case 1:
                    flowController.goTo(
                            new AgencyCreateScreen(flowController, scanner, agencyService));
                    break;
                case 2:
                    flowController.goTo(
                            new AgencyListScreen(flowController, scanner, agencyService, false));
                    break;
                case 3:
                    flowController.goTo(
                            new AgencyUpdateScreen(flowController, scanner, agencyService));
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
