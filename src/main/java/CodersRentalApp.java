import ui.flow.FlowController;
import ui.screens.MainMenuScreen;

import java.util.Scanner;

public class CodersRentalApp {

    public static void main(String[] args) {

        FlowController flowController = new FlowController();

        Scanner scanner = new Scanner(System.in);

        flowController.goTo(new MainMenuScreen(flowController, scanner));

    }
}
