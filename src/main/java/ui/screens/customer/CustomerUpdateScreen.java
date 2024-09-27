package ui.screens.customer;

import enums.CustomerType;
import model.customer.Customer;
import service.customer.CustomerService;
import ui.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class CustomerUpdateScreen extends Screen {
    private final Scanner scanner;
    private final CustomerService customerService;
    private Customer customerToUpdate;

    private int currentField = 0;

    private boolean isSelectionListCalled = false;

    private String name = "";
    private String phone = "";
    private String document = "";
    private CustomerType type;

    public CustomerUpdateScreen(FlowController flowController, Scanner scanner, CustomerService customerService) {
        super(flowController);
        this.scanner = scanner;
        this.customerService = customerService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();

            Header.show("Preencha os campos abaixo para editar o cliente.", null);

            if (customerToUpdate != null) {
                displayCustomerUpdateForm();
                Output.info("'V' para voltar campo, 'C' para cancelar a edição.");
            }
            switch (currentField) {
                case 0 -> {
                    if (!isSelectionListCalled) {
                        isSelectionListCalled = true;

                        CustomerListScreen customerListScreen = new CustomerListScreen(flowController, scanner, customerService, true);
                        flowController.goTo(customerListScreen);

                        customerToUpdate = customerListScreen.getSelectedCustomer();
                        if (customerToUpdate != null) {
                            name = customerToUpdate.getName();
                            phone = customerToUpdate.getPhoneNumber();
                            document = customerToUpdate.getDocumentId();
                        }

                        flowController.goBack();
                    }

                    if (customerToUpdate == null) {
                        Output.error("Você precisa selecionar um válido!");

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
                    String phoneInput = Input.getAsString(scanner, "Telefone: ", true, false);
                    if (processInputCommands(phoneInput)) break;
                    phone = phoneInput.trim().isEmpty() ? phone : phoneInput;
                    currentField = 3;
                }
                case 3 -> {
                    String DocumentInput = Input.getAsString(scanner, "Documento: ", true, false);
                    if (processInputCommands(DocumentInput)) break;
                    document = DocumentInput.trim().isEmpty() ? document : DocumentInput;
                    currentField = 4;
                }
                case 4 -> confirmUpdate();
            }
        } while (true);
    }

    private void displayCustomerUpdateForm() {
        String typeName = type != null ? type.name().isEmpty() ? "" : type.getDescription() : "";

        String documentPrompt = type == CustomerType.INDIVIDUAL ? "CPF: " : type == CustomerType.LEGALENTITY ? "CNPJ: " : "Documento: ";

        String namePrompt = "Nome: ";
        String phonePrompt = "Telefone: ";

        int maxLineLength = 47; // Ajuste conforme necessário

//        String topLine = "╔" + "═".repeat(maxLineLength) + "╗";
        String emptyLine = "║" + " ".repeat(maxLineLength) + "║";
        String bottomLine = "╚" + "═".repeat(maxLineLength) + "╝";

//        System.out.println(topLine);
        System.out.println(emptyLine);
        System.out.printf("║   %-43s ║%n", typeName);
        System.out.printf("║   %-43s ║%n", namePrompt + name);
        System.out.printf("║   %-43s ║%n", phonePrompt + phone);
        System.out.printf("║   %-43s ║%n", documentPrompt + document);
        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void confirmUpdate() {
        String input = Input.getAsString(scanner, "Confirmar alterações? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de atualização

            try {
                customerService.updateCustomer(customerToUpdate);
            } catch (Exception e) {
                Output.error(e.getMessage());
                System.out.println("... cancelado.");
                scanner.nextLine();
                return;
            }


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
