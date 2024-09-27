package ui.utils;

public class ScreenUtils {
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void showHeader(String title) {
        clearScreen();
        System.out.println("=================================================");
        System.out.println("               CodersRentalApp                   ");
        System.out.println("=================================================");
        System.out.println(title);
        System.out.println("-------------------------------------------------");
    }

}
