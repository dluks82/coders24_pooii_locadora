package ui.screens.customer;

import model.customer.Customer;
import service.customer.CustomerService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CustomerListScreen extends Screen {
    private final Scanner scanner;
    private final CustomerService customerService;
    private final boolean isModal;

    private Customer selectedCustomer;

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

            Output.info("'F' para filtrar, 'L' para limpar filtro.");
            Output.info("'A' para avançar página, 'V' para voltar página");
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
        int totalPages = (int) Math.ceil((double) customers.size() / PAGE_SIZE);
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, customers.size());

        if (isModal) {
            ScreenUtils.showHeader("Selecione um Cliente");
        } else {
            ScreenUtils.showHeader("Lista de Clientes");
        }

        System.out.printf("%-5s %-30s %-30s %-20s%n", "Nº", "Nome", "Documento", "Telefone");
        System.out.println("--------------------------------------------------------------------------");

        for (int i = start; i < end; i++) {
            Customer customer = customers.get(i);
            System.out.printf("%-5d %-30s %-30s %-20s%n", (i + 1), customer.getName(), customer.getDocumentId(), customer.getPhoneNumber());
        }

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\nPágina " + (currentPage + 1) + " de " + totalPages);
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
                searchCustomers(customers);
                break;

            case "l":
                filteredCustomers = customers;
                currentPage = 0;
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
        System.out.println("Digite o nome do cliente que deseja buscar: ");
        String searchQuery = scanner.nextLine().trim().toLowerCase();

        filteredCustomers = customers.stream()
                .filter(agency -> agency.getName().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        currentPage = 0;

        if (filteredCustomers.isEmpty()) {
            System.out.println("Nenhum cliente encontrado com o nome: " + searchQuery);
            filteredCustomers = customers;
        } else {
            System.out.println(filteredCustomers.size() + " cliente(s) encontrado(s).");
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
