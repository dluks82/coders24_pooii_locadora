package ui.screens.customer;

import service.customer.CustomerService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class CustomerMenuScreen extends Screen {
    private final Scanner scanner;

    private final CustomerService customerService;

    public CustomerMenuScreen(FlowController flowController,
                              Scanner scanner, CustomerService customerService) {
        super(flowController);
        this.scanner = scanner;
        this.customerService = customerService;
    }

    @Override
    public void show() {
        int option;

        do {
            ScreenUtils.clearScreen();

            ScreenUtils.showHeader("Clientes");

            System.out.println("1 - Registrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("3 - Atualizar Cliente");
            System.out.println("0 - Voltar");

            option = Input.getAsInt(scanner, "Escolha uma opção: ", false);

            switch (option) {
                case 1:
                    flowController.goTo(
                            new CustomerCreateScreen(flowController, scanner, customerService));
                    break;
                case 2:
                    flowController.goTo(
                            new CustomerListScreen(flowController, scanner, customerService, false));
                    break;
                case 3:
                    flowController.goTo(
                            new CustomerUpdateScreen(flowController, scanner, customerService));
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
