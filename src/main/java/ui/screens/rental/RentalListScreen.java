package ui.screens.rental;

import model.rental.Rental;
import service.rental.RentalService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RentalListScreen extends Screen {
    private final Scanner scanner;
    private final RentalService rentalService;
    private final boolean isModal;

    private Rental selectedRental;

    private int currentPage = 0;
    private static final int PAGE_SIZE = 2;
    private List<Rental> filteredRentals;

    public RentalListScreen(FlowController flowController, Scanner scanner, RentalService rentalService, boolean isModal) {
        super(flowController);
        this.scanner = scanner;
        this.rentalService = rentalService;
        this.isModal = isModal;
    }

    private boolean rentalSelected = false;

    @Override
    public void show() {
        List<Rental> rentals = rentalService.findAllRentals();
        this.filteredRentals = rentals;

        do {
            ScreenUtils.clearScreen();
            System.out.println("=== Lista de Veículos ===");

            listPaginatedRentals(filteredRentals, currentPage);

            Output.info("'F' para filtrar, 'L' para limpar filtro.");
            Output.info("'A' para avançar página, 'V' para voltar página");
            Output.info("'X' para voltar");

            String promptMessage = isModal ? "Selecione um veículo pelo número ou utilize os comandos acima: "
                    : "Utilize os comandos de navegação: ";

            String input = Input.getAsString(scanner, promptMessage, true, false);

            if (processInputCommands(input, rentals)) {
                break;
            }

        } while (!rentalSelected);
    }

    private void listPaginatedRentals(List<Rental> rentals, int page) {
        int totalPages = (int) Math.ceil((double) rentals.size() / PAGE_SIZE);
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, rentals.size());

        System.out.printf("%-5s %-30s %-30s %-20s%n", "Nº", "Nome", "Endereço", "Telefone");
        System.out.println("--------------------------------------------------------------------------");

        for (int i = start; i < end; i++) {
            Rental rental = rentals.get(i);
            System.out.printf("%-5d %-30s %-30s %-20s%n", (i + 1), rental.getCustomer().getName(), rental.getCustomer().getName(), rental.getCustomer().getNumberPhone());
        }

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\nPágina " + (currentPage + 1) + " de " + totalPages);
    }

    private boolean processInputCommands(String input, List<Rental> rentals) {
        switch (input.toLowerCase()) {
            case "v":
                if (currentPage > 0) {
                    currentPage--;
                }
                break;

            case "a":
                if (currentPage < (int) Math.ceil((double) filteredRentals.size() / PAGE_SIZE) - 1) {
                    currentPage++;
                }
                break;

            case "f":
                searchRentals(rentals);
                break;

            case "l":
                filteredRentals = rentals;
                currentPage = 0;
                break;

            case "x":
                back();
                return true;

            default:
                try {
                    int vehicleIndex = Integer.parseInt(input) - 1;
                    if (vehicleIndex >= 0 && vehicleIndex < filteredRentals.size()) {
                        selectRental(filteredRentals.get(vehicleIndex));
                        rentalSelected = true;
                        return true;
                    } else {
                        System.out.println("Opção inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Tente novamente.");
                }
                break;
        }
        return false;
    }

    private void searchRentals(List<Rental> rentals) {
        System.out.println("Digite o modelo que deseja buscar: ");
        String searchQuery = scanner.nextLine().trim().toLowerCase();

        filteredRentals = rentals; // TODO: BUSCAR ABERTOS

//        filteredRentals = rentals.stream()
//                .filter(vehicle -> vehicle.getModel().toLowerCase().contains(searchQuery))
//                .collect(Collectors.toList());

        currentPage = 0;

        if (filteredRentals.isEmpty()) {
            System.out.println("Nenhum veículo encontrado com o modelo: " + searchQuery);
            filteredRentals = rentals;
        } else {
            System.out.println(filteredRentals.size() + " veículo(s) encontrado(s).");
        }

        System.out.println("Pressione Enter para continuar.");
        scanner.nextLine();
    }

    private void selectRental(Rental rental) {
        this.selectedRental = rental;
        this.rentalSelected = true;
    }

    private void back() {
        flowController.goBack();
    }

    public Rental getSelectedRental() {
        return selectedRental;
    }
}
