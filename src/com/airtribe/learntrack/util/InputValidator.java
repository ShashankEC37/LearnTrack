package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

public final class InputValidator {
    private InputValidator() {
    }

    public static int parseInt(String text) throws InvalidInputException {
        if (text == null) {
            throw new InvalidInputException("Input cannot be empty.");
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + text + "' is not a valid number.");
        }
    }

    public static int parsePositiveInt(String text) throws InvalidInputException {
        int value = parseInt(text);
        if (value <= 0) {
            throw new InvalidInputException("Please enter a number greater than 0.");
        }
        return value;
    }

    public static String requireNonEmpty(String text, String fieldName) throws InvalidInputException {
        if (text == null || text.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return text.trim();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    }
}
