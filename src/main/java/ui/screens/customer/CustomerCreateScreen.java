package ui.screens.customer;

import dto.CreateCustomerDTO;
import enums.CustomerType;
import service.customer.CustomerService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class CustomerCreateScreen extends Screen {
    private final Scanner scanner;

    private final CustomerService customerService;

    protected String name = "";
    protected String phoneNumber = "";
    protected String documentId = "";
    protected CustomerType type;

    private int currentField = 0;

    public CustomerCreateScreen(FlowController flowController,
                                Scanner scanner, CustomerService customerService) {
        super(flowController);
        this.scanner = scanner;
        this.customerService = customerService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();

            System.out.println("=== Cadastro de Cliente ===");

            String typeName = type != null ? type.name().isEmpty() ? "" : type.name() : "";
            String document = type == CustomerType.INDIVIDUAL ? "CPF: " : type == CustomerType.LEGALENTITY ? "CNPJ: " : "Documento: ";

            System.out.println("Tipo: " + typeName);
            System.out.println(document + (documentId.isEmpty() ? "" : documentId));
            System.out.println("Nome: " + (name.isEmpty() ? "" : name));
            System.out.println("Telefone: " + (phoneNumber.isEmpty() ? "" : phoneNumber));

            Output.info("'V' para voltar campo, 'C' para cancelar o cadastro.");

            switch (currentField) {
                case 0 -> {
                    System.out.println("Selecione o tipo de cliente:");
                    for (CustomerType type : CustomerType.values()) {
                        System.out.println(type.ordinal() + " - " + type.name());
                    }
                    int inputType = Input.getAsInt(scanner, "Tipo: ", false);
                    if (inputType < 0 || inputType >= CustomerType.values().length) {
                        Output.error("Tipo de cliente inválido!");
                        scanner.nextLine();
                        break;
                    }
                    type = CustomerType.values()[inputType];

                    currentField = 1;
                }
                case 1 -> {
                    String documentInput = Input.getAsString(scanner, document, false, false);
                    if (processInputCommands(documentInput)) {
                        break;
                    }
                    documentId = documentInput;
                    currentField = 2;
                }
                case 2 -> {
                    String inputName = Input.getAsString(scanner, "Nome: ", false, false);
                    if (processInputCommands(inputName)) {
                        break;
                    }
                    name = inputName;
                    currentField = 3;
                }
                case 3 -> {
                    String phoneInput = Input.getAsString(scanner, "Telefone: ", false, false);
                    if (processInputCommands(phoneInput)) {
                        break;
                    }
                    phoneNumber = phoneInput;
                    currentField = 4;
                }

                case 4 -> confirmRegistration();
            }
        } while (true);
    }

    private void confirmRegistration() {
        String input = Input.getAsString(scanner, "Confirma o cadastro? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de cadastro
            CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO(
                    type, name, phoneNumber, documentId
            );

            customerService.createCustomer(createCustomerDTO);

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