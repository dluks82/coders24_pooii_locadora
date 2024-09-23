package utils;

import java.util.InputMismatchException;

public class Validator {

    public static boolean isValidCpf(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF tem 11 dígitos ou é uma sequência repetida (invalida)
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Validação dos dois dígitos verificadores;
        int[] cpfDigits = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfDigits[i] = Character.getNumericValue(cpf.charAt(i));
        }

        // Valida o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpfDigits[i] * (10 - i);
        }
        int firstVerifier = 11 - (sum % 11);
        firstVerifier = (firstVerifier >= 10) ? 0 : firstVerifier;

        if (cpfDigits[9] != firstVerifier) {
            return false;
        }

        // Valida o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += cpfDigits[i] * (11 - i);
        }
        int secondVerifier = 11 - (sum % 11);
        secondVerifier = (secondVerifier >= 10) ? 0 : secondVerifier;

        return cpfDigits[10] == secondVerifier;
    }


    public static boolean isValidCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");

        try {
            Long.parseLong(cnpj);
        } catch (NumberFormatException e) {
            return false;
        }

        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
                || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
                || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888") || cnpj.equals("99999999999999") || (cnpj.length() != 14))
            return (false);
        char dig13, dig14;
        int sm, i, r, num, peso; // "try" - protege o código para eventuais
        // erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-ésimo caractere do CNPJ em um número: // por
                // exemplo, transforma o caractere '0' no inteiro 0 // (48 eh a
                // posição de '0' na tabela ASCII)
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else
                dig13 = (char) ((11 - r) + 48);

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else
                dig14 = (char) ((11 - r) + 48);
            // Verifica se os dígitos calculados conferem com os dígitos
            // informados.
            if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
                return (true);
            else
                return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }


    //Limpa CPF e CNPJ
    public static String sanitizeCpf(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        return cpf;
    }

    public static String sanitizeCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");
        return cnpj;
    }

}
