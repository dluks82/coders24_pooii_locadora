package ui.screens.agency;

import model.agency.Agency;
import service.agency.AgencyService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.ScreenUtils;

import java.util.List;
import java.util.Scanner;

public class AgencySelectionScreen extends Screen {

    private final Scanner scanner;
    private final AgencyService agencyService;

    public AgencySelectionScreen(FlowController flowController, Scanner scanner, AgencyService agencyService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
    }

    @Override
    public void show() {
        ScreenUtils.clearScreen();
        System.out.println("=== Seleção de Agência para Edição ===");

        // Obter lista de agências do serviço
        List<Agency> agencies = agencyService.findAllAgencies();

        if (agencies.isEmpty()) {
            System.out.println("Nenhuma agência disponível para editar.");
            scanner.nextLine();
            flowController.goBack();
            return;
        }

        // Exibir a lista de agências
        for (int i = 0; i < agencies.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, agencies.get(i).getName());
        }

        System.out.println("\nEscolha uma agência pelo número ou 'c' para cancelar:");

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("c")) {
            cancelarSelecao();
            return;
        }

        try {
            int selectedIndex = Integer.parseInt(input) - 1;
            if (selectedIndex >= 0 && selectedIndex < agencies.size()) {
                // Agência selecionada
                Agency selectedAgency = agencies.get(selectedIndex);
                editarAgencia(selectedAgency);
            } else {
                System.out.println("Opção inválida.");
                scanner.nextLine();
                show();
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            scanner.nextLine();
            show();
        }
    }

    // Navegar para a tela de edição da agência selecionada
    private void editarAgencia(Agency agency) {
        AgencyUpdateScreen updateScreen = new AgencyUpdateScreen(flowController, scanner, agencyService);
        flowController.goTo(updateScreen);
    }

    // Cancelar a seleção e voltar à tela anterior
    private void cancelarSelecao() {
        System.out.println("Seleção cancelada.");
        flowController.goBack();
    }

}
