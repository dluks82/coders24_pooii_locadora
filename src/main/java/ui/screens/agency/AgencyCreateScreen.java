package ui.screens.agency;

import dto.CreateAgencyDTO;
import service.AgencyService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class AgencyCreateScreen extends Screen {
    private final Scanner scanner;

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

            System.out.println("=== Cadastro de Agência ===");
            System.out.println("Nome: " + (name.isEmpty() ? "" : name));
            System.out.println("Endereço: " + (address.isEmpty() ? "" : address));
            System.out.println("Telefone: " + (phone.isEmpty() ? "" : phone));

            Output.info("'V' para voltar campo, 'C' para cancelar o cadastro.");

            switch (currentField) {
                case 0 -> {
                    String inputName = Input.getAsString(scanner, "Nome: ", false, false);
                    if (processInputCommands(inputName)) {
                        break;
                    }
                    name = inputName;
                    currentField = 1;
                }
                case 1 -> {
                    String addressInput = Input.getAsString(scanner, "Endereço: ", false, false);
                    if (processInputCommands(addressInput)) {
                        break;
                    }
                    address = addressInput;
                    currentField = 2;
                }
                case 2 -> {
                    String phoneInput = Input.getAsString(scanner, "Telefone: ", false, false);
                    if (processInputCommands(phoneInput)) {
                        break;
                    }
                    phone = phoneInput;
                    currentField = 3;
                }
                case 3 -> confirmRegistration();
            }
        } while (true);
    }

    private void confirmRegistration() {
        String input = Input.getAsString(scanner, "Confirma o cadastro? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de cadastro
            CreateAgencyDTO createAgencyDTO = new CreateAgencyDTO(name, address, phone);
            agencyService.createAgency(createAgencyDTO);

            System.out.println("Cadastro realizado com sucesso!");
        } else {
            System.out.println("Cadastro cancelado.");
        }
        scanner.nextLine();
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
