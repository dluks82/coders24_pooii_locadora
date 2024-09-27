package ui.screens.vehicle;

import dto.CreateVehicleDTO;
import enums.VehicleType;
import model.agency.Agency;
import service.agency.AgencyService;
import service.vehicle.VehicleService;
import ui.utils.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.screens.agency.AgencyListScreen;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class VehicleCreateScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 65;
    private final Scanner scanner;

    private final AgencyService agencyService;
    private final VehicleService vehicleService;

    private Agency selectedAgency;

    private String plate = "";
    private String model = "";
    private String brand = "";
    private VehicleType type;

    private int currentField = 0;

    private boolean isSelectionListCalled = false;

    public VehicleCreateScreen(FlowController flowController,
                               Scanner scanner, AgencyService agencyService, VehicleService vehicleService) {
        super(flowController);
        this.scanner = scanner;
        this.agencyService = agencyService;
        this.vehicleService = vehicleService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();

            Header.show("Preencha os campos abaixo para cadastrar um veículo.", null);

            if (type != null) {
                displayVehicleRegistration();
                Output.info("'V' para voltar campo, 'C' para cancelar o cadastro.");
            }

            switch (currentField) {
                case 0 -> {
                    String emptyLine = "║  " + " ".repeat(25) + "  ║";
                    String bottomLine = "╚══" + "═".repeat(25) + "══╝";

                    System.out.println(emptyLine);
                    for (VehicleType type : VehicleType.values()) {
                        System.out.printf("║  [ %d ] - %-15s    ║%n", type.ordinal(), type.getDescription());
                    }
                    System.out.println(emptyLine);
                    System.out.println(bottomLine);

                    Result<Integer> inputType = Input.getAsInt(scanner, "Tipo: ", false);

                    if (inputType.isFailure()) {
                        Output.error(inputType.getErrorMessage());
                        scanner.nextLine();
                        break;
                    }

                    if (inputType.getValue() < 0 || inputType.getValue() >= VehicleType.values().length) {
                        Output.error("Tipo de veículo inválido!");
                        scanner.nextLine();
                        break;
                    }
                    type = VehicleType.values()[inputType.getValue()];

                    currentField = 1;
                }
                case 1 -> {
                    String plateInput = Input.getAsString(scanner, "Placa: ", false, false);
                    if (processInputCommands(plateInput)) {
                        break;
                    }
                    plate = plateInput;
                    currentField = 2;
                }
                case 2 -> {
                    String modelInput = Input.getAsString(scanner, "Modelo: ", false, false);
                    if (processInputCommands(modelInput)) {
                        break;
                    }
                    model = modelInput;
                    currentField = 3;
                }
                case 3 -> {
                    String brandInput = Input.getAsString(scanner, "Marca: ", false, false);
                    if (processInputCommands(brandInput)) {
                        break;
                    }
                    brand = brandInput;
                    currentField = 4;
                }
                case 4 -> {

                    if (!isSelectionListCalled) {
                        isSelectionListCalled = true;

                        AgencyListScreen agencyListScreen = new AgencyListScreen(flowController, scanner, agencyService, true);
                        flowController.goTo(agencyListScreen);

                        selectedAgency = agencyListScreen.getSelectedAgency();

                        flowController.goBack();
                    }

                    if (selectedAgency == null) {
                        Output.error("Você precisa selecionar uma agência válida!");

                        System.out.println("1 - Voltar para o campo anterior");
                        System.out.println("2 - Tentar novamente");
                        System.out.println("3 - Cancelar o cadastro");

                        Result<Integer> option = Input.getAsInt(scanner, "Escolha uma opção: ", false);
                        switch (option.getValue()) {
                            case 1:
                                currentField = 3;
                                isSelectionListCalled = false;
                                break;
                            case 2:
                                isSelectionListCalled = false;
                                break;
                            case 3:
                                cancelRegistration();
                                break;
                        }
                        break;
                    }
                    currentField = 5;
                }
                case 5 -> confirmRegistration();
            }
        } while (true);
    }

    private void displayVehicleRegistration() {

        String typeName = type != null ? type.name().isEmpty() ? "" : type.getDescription() : "";
        String agencyName = selectedAgency != null ? selectedAgency.getName().isEmpty() ? "" : selectedAgency.getName() : "";

        String[] fields = {
                "Tipo: " + typeName,
                "Placa: " + (plate.isEmpty() ? "" : plate),
                "Modelo: " + (model.isEmpty() ? "" : model),
                "Marca: " + (brand.isEmpty() ? "" : brand),
                "Agência: " + agencyName
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

    private void confirmRegistration() {
        String input = Input.getAsString(scanner, "Confirma o cadastro? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de cadastro
            CreateVehicleDTO createVehicleDTO = new CreateVehicleDTO(
                    type, plate, model, brand, selectedAgency
            );

            try {
                vehicleService.createVehicle(createVehicleDTO);
            } catch (Exception e) {
                Output.error(e.getMessage());
                System.out.println("... cancelado.");
                scanner.nextLine();
                return;
            }

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
