package ui.screens.vehicle;

import model.vehicle.Vehicle;
import service.vehicle.VehicleService;
import ui.core.Screen;
import ui.flow.FlowController;
import ui.utils.Input;
import ui.utils.Output;
import ui.utils.ScreenUtils;

import java.util.Scanner;

public class VehicleUpdateScreen extends Screen {
    private final Scanner scanner;
    private final VehicleService vehicleService;
    private Vehicle vehicleToUpdate;

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

            System.out.println("=== Editar Veículo ===");

            if (vehicleToUpdate != null) {
                System.out.println("Modelo: " + vehicleToUpdate.getModel());
                System.out.println("Marca: " + vehicleToUpdate.getBrand());
                System.out.println("Placa: " + vehicleToUpdate.getPlate());
            }
            switch (currentField) {
                case 0 -> {
                    if (!isSelectionListCalled) {
                        isSelectionListCalled = true;

                        VehicleListScreen vehicleListScreen = new VehicleListScreen(flowController, scanner, vehicleService, true);
                        flowController.goTo(vehicleListScreen);

                        vehicleToUpdate = vehicleListScreen.getSelectedVehicle();

                        flowController.goBack();
                    }

                    if (vehicleToUpdate == null) {
                        Output.error("Você precisa selecionar um veículo válido!");

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
                    String inputModel = Input.getAsString(scanner, "Modelo: ", true, false);
                    if (processInputCommands(inputModel)) break;
                    if (!inputModel.isEmpty())
                        vehicleToUpdate.setModel(inputModel);
                    currentField = 2;
                }
                case 2 -> {
                    String brandInput = Input.getAsString(scanner, "Marca: ", true, false);
                    if (processInputCommands(brandInput)) break;
                    if (!brandInput.isEmpty())
                        vehicleToUpdate.setBrand(brandInput);
                    currentField = 3;
                }
                case 3 -> {
                    String plateInput = Input.getAsString(scanner, "placa: ", true, false);
                    if (processInputCommands(plateInput)) break;
                    if (!plateInput.isEmpty())
                        vehicleToUpdate.setPlate(plateInput);
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
            vehicleService.updateVehicle(vehicleToUpdate);

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
