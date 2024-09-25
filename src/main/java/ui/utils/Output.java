package ui.utils;

import enums.Color;

public class Output {
//    private static final String PREFIX = "║ ";
    private static final String PREFIX = "";

    public static void message(String message) {
        System.out.println(PREFIX + message);
    }

    public static void prompt(String message) {
        System.out.print(PREFIX + " -> " + Color.GREEN.apply(message));
    }

    public static void error(String message) {
        System.out.print(PREFIX + " >> " + Color.RED.apply(message));
    }

    public static void info(String message) {
        System.out.println(PREFIX + " >> " + Color.YELLOW.apply(message));
    }
}
