package ui.screens.agency;

import model.agency.Agency;
import service.agency.AgencyService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class AgencyUpdateScreen extends Screen {
    private final Scanner scanner;
    private final AgencyService agencyService;
    private Agency agencyToUpdate;

    private String name = "";
    private String address = "";
    private String phone = "";

    private int currentField = 0;

    private String errorMessage = "";

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
            ScreenUtils.showHeader("Editar Agência");

            if (agencyToUpdate != null) {
                displayAgencyUpdateForm();
                // Enviar vazio mantem o valor atual
                Output.info("'V' para voltar campo, 'C' para cancelar a edição.");
            }

            displayPendingMessages();

            switch (currentField) {
                case 0 -> {
                    if (!isSelectionListCalled) {
                        isSelectionListCalled = true;

                        AgencyListScreen agencyListScreen =
                                new AgencyListScreen(flowController, scanner, agencyService, true);
                        flowController.goTo(agencyListScreen);

                        agencyToUpdate = agencyListScreen.getSelectedAgency();
                        if (agencyToUpdate != null) {
                            name = agencyToUpdate.getName();
                            address = agencyToUpdate.getAddress();
                            phone = agencyToUpdate.getPhone();
                        }

                        flowController.goBack();
                    }

                    if (agencyToUpdate == null) {
                        Output.error("Você precisa selecionar uma agência válida!\n\n");

                        System.out.println("1 - Tentar novamente");
                        System.out.println("2 - Cancelar o cadastro");

                        Result<Integer> option = Input.getAsInt(scanner, "Escolha uma opção: ", false);
                        switch (option.getValue()) {
                            case 1:
                                isSelectionListCalled = false;
                                break;
                            case 2:
                                cancelUpdate();
                                break;
                        }
                        break;
                    }
                    currentField = 1;
                }

                case 1 -> {
                    String inputName = Input.getAsString(scanner, "Nome: ", true, false);
                    if (processInputCommands(inputName)) break;

                    name = inputName.trim().isEmpty() ? name : inputName;
                    currentField = 2;
                }
                case 2 -> {
                    String addressInput = Input.getAsString(scanner, "Endereço: ", true, false);
                    if (processInputCommands(addressInput)) break;

                    address = addressInput.trim().isEmpty() ? address : addressInput;
                    currentField = 3;
                }
                case 3 -> {
                    String phoneInput = Input.getAsString(scanner, "Telefone: ", true, false);
                    if (processInputCommands(phoneInput)) break;

                    phone = phoneInput.trim().isEmpty() ? phone : phoneInput;
                    currentField = 4;
                }
                case 4 -> confirmUpdate();
            }
        } while (true);
    }

    private void displayPendingMessages() {
        if (!errorMessage.isEmpty()) {
            Output.error(errorMessage);
            errorMessage = "";
        }
    }

    private void displayAgencyUpdateForm() {
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

    private void confirmUpdate() {
        String input = Input.getAsString(scanner, "Confirmar alterações? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de atualização
            Agency updatedAgency = new Agency(
                    agencyToUpdate.getId(),
                    name.isEmpty() ? agencyToUpdate.getName() : name,
                    address.isEmpty() ? agencyToUpdate.getAddress() : address,
                    phone.isEmpty() ? agencyToUpdate.getPhone() : phone
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
