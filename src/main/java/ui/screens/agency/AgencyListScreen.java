package ui.screens.agency;

import model.agency.Agency;
import service.agency.AgencyService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AgencyListScreen extends Screen {
    private final Scanner scanner;
    private final AgencyService agencyService;

    private Agency selectedAgency;

    private int currentPage = 0;
    private static final int PAGE_SIZE = 2;
    private List<Agency> filteredAgencies;

    public AgencyListScreen(FlowController flowController, Scanner scanner, AgencyService agencyService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
    }

    private boolean agencySelected = false;

    @Override
    public void show() {
        List<Agency> agencies = agencyService.findAllAgencies();
        this.filteredAgencies = agencies;

        do {
            ScreenUtils.clearScreen();
            System.out.println("=== Lista de Agências ===");

            listPaginatedAgencies(filteredAgencies, currentPage);

            Output.info("'B' para buscar, 'A' para avançar página, 'V' para voltar página, 'C' para cancelar.");
            String input = Input.getAsString(scanner, "Selecione uma agência pelo número ou utilize os comandos acima: ", false, false);

            if (processInputCommands(input, agencies)) {
                break;
            }

        } while (!agencySelected);
    }

    private void listPaginatedAgencies(List<Agency> agencies, int page) {
        int totalPages = (int) Math.ceil((double) agencies.size() / PAGE_SIZE);
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, agencies.size());

        System.out.printf("%-5s %-30s %-30s %-20s%n", "Nº", "Nome", "Endereço", "Telefone");
        System.out.println("--------------------------------------------------------------------------");

        for (int i = start; i < end; i++) {
            Agency agency = agencies.get(i);
            System.out.printf("%-5d %-30s %-30s %-20s%n", (i + 1), agency.getName(), agency.getAddress(), agency.getPhone());
        }

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("\nPágina " + (currentPage + 1) + " de " + totalPages);
    }

    private boolean processInputCommands(String input, List<Agency> agencies) {
        switch (input.toLowerCase()) {
            case "v":
                if (currentPage > 0) {
                    currentPage--;
                }
                break;

            case "a":
                if (currentPage < (int) Math.ceil((double) filteredAgencies.size() / PAGE_SIZE) - 1) {
                    currentPage++;
                }
                break;

            case "b":
                searchAgencies(agencies);
                break;

            case "c":
                back();
                return true;

            default:
                try {
                    int agencyIndex = Integer.parseInt(input) - 1;
                    if (agencyIndex >= 0 && agencyIndex < filteredAgencies.size()) {
                        selectAgency(filteredAgencies.get(agencyIndex));
                        agencySelected = true;
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

    private void searchAgencies(List<Agency> agencies) {
        System.out.println("Digite o nome da agência que deseja buscar: ");
        String searchQuery = scanner.nextLine().trim().toLowerCase();

        filteredAgencies = agencies.stream()
                .filter(agency -> agency.getName().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());

        currentPage = 0;

        if (filteredAgencies.isEmpty()) {
            System.out.println("Nenhuma agência encontrada com o nome: " + searchQuery);
            filteredAgencies = agencies;
        } else {
            System.out.println(filteredAgencies.size() + " agência(s) encontrada(s).");
        }

        System.out.println("Pressione Enter para continuar.");
        scanner.nextLine();
    }

    private void selectAgency(Agency agency) {
        System.out.println("\nAgência selecionada: " + agency);
        this.selectedAgency = agency;
        this.agencySelected = true;
    }

    private void back() {
        flowController.goBack();
    }

    public Agency getSelectedAgency() {
        return selectedAgency;
    }
}
