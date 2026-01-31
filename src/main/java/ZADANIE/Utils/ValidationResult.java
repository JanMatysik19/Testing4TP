package ZADANIE.Utils;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private final List<String> errors;

    public ValidationResult() {
        errors = new ArrayList<>();
    }

    public void add(String error) {
        errors.add(error);
    }

    public void add(ValidationResult result) {
        errors.addAll(result.getErrors());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(var error : errors) sb.append("- ").append(error).append("\n");

        return sb.toString();
    }

    public List<String> getErrors() {
        return errors;
    }
}
