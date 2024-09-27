package ui.screens.agency;

import exceptions.DataInputInterruptedException;
import model.agency.Agency;
import service.agency.AgencyService;
import ui.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class AgencyUpdateScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 47;
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
            Header.show("Editar Agência", null);

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
                        System.out.println("2 - Cancelar...");

                        Result<Integer> option = getUserOption();

                        if (option.isFailure()) {
                            errorMessage = option.getErrorMessage();
                            continue;
                        }

                        switch (option.getValue()) {
                            case 1 -> isSelectionListCalled = false;
                            case 2 -> cancelUpdate();
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

    private Result<Integer> getUserOption() {
        try {
            return Input.getAsInt(scanner, "Escolha uma opção: ", false);
        } catch (DataInputInterruptedException e) {
            return Result.fail(e.getMessage());
        }
    }

    private void displayPendingMessages() {
        if (!errorMessage.isEmpty()) {
            Output.error(errorMessage);
            // Esperar o usuário pressionar Enter para continuar
            Input.getAsString(scanner, "Pressione Enter para continuar...", true, false);
            errorMessage = ""; // Limpa o erro após o usuário confirmar
        }
    }

    private void displayAgencyUpdateForm() {
        String[] fields = {
                String.format("Nome: %s", name.isEmpty() ? "" : name),
                String.format("Endereço: %s", address.isEmpty() ? "" : address),
                String.format("Telefone: %s", phone.isEmpty() ? "" : phone)
        };

        String emptyLine = "║" + " ".repeat(MAX_LINE_LENGTH) + "║";
        String bottomLine = "╚" + "═".repeat(MAX_LINE_LENGTH) + "╝";

        System.out.println(emptyLine);
        for (String field : fields) {
            System.out.printf("║   %-43s ║%n", field);
        }
        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void confirmUpdate() {
        String input = Input.getAsString(scanner, "Confirmar alterações? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de atualização
            try {
                updateAgency();
            } catch (IllegalArgumentException e) {
                handleError(e.getMessage());
                return;
            } catch (Exception e) {
                handleError("Erro desconhecido ao atualizar a agência. Tente novamente!");
                return;
            }
            Output.info("Edição realizada com sucesso!");
            scanner.nextLine();
        } else {
            Output.info("Edição cancelada.");
            scanner.nextLine();
        }
        scanner.nextLine();
        flowController.goBack();
    }

    private void updateAgency() {
        Agency updatedAgency = new Agency(
                agencyToUpdate.getId(),
                name.isEmpty() ? agencyToUpdate.getName() : name,
                address.isEmpty() ? agencyToUpdate.getAddress() : address,
                phone.isEmpty() ? agencyToUpdate.getPhone() : phone
        );
        agencyService.updateAgency(updatedAgency);
    }

    private void handleError(String message) {
        errorMessage = message;
        currentField = 0;
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
