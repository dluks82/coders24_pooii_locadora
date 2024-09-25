package ui.screens.agency;

import model.agency.Agency;
import service.agency.AgencyService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class AgencyUpdateScreen extends Screen {
    private final Scanner scanner;
    private final AgencyService agencyService;
    private Agency agencyToUpdate;

    private int currentField = 0;

    private boolean isSelectionListCalled = false;

    public AgencyUpdateScreen(FlowController flowController, Scanner scanner, AgencyService agencyService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();

            System.out.println("=== Editar Agência ===");

            if (agencyToUpdate == null && !isSelectionListCalled) {
                isSelectionListCalled = true;
                currentField = 0;

                AgencyListScreen agencyListScreen = new AgencyListScreen(flowController, scanner, agencyService);
                flowController.goTo(agencyListScreen);

                agencyToUpdate = agencyListScreen.getSelectedAgency();

                flowController.goBack();
                return;
            }

            if (agencyToUpdate != null) {
                System.out.println("Nome: " + agencyToUpdate.getName());
                System.out.println("Endereço: " + agencyToUpdate.getAddress());
                System.out.println("Telefone: " + agencyToUpdate.getPhone());

                switch (currentField) {
                    case 0 -> {
                        String inputName = Input.getAsString(scanner, "Nome: ", true, false);
                        if (processInputCommands(inputName)) break;
                        if (!inputName.isEmpty())
                            agencyToUpdate.setName(inputName);
                        currentField = 1;
                    }
                    case 1 -> {
                        String addressInput = Input.getAsString(scanner, "Endereço: ", true, false);
                        if (processInputCommands(addressInput)) break;
                        if (!addressInput.isEmpty())
                            agencyToUpdate.setAddress(addressInput);
                        currentField = 2;
                    }
                    case 2 -> {
                        String phoneInput = Input.getAsString(scanner, "Telefone: ", true, false);
                        if (processInputCommands(phoneInput)) break;
                        if (!phoneInput.isEmpty())
                            agencyToUpdate.setPhone(phoneInput);
                        currentField = 3;
                    }
                    case 3 -> confirmUpdate();
                }
            } else {
                System.out.println("Nenhuma agência selecionada para edição.");
                scanner.nextLine();
                flowController.goBack();
                return;
            }

        } while (true);
    }

    private void confirmUpdate() {
        String input = Input.getAsString(scanner, "Confirmar alterações? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de atualização
            Agency updatedAgency = new Agency(
                    agencyToUpdate.getId(),
                    agencyToUpdate.getName(),
                    agencyToUpdate.getAddress(),
                    agencyToUpdate.getPhone()
            );
            agencyService.updateAgency(updatedAgency);

            Output.info("Edição realizada com sucesso!");
        } else {
            Output.info("Edição cancelada.");
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
            cancelUpdate();
            return true;
        }
        return false;
    }

    private void cancelUpdate() {
        flowController.goBack();
    }

}
