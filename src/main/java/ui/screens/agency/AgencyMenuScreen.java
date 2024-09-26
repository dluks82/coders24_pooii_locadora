package ui.screens.agency;

import exceptions.DataInputInterruptedException;
import service.agency.AgencyService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class AgencyMenuScreen extends Screen {
    private final Scanner scanner;
    private final AgencyService agencyService;

    private String errorMessage = "";

    public AgencyMenuScreen(FlowController flowController,
                            Scanner scanner, AgencyService agencyService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
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
        ScreenUtils.showHeader("Menu Agências");

        int maxLineLength = 47; // Ajuste conforme necessário

//        String topLine = "╔" + "═".repeat(maxLineLength) + "╗";
        String emptyLine = "║" + " ".repeat(maxLineLength) + "║";
        String bottomLine = "╚" + "═".repeat(maxLineLength) + "╝";

//        System.out.println(topLine);
        System.out.println(emptyLine);
        System.out.printf("║   %-43s ║%n", "[ 1 ] - Adicionar");
        System.out.printf("║   %-43s ║%n", "[ 2 ] - Listar");
        System.out.printf("║   %-43s ║%n", "[ 3 ] - Editar");
        System.out.printf("║   %-43s ║%n", "[ 0 ] - Voltar");
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
            case 1 -> navigateTo(new AgencyCreateScreen(flowController, scanner, agencyService));
            case 2 -> navigateTo(new AgencyListScreen(flowController, scanner, agencyService, false));
            case 3 -> navigateTo(new AgencyUpdateScreen(flowController, scanner, agencyService));
            case 0 -> flowController.goBack();
            default -> errorMessage = "Opção inválida! Por favor, informe uma opção do menu...";
        }
    }

    private void navigateTo(Screen screen) {
        flowController.goTo(screen);
    }

}
