package ZADANIE.Utils;

import ZADANIE.Config.AppConstants;
import ZADANIE.Models.TaskModel;

public class InsertValidator {
    private final String content;
    private final String status;

    public InsertValidator(String content, String status) {
        this.content = content;
        this.status = status;
    }

    public ValidationResult validateAll() {
        final var result = new ValidationResult();
        result.add(validateContent());
        result.add(validateStatus());

        return result;
    }

    public ValidationResult validateContent() {
        final var result = new ValidationResult();
        if(content.trim().isBlank() || content.trim().length() < AppConstants.MINIMUM_TASK_CONTENT_LENGTH)
            result.add("Treść musi mieć co najmniej %d znaków".formatted(AppConstants.MINIMUM_TASK_CONTENT_LENGTH));

        return result;
    }

    public ValidationResult validateStatus() {
        final var result = new ValidationResult();
        if(TaskModel.StatusType.fromString(status) == null)
            result.add("Nieznany status");

        return result;
    }
}
