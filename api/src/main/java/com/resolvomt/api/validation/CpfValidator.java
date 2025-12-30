package com.resolvomt.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ValidCpf, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] digits = cpf.chars().map(c -> c - '0').toArray();

            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * (10 - i);
            }
            int firstCheck = 11 - (sum % 11);
            if (firstCheck >= 10) firstCheck = 0;
            if (digits[9] != firstCheck) return false;

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * (11 - i);
            }
            int secondCheck = 11 - (sum % 11);
            if (secondCheck >= 10) secondCheck = 0;

            return digits[10] == secondCheck;

        } catch (Exception e) {
            return false;
        }
    }
}
