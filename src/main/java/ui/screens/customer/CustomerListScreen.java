package ui.screens.customer;

import model.customer.Customer;
import service.customer.CustomerService;
import ui.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CustomerListScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 65;
    private final Scanner scanner;
    private final CustomerService customerService;
    private final boolean isModal;

    private Customer selectedCustomer;

    private String searchQuery = "";

    private int currentPage = 0;
    private static final int PAGE_SIZE = 2;
    private List<Customer> filteredCustomers;

    public CustomerListScreen(
            FlowController flowController,
            Scanner scanner,
            CustomerService customerService,
            boolean isModal) {
        super(flowController);
        this.scanner = scanner;
        this.customerService = customerService;
        this.isModal = isModal;
    }

    private boolean customerSelected = false;

    @Override
    public void show() {
        List<model.customer.Customer> customers = customerService.findAllCustomers();
        this.filteredCustomers = customers;

        do {
            ScreenUtils.clearScreen();

            listPaginatedCustomers(filteredCustomers, currentPage);

            Output.info("'X' para voltar");

            String promptMessage = isModal ? "Selecione um cliente pelo número ou utilize os comandos acima: "
                    : "Utilize os comandos de navegação: ";

            String input = Input.getAsString(scanner, promptMessage, true, false);

            if (processInputCommands(input, customers)) {
                break;
            }

        } while (!customerSelected);
    }

    private void listPaginatedCustomers(List<Customer> customers, int page) {
        if (isModal) {
            Header.show("Selecione um cliente para continuar...", null);
        } else {
            Header.show("Lista de Clientes", null);
        }

        if (!searchQuery.isEmpty()) {
            System.out.println("║  Filtro: " + searchQuery);
        }

        if (customers.isEmpty()) {
            Output.info("Nenhum cliente encontrado.\n");
            return;
        }

        int totalPages = (int) Math.ceil((double) customers.size() / PAGE_SIZE);
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, customers.size());

        String emptyLine = "║    " + " ".repeat(MAX_LINE_LENGTH) + "    ║";
        String bottomLine = "╚════" + "═".repeat(MAX_LINE_LENGTH) + "════╝";

        System.out.println(emptyLine);
        System.out.printf("║ %-3s │ %-23s │ %-23s │ %-13s ║%n", "Nº", "Nome", "Documento", "Telefone");
        System.out.println("╟─────┼─────────────────────────┼─────────────────────────┼───────────────╢");

        for (int i = start; i < end; i++) {
            Customer customer = customers.get(i);
            System.out.printf("║ %-3d │ %-23s │ %-23s │ %-13s ║%n",
                    (i + 1),
                    limitString(customer.getName(), 23),
                    limitString(customer.getDocumentId(), 23),
                    limitString(customer.getPhoneNumber(), 13));
        }

        System.out.println(emptyLine);
        System.out.println(bottomLine);

        System.out.println("\nPágina " + (page + 1) + " de " + totalPages + "\n");

        Output.info("'F' para filtrar, 'L' para limpar filtro.");
        Output.info("'A' para avançar página, 'V' para voltar página");
    }

    private String limitString(String str, int maxLength) {
        if (str.length() > maxLength) {
            return str.substring(0, maxLength - 3) + "...";
        }
        return str;
    }

    private boolean processInputCommands(String input, List<Customer> customers) {
        switch (input.toLowerCase()) {
            case "v":
                if (currentPage > 0) {
                    currentPage--;
                }
                break;
            case "a":
                if (currentPage < (int) Math.ceil((double) filteredCustomers.size() / PAGE_SIZE) - 1) {
                    currentPage++;
                }
                break;
            case "f":
                if (filteredCustomers.isEmpty())
                    break;

                searchCustomers(customers);
                break;
            case "l":
                if (!searchQuery.isEmpty()) {
                    searchQuery = "";
                    filteredCustomers = customers;
                    currentPage = 0;
                }
                break;
            case "x":
                back();
                return true;
            default:
                if (isModal) {
                    try {
                        int customerIndex = Integer.parseInt(input) - 1;
                        if (customerIndex >= 0 && customerIndex < filteredCustomers.size()) {
                            selectCustomer(filteredCustomers.get(customerIndex));
                            customerSelected = true;
                            return true;
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Tente novamente.");
                    }
                }
                return false;
        }
        return false;
    }

    private void searchCustomers(List<Customer> customers) {
        searchQuery = Input.getAsString(scanner, "Filtrar por nome de cliente: ", true, false);

        filteredCustomers = customers.stream()
                .filter(agency -> agency.getName().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        currentPage = 0;

        if (filteredCustomers.isEmpty()) {
            System.out.println("Nenhum cliente encontrado com o nome: " + searchQuery);
            searchQuery = "";
            filteredCustomers = customers;
        }

        System.out.println("Pressione Enter para continuar.");
        scanner.nextLine();
    }

    private void selectCustomer(Customer customer) {
        this.selectedCustomer = customer;
        this.customerSelected = true;
    }

    private void back() {
        flowController.goBack();
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }
}
