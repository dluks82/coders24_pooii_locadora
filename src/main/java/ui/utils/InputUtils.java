package ui.utils;

import enums.Color;
import exceptions.DataInputInterruptedException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputUtils {

    private static final String CANCEL_COMMAND = "cancel";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

    public static String getInput(Scanner scanner, String promptMessage, boolean isHidden) {
        Output.prompt(promptMessage);
        if (isHidden) System.out.print(Color.WHITE.getCode() + Color.BG_WHITE.getCode());
        String value = scanner.nextLine().trim();
        if (isHidden) System.out.print(Color.RESET.getCode());

        if (value.equalsIgnoreCase(CANCEL_COMMAND)) throw new DataInputInterruptedException();
        return value;
    }

    public static Result<Integer> parseInt(String value, boolean canBeNegative) {
        try {
            int intValue = Integer.parseInt(value);
            if (intValue >= 0 || canBeNegative)
                return Result.success(intValue);
            return Result.fail("O valor deve ser positivo.");
        } catch (NumberFormatException e) {
            return Result.fail("O valor deve ser um número inteiro.");
        }
    }

    public static Result<Double> parseDouble(String value, boolean canBeNegative) {
        try {
            double doubleValue = Double.parseDouble(value);
            if (doubleValue >= 0 || canBeNegative)
                return Result.success(doubleValue);
            return Result.fail("O valor deve ser positivo.");
        } catch (NumberFormatException e) {
            return Result.fail("O valor deve ser um número decimal.");
        }
    }

    public static Result<LocalDateTime> parseDateTime(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        try {
            LocalDateTime dateTime = LocalDateTime.parse(value, formatter);
            return Result.success(dateTime);
        } catch (Exception e) {
            return Result.fail("Data e hora inválidas. Formato esperado: " + DATE_TIME_FORMAT);
        }
    }

    public static Result<BigDecimal> parseBigDecimal(String value, boolean canBeNegative) {
        try {
            BigDecimal parsedValue = new BigDecimal(value);
            if (parsedValue.compareTo(BigDecimal.ZERO) >= 0 || canBeNegative)
                return Result.success(parsedValue);
            return Result.fail("O valor deve ser positivo.");
        } catch (NumberFormatException e) {
            return Result.fail("O valor deve ser um número decimal.");
        }
    }

    public static Result<Boolean> parseBoolean(String value) {
        if (value.equalsIgnoreCase("sim") || value.equalsIgnoreCase("s"))
            return Result.success(true);
        if (value.equalsIgnoreCase("não") || value.equalsIgnoreCase("n"))
            return Result.success(false);
        return Result.fail("Valor inválido. Digite 'sim' ou 'não'.");
    }

    public static Result<String> parseString(String value, boolean canBeEmpty) {
        if (value.isEmpty() && !canBeEmpty)
            return Result.fail("O valor não pode ser vazio.");
        return Result.success(value);
    }
}
