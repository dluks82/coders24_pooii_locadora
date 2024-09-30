package ui.utils;

import enums.Color;
import exceptions.DataInputInterruptedException;
import utils.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Input {

    static String emptyLine = "";

    public static Result<Integer> getAsInt(Scanner scanner, String promptMessage, boolean canBeNegative) {
        String value = InputUtils.getInput(scanner, promptMessage, false);
        return InputUtils.parseInt(value, canBeNegative);
    }

    public static Result<String> getAsString2(Scanner scanner, String promptMessage, boolean canBeEmpty) {
        String value = InputUtils.getInput(scanner, promptMessage, false);
        return InputUtils.parseString(value, canBeEmpty);
    }

    public static Result<LocalDateTime> getAsDateTime(Scanner scanner, String promptMessage, boolean canBeEmpty) {
        String value = InputUtils.getInput(scanner, promptMessage, false);
        return InputUtils.parseDateTime(value);
    }

    public static Result<Double> getAsDouble(Scanner scanner, String promptMessage, boolean canBeNegative) {
        String value = InputUtils.getInput(scanner, promptMessage, false);
        return InputUtils.parseDouble(value, canBeNegative);
    }

    public static Result<BigDecimal> getAsBigDecimal(Scanner scanner, String promptMessage, boolean canBeNegative) {
        String value = InputUtils.getInput(scanner, promptMessage, false);
        return InputUtils.parseBigDecimal(value, canBeNegative);
    }

    public static Result<Boolean> getAsBoolean(Scanner scanner, String promptMessage) {
        String value = InputUtils.getInput(scanner, promptMessage, false);
        return InputUtils.parseBoolean(value);
    }

    public static String getAsString(Scanner scanner, String promptMessage, boolean canBeEmpty, boolean hidden) {
        while (true) {
            Output.prompt(promptMessage);
            if (hidden) System.out.print(Color.WHITE.getCode() + Color.BG_WHITE.getCode());
            String value = scanner.nextLine().trim();
            if (hidden) System.out.print(Color.RESET.getCode());

            if (value.equalsIgnoreCase("cancel")) throw new DataInputInterruptedException();

            if (!value.isEmpty() || canBeEmpty) {
                return value;
            }
            Output.error("A entrada não pode estar vazia.");
            System.out.println(emptyLine);
        }
    }

    public static String getAsCPF(Scanner scanner, String promptMessage, boolean canBeEmpty) {
        while (true) {
            Output.prompt(promptMessage);
            String value = scanner.nextLine().trim();

            if (value.equalsIgnoreCase("cancel")) throw new DataInputInterruptedException();

            if (value.isEmpty() && canBeEmpty) return value;

            if (Validator.isValidCpf(value)) return value;

            Output.error("CPF inválido! Por favor, tente novamente...");
            System.out.println(emptyLine);
        }
    }

}
