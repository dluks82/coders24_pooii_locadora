package ui.screens.customer;

import exceptions.DataInputInterruptedException;
import service.customer.CustomerService;
import ui.utils.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class CustomerMenuScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 65;
    private final Scanner scanner;

    private final CustomerService customerService;

    private String errorMessage = "";

    public CustomerMenuScreen(FlowController flowController,
                              Scanner scanner, CustomerService customerService) {
        super(flowController);
        this.scanner = scanner;
        this.customerService = customerService;
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
            case 1 -> navigateTo(new CustomerCreateScreen(flowController, scanner, customerService));

            case 2 -> navigateTo(new CustomerListScreen(flowController, scanner, customerService, false));

            case 3 -> navigateTo(new CustomerUpdateScreen(flowController, scanner, customerService));

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
        Header.show("Menu Clientes", null);

        String emptyLine = "║    " + " ".repeat(MAX_LINE_LENGTH) + "    ║";
        String bottomLine = "╚════" + "═".repeat(MAX_LINE_LENGTH) + "════╝";

//        System.out.println(topLine);
        System.out.println(emptyLine);
        System.out.printf("║    %-65s    ║%n", "[ 1 ] - Adicionar");
        System.out.printf("║    %-65s    ║%n", "[ 2 ] - Listar");
        System.out.printf("║    %-65s    ║%n", "[ 3 ] - Editar");
        System.out.printf("║    %-65s    ║%n", "[ 0 ] - Voltar");
        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void navigateTo(Screen screen) {
        flowController.goTo(screen);
    }

}
