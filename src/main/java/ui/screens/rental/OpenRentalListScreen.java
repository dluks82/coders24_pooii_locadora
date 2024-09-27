package ui.screens.rental;

import model.rental.Rental;
import service.rental.RentalService;
import ui.utils.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class OpenRentalListScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 65;
    private final Scanner scanner;
    private final RentalService rentalService;
    private final boolean isModal;

    private Rental selectedRental;

    private int currentPage = 0;
    private static final int PAGE_SIZE = 2;
    private List<Rental> filteredRentals;

    public OpenRentalListScreen(FlowController flowController, Scanner scanner, RentalService rentalService, boolean isModal) {
        super(flowController);
        this.scanner = scanner;
        this.rentalService = rentalService;
        this.isModal = isModal;
    }

    private boolean rentalSelected = false;

    @Override
    public void show() {
        List<Rental> rentals = rentalService.findOpenRentals();
        this.filteredRentals = rentals;

        do {
            ScreenUtils.clearScreen();

            listPaginatedRentals(filteredRentals, currentPage);

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int totalPages = (int) Math.ceil((double) rentals.size() / PAGE_SIZE);
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, rentals.size());

        if (isModal) {
            Header.show("Selecione uma locação para continuar...", null);
        } else {
            Header.show("Lista De Locações Abertas", null);
        }

        if (rentals.isEmpty()) {
            Output.info("Nenhuma locação aberta encontrada.\n");
            return;
        }

        String emptyLine = "║    " + " ".repeat(MAX_LINE_LENGTH) + "    ║";
        String bottomLine = "╚════" + "═".repeat(MAX_LINE_LENGTH) + "════╝";

        System.out.println(emptyLine);
        System.out.printf("║ %-3s │ %-33s │ %-13s │ %-13s ║%n", "Nº", "Cliente", "Data Início", "Data Estimada");
        System.out.println("╟─────┼───────────────────────────────────┼───────────────┼───────────────╢");

        for (int i = start; i < end; i++) {
            Rental rental = rentals.get(i);
            System.out.printf("║ %-3d │ %-33s │ %-13s │ %-13s ║%n",
                    (i + 1),
                    limitString(rental.getCustomer().getName(), 23),
                    limitString(rental.getPickUpDate().format(formatter), 23),
                    limitString(rental.getEstimatedReturnDate().format(formatter), 13));
        }

        System.out.println(emptyLine);
        System.out.println(bottomLine);

        System.out.println("\nPágina " + (page + 1) + " de " + totalPages + "\n");

        Output.info("'A' para avançar página, 'V' para voltar página");

    }

    private String limitString(String str, int maxLength) {
        if (str.length() > maxLength) {
            return str.substring(0, maxLength - 3) + "...";
        }
        return str;
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
