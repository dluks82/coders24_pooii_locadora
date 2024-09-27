package ui.screens.agency;

import dto.CreateAgencyDTO;
import exceptions.DataInputInterruptedException;
import service.agency.AgencyService;
import ui.utils.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;
import java.util.function.Consumer;

public class AgencyCreateScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 65;
    private final Scanner scanner;
    private final AgencyService agencyService;

    private String errorMessage = "";

    private String name = "";
    private String address = "";
    private String phone = "";

    private int currentField = 0;

    public AgencyCreateScreen(FlowController flowController, Scanner scanner, AgencyService agencyService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();
            Header.show("Cadastro de Agência", null);

            displayAgencyRegistration();
            Output.info("'V' para voltar campo, 'C' para cancelar o cadastro.");
            displayPendingMessages();
            handleCurrentField();
        } while (true);
    }

    private void displayAgencyRegistration() {
        String[] fields = {
                String.format("Nome: %s", name.isEmpty() ? "" : name),
                String.format("Endereço: %s", address.isEmpty() ? "" : address),
                String.format("Telefone: %s", phone.isEmpty() ? "" : phone)
        };

        String emptyLine = "║    " + " ".repeat(MAX_LINE_LENGTH) + "    ║";
        String bottomLine = "╚════" + "═".repeat(MAX_LINE_LENGTH) + "════╝";

        System.out.println(emptyLine);
        for (String field : fields) {
            System.out.printf("║    %-65s    ║%n", field);
        }
        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void displayPendingMessages() {
        if (!errorMessage.isEmpty()) {
            Output.error(errorMessage);
            // Esperar o usuário pressionar Enter para continuar
            Input.getAsString(scanner, "Pressione Enter para continuar...", true, false);
            errorMessage = ""; // Limpa o erro após o usuário confirmar
        }
    }

    private void handleCurrentField() {
        switch (currentField) {
            case 0 -> handleStringField("Nome: ", value -> name = value, 1);
            case 1 -> handleStringField("Endereço: ", value -> address = value, 2);
            case 2 -> handleStringField("Telefone: ", value -> phone = value, 3);
            case 3 -> confirmRegistration();
        }
    }

    private void handleStringField(String prompt, Consumer<String> setter, int nextField) {
        try {
            Result<String> input = Input.getAsString2(scanner, prompt, false, false);
            if (input.isFailure()) {
                handleError(input.getErrorMessage());
                return;
            }
            if (processInputCommands(input.getValue())) return;
            setter.accept(input.getValue());
            currentField = nextField;
        } catch (DataInputInterruptedException e) {
            cancelRegistration();
            return;
        }
    }

    private void confirmRegistration() {
        String input =
                Input.getAsString(scanner, "Confirma o cadastro? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de cadastro
            try {
                registerAgency();
            } catch (IllegalArgumentException e) {
                handleError(e.getMessage());
                return;
            } catch (Exception e) {
                handleError("Erro desconhecido ao cadastrar a agência. Tente novamente!");
                return;
            }
        }
        flowController.goBack();
    }

    private void registerAgency() {
        CreateAgencyDTO createAgencyDTO = new CreateAgencyDTO(name, address, phone);
        agencyService.createAgency(createAgencyDTO);
    }

    private void handleError(String message) {
        errorMessage = message;
        currentField = 0;
    }

    private boolean processInputCommands(String input) {
        if (input.equalsIgnoreCase("v")) {
            if (currentField > 0) currentField--;
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
