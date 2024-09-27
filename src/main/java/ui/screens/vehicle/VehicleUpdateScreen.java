package ui.screens.vehicle;

import model.vehicle.Car;
import model.vehicle.Motorcycle;
import model.vehicle.Truck;
import model.vehicle.Vehicle;
import service.vehicle.VehicleService;
import ui.utils.Header;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.Result;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class VehicleUpdateScreen extends Screen {
    private static final int MAX_LINE_LENGTH = 65;
    private final Scanner scanner;
    private final VehicleService vehicleService;
    private Vehicle vehicleToUpdate;

    private String model = "";
    private String brand = "";
    private String plate = "";

    private String errorMessage = "";

    private int currentField = 0;

    private boolean isSelectionListCalled = false;

    public VehicleUpdateScreen(FlowController flowController, Scanner scanner, VehicleService vehicleService) {
        super(flowController);
        this.scanner = scanner;
        this.vehicleService = vehicleService;
    }

    @Override
    public void show() {
        do {
            ScreenUtils.clearScreen();
            Header.show("Preencha os campos abaixo para editar o veículo.", null);

            if (vehicleToUpdate != null) {
                displayVehicleUpdateForm();

                Output.info("'V' para voltar campo, 'C' para cancelar a edição.");
            }
            switch (currentField) {
                case 0 -> {
                    if (!isSelectionListCalled) {
                        isSelectionListCalled = true;

                        VehicleListScreen vehicleListScreen = new VehicleListScreen(flowController, scanner, vehicleService, true);
                        flowController.goTo(vehicleListScreen);

                        vehicleToUpdate = vehicleListScreen.getSelectedVehicle();
                        if (vehicleToUpdate != null) {
                            model = vehicleToUpdate.getModel();
                            brand = vehicleToUpdate.getBrand();
                            plate = vehicleToUpdate.getPlate();

                            System.out.println("Veículo selecionado:");
                            System.out.println("Tipo: " + vehicleToUpdate.getType().getDescription());
                        }

                        flowController.goBack();
                    }

                    if (vehicleToUpdate == null) {
                        Output.error("Você precisa selecionar um veículo válido!");

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
                    String inputModel = Input.getAsString(scanner, "Modelo: ", true, false);
                    if (processInputCommands(inputModel)) break;
                    model = inputModel.trim().isEmpty() ? model : inputModel;
                    currentField = 2;
                }
                case 2 -> {
                    String brandInput = Input.getAsString(scanner, "Marca: ", true, false);
                    if (processInputCommands(brandInput)) break;
                    brand = brandInput.trim().isEmpty() ? brand : brandInput;
                    currentField = 3;
                }
                case 3 -> {
                    String plateInput = Input.getAsString(scanner, "placa: ", true, false);
                    if (processInputCommands(plateInput)) break;
                    plate = plateInput.trim().isEmpty() ? plate : plateInput;
                    currentField = 4;
                }
                case 4 -> confirmUpdate();
            }
        } while (true);
    }

    private void displayVehicleUpdateForm() {

        String modelPrompt = "Modelo:";
        String brandPrompt = "Marca:";
        String platePrompt = "Placa:";

        String emptyLine = "║    " + " ".repeat(MAX_LINE_LENGTH) + "    ║";
        String bottomLine = "╚════" + "═".repeat(MAX_LINE_LENGTH) + "════╝";

//        System.out.println(topLine);
        System.out.println(emptyLine);
        System.out.printf("║    %-65s    ║%n", modelPrompt + (model.isEmpty() ? "" : " " + model));
        System.out.printf("║    %-65s    ║%n", brandPrompt + (brand.isEmpty() ? "" : " " + brand));
        System.out.printf("║    %-65s    ║%n", platePrompt + (plate.isEmpty() ? "" : " " + plate));
        System.out.println(emptyLine);
        System.out.println(bottomLine);
    }

    private void confirmUpdate() {
        String input = Input.getAsString(scanner, "Confirmar alterações? (S/n): ", true, false);
        input = input.isEmpty() ? "s" : input;

        if (input.equalsIgnoreCase("s")) {
            // Chamar o serviço de atualização
            switch (vehicleToUpdate.getType()) {
                case CAR -> {
                    Vehicle updatedVehicle = new Car(vehicleToUpdate.getId(), model, brand, plate, vehicleToUpdate.getAgency());
                    vehicleService.updateVehicle(updatedVehicle);
                }
                case MOTORCYCLE -> {
                    Vehicle updatedVehicle = new Motorcycle(vehicleToUpdate.getId(), model, brand, plate, vehicleToUpdate.getAgency());
                    vehicleService.updateVehicle(updatedVehicle);
                }
                case TRUCK -> {
                    Vehicle updatedVehicle = new Truck(vehicleToUpdate.getId(), model, brand, plate, vehicleToUpdate.getAgency());
                    vehicleService.updateVehicle(updatedVehicle);
                }
            }

            try {
                vehicleService.updateVehicle(vehicleToUpdate);
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
