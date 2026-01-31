package ZADANIE.Utils;

public class DeleteValidator {
    private final int row;

    public DeleteValidator(int row) {
        this.row = row;
    }

    public ValidationResult validateAll() {
        final var result = new ValidationResult();
        result.add(validateRow());

        return result;
    }

    public ValidationResult validateRow() {
        final var result = new ValidationResult();
        if(row < 0)
            result.add("Brak wybranego zadania do usunięcia");

        return result;
    }
}
