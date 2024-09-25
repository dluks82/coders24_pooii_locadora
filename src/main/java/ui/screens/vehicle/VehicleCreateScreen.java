package ui.screens.vehicle;

import dto.CreateVehicleDTO;
import enums.VehicleType;
import model.agency.Agency;
import service.agency.AgencyService;
import service.vehicle.VehicleService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.screens.agency.AgencyListScreen;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class VehicleCreateScreen extends Screen {
    private final Scanner scanner;

    private final AgencyService agencyService;
    private final VehicleService vehicleService;

    private Agency selectedAgency;

    private String id = "";
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

            System.out.println("=== Cadastro de Veículo ===");

            String typeName = type != null ? type.name().isEmpty() ? "" : type.name() : "";
            String agencyName = selectedAgency != null ? selectedAgency.getName().isEmpty() ? "" : selectedAgency.getName() : "";

            System.out.println("Tipo: " + typeName);
            System.out.println("Placa: " + (plate.isEmpty() ? "" : plate));
            System.out.println("Modelo: " + (model.isEmpty() ? "" : model));
            System.out.println("Marca: " + (brand.isEmpty() ? "" : brand));
            System.out.println("Agencia: " + agencyName);

            Output.info("'V' para voltar campo, 'C' para cancelar o cadastro.");

            switch (currentField) {
                case 0 -> {
                    System.out.println("Selecione o tipo de veículo:");
                    for (VehicleType type : VehicleType.values()) {
                        System.out.println(type.ordinal() + " - " + type.name());
                    }
                    int inputType = Input.getAsInt(scanner, "Tipo: ", false);
                    if (inputType < 0 || inputType >= VehicleType.values().length) {
                        Output.error("Tipo de veículo inválido!");
                        scanner.nextLine();
                        break;
                    }
                    type = VehicleType.values()[inputType];

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
                        System.out.println("Selecione a agência:");
                        scanner.nextLine();

                        AgencyListScreen agencyListScreen = new AgencyListScreen(flowController, scanner, agencyService, true);
                        flowController.goTo(agencyListScreen);

                        selectedAgency = agencyListScreen.getSelectedAgency();

                        // TODO: pensar no shared data com o flowController
//                        selectedAgency = flowController.getSharedData("selectedAgency");

                        flowController.goBack();
                    }

                    if (selectedAgency == null) {
                        Output.error("Você precisa selecionar uma agência válida!");

                        System.out.println("1 - Voltar para o campo anterior");
                        System.out.println("2 - Tentar novamente");
                        System.out.println("3 - Cancelar o cadastro");

                        int option = Input.getAsInt(scanner, "Escolha uma opção: ", false);
                        switch (option) {
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

    private void confirmRegistration() {
        String input = Input.getAsString(scanner, "Confirma o cadastro? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de cadastro
            CreateVehicleDTO createVehicleDTO = new CreateVehicleDTO(
                    type, id, plate, model, brand, selectedAgency.getId()
            );
            vehicleService.createVehicle(createVehicleDTO);

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
