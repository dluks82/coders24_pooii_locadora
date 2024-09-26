package ui.screens.agency;

import dto.CreateAgencyDTO;
import service.agency.AgencyService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class AgencyCreateScreen extends Screen {
    private final Scanner scanner;

    private String errorMessage = "";

    private final AgencyService agencyService;

    private String name = "";
    private String address = "";
    private String phone = "";

    private int currentField = 0;

    public AgencyCreateScreen(FlowController flowController,
                              Scanner scanner, AgencyService agencyService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();
            ScreenUtils.showHeader("Cadastro de Agência");

            displayAgencyRegistration();

            Output.info("'V' para voltar campo, 'C' para cancelar o cadastro.");

            displayPendingMessages();

            handleCurrentField();


        } while (true);
    }

    private void displayAgencyRegistration() {
        String namePrompt = "Nome:";
        String addressPrompt = "Endereço:";
        String phonePrompt = "Telefone:";

        int maxLineLength = 47; // Ajuste conforme necessário

//        String topLine = "╔" + "═".repeat(maxLineLength) + "╗";
        String emptyLine = "║" + " ".repeat(maxLineLength) + "║";
        String bottomLine = "╚" + "═".repeat(maxLineLength) + "╝";

//        System.out.println(topLine);
        System.out.println(emptyLine);
        System.out.printf("║   %-43s ║%n", namePrompt + (name.isEmpty() ? "" : " " + name));
        System.out.printf("║   %-43s ║%n", addressPrompt + (address.isEmpty() ? "" : " " + address));
        System.out.printf("║   %-43s ║%n", phonePrompt + (phone.isEmpty() ? "" : " " + phone));
        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void displayPendingMessages() {
        if (!errorMessage.isEmpty()) {
            Output.error(errorMessage);
            errorMessage = "";
        }
    }

    private void handleCurrentField() {
        switch (currentField) {
            case 0 -> {
                String inputName =
                        Input.getAsString(scanner, "Nome: ", false, false);
                if (processInputCommands(inputName)) {
                    break;
                }
                name = inputName;
                currentField = 1;
            }
            case 1 -> {
                String addressInput =
                        Input.getAsString(scanner, "Endereço: ", false, false);
                if (processInputCommands(addressInput)) {
                    break;
                }
                address = addressInput;
                currentField = 2;
            }
            case 2 -> {
                String phoneInput =
                        Input.getAsString(scanner, "Telefone: ", false, false);
                if (processInputCommands(phoneInput)) {
                    break;
                }
                phone = phoneInput;
                currentField = 3;
            }
            case 3 -> confirmRegistration();
        }
    }

    private void confirmRegistration() {
        String input =
                Input.getAsString(scanner, "Confirma o cadastro? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de cadastro
            try {
                CreateAgencyDTO createAgencyDTO = new CreateAgencyDTO(name, address, phone);
                agencyService.createAgency(createAgencyDTO);
            } catch (IllegalArgumentException e) {
                errorMessage = e.getMessage();
                currentField = 0;
                return;
            } catch (Exception e) {
                errorMessage = "Erro desconhecido ao cadastrar a agência. Tente novamente!";
                currentField = 0;
                return;
            }
        }
        flowController.goBack();
    }

    private boolean processInputCommands(String input) {
        if (input.equalsIgnoreCase("v")) {
            if (currentField > 0) {
                currentField--;
            }
            return true;
        } else if (input.equalsIgnoreCase("c")) {
            cancelRegistration();
            return true;
        }
        return false;
    }

    private void cancelRegistration() {
        flowController.goBack();
    }

}
