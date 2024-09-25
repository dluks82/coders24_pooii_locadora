package ui.screens.customer;

import model.customer.Customer;
import service.customer.CustomerService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class CustomerUpdateScreen extends Screen {
    private final Scanner scanner;
    private final CustomerService customerService;
    private Customer customerToUpdate;

    private int currentField = 0;

    private boolean isSelectionListCalled = false;

    public CustomerUpdateScreen(FlowController flowController, Scanner scanner, CustomerService customerService) {
        super(flowController);
        this.scanner = scanner;
        this.customerService = customerService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();

            System.out.println("=== Editar Agência ===");

            if (customerToUpdate != null) {
                System.out.println("Nome: " + customerToUpdate.getName());
                System.out.println("Telefone: " + customerToUpdate.getNumberPhone());
                System.out.println("Documento: " + customerToUpdate.getDocumentId());
            }
            switch (currentField) {
                case 0 -> {
                    if (!isSelectionListCalled) {
                        isSelectionListCalled = true;

                        CustomerListScreen customerListScreen = new CustomerListScreen(flowController, scanner, customerService, true);
                        flowController.goTo(customerListScreen);

                        customerToUpdate = customerListScreen.getSelectedCustomer();

                        flowController.goBack();
                    }

                    if (customerToUpdate == null) {
                        Output.error("Você precisa selecionar um válido!");

                        System.out.println("1 - Tentar novamente");
                        System.out.println("2 - Cancelar o cadastro");

                        int option = Input.getAsInt(scanner, "Escolha uma opção: ", false);
                        switch (option) {
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
                    if (!inputName.isEmpty())
                        customerToUpdate.setName(inputName);
                    currentField = 2;
                }
                case 2 -> {
                    String phoneInput = Input.getAsString(scanner, "Telefone: ", true, false);
                    if (processInputCommands(phoneInput)) break;
                    if (!phoneInput.isEmpty())
                        customerToUpdate.setNumberPhone(phoneInput);
                    currentField = 3;
                }
                case 3 -> {
                    String DocumentInput = Input.getAsString(scanner, "Documento: ", true, false);
                    if (processInputCommands(DocumentInput)) break;
                    if (!DocumentInput.isEmpty())
                        customerToUpdate.setDocumentId(DocumentInput);
                    currentField = 4;
                }
                case 4 -> confirmUpdate();
            }
        } while (true);
    }

    private void confirmUpdate() {
        String input = Input.getAsString(scanner, "Confirmar alterações? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de atualização
            customerService.updateCustomer(customerToUpdate);

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
