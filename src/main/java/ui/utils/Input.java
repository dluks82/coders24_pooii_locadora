package ui.utils;

import enums.Color;
import exceptions.DataInputInterruptedException;
import utils.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Input {

    static String emptyLine = "";

    public static Result<Integer> getAsInt(Scanner scanner, String promptMessage, boolean canBeNegative) {
        Output.prompt(promptMessage);
        try {
            String value = scanner.nextLine();

            if (value.equalsIgnoreCase("cancel")) throw new DataInputInterruptedException();

            int parsedValue = Integer.parseInt(value);

            if (parsedValue >= 0 || canBeNegative) return Result.success(parsedValue);

            return Result.fail("Não pode ser negativo!");
        } catch (NumberFormatException e) {
            return Result.fail("Valor não é válido!");
        } catch (Exception e) {
            return Result.fail("Erro desconhecido ao processar a entrada.");
        }
    }

    public static Result<String> getAsString2(Scanner scanner, String promptMessage, boolean canBeEmpty, boolean hidden) {
        Output.prompt(promptMessage);
        try {
            if (hidden) System.out.print(Color.WHITE.getCode() + Color.BG_WHITE.getCode());
            String value = scanner.nextLine().trim();
            if (hidden) System.out.print(Color.RESET.getCode());

            if (value.equalsIgnoreCase("cancel")) throw new DataInputInterruptedException();

            if (!value.isEmpty() || canBeEmpty) return Result.success(value);

            return Result.fail("A entrada não pode estar vazia.");
        } catch (Exception e) {
            return Result.fail("Erro desconhecido ao processar a entrada.");
        }
    }

    public static Result<LocalDate> getAsDate(Scanner scanner, String promptMessage, boolean canBeEmpty) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Output.prompt(promptMessage);
        try {
            String value = scanner.nextLine().trim();

            if (canBeEmpty && value.isEmpty()) return Result.success(null);

            if (value.equalsIgnoreCase("cancel")) throw new DataInputInterruptedException();

            LocalDate date = LocalDate.parse(value, formatter);

            return Result.success(date);
        } catch (DateTimeParseException e) {
            return Result.fail("Data inválida! Por favor, insira uma data no formato dd/MM/aaaa.");
        } catch (Exception e) {
            return Result.fail("Erro desconhecido ao processar a data.");
        }
    }

    public static double getAsDouble(Scanner scanner, String promptMessage, boolean canBeNegative) {
        while (true) {
            Output.prompt(promptMessage);
            try {
                String value = scanner.nextLine();

                if (value.equalsIgnoreCase("cancel")) throw new DataInputInterruptedException();

                double parsedValue = Double.parseDouble(value);
                if (parsedValue >= 0 || canBeNegative) {
                    return parsedValue;
                }
                Output.error("Não pode ser negativo!");
                System.out.println(emptyLine);
            } catch (NumberFormatException e) {
                Output.error("Valor inválido! Por favor tente novamente...");
                System.out.println(emptyLine);
            }
        }
    }

    public static BigDecimal getAsBigDecimal(Scanner scanner, String promptMessage, boolean canBeNegative) {
        while (true) {
            Output.prompt(promptMessage);
            try {
                String value = scanner.nextLine();

                if (value.equalsIgnoreCase("cancel")) throw new DataInputInterruptedException();

                double parsedValue = Double.parseDouble(value);

                BigDecimal BDValue = BigDecimal.valueOf(parsedValue);
                if (BDValue.compareTo(BigDecimal.ZERO) >= 0 || canBeNegative) {
                    return BDValue;
                }
                Output.error("Não pode ser negativo!");
                System.out.println(emptyLine);
            } catch (NumberFormatException e) {
                Output.error("Valor inválido! Por favor tente novamente...");
                System.out.println(emptyLine);
            }
        }
    }

    public static long getAsLong(Scanner scanner, String promptMessage, boolean canBeNegative) {
        while (true) {
            Output.prompt(promptMessage);
            try {
                String value = scanner.nextLine();

                if (value.equalsIgnoreCase("cancel")) throw new DataInputInterruptedException();

                long parsedValue = Long.parseLong(value);

                if (parsedValue >= 0 || canBeNegative) {
                    return parsedValue;
                }
                Output.error("Não pode ser negativo!");
                System.out.println(emptyLine);
            } catch (NumberFormatException e) {
                Output.error("Valor inválido! Por favor, tente novamente...");
                System.out.println(emptyLine);
            }
        }
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
