package ZADANIE.Utils;

import ZADANIE.Config.AppConstants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class InsertValidatorTest {
    @ParameterizedTest
    @CsvSource({
            "'Zjeść kanapkę', ''",
            "'Odrobić lekcje', ''",
            "'12', '- Treść musi mieć co najmniej %d znaków\n'",
            "' Ab', '- Treść musi mieć co najmniej %d znaków\n'",
    })
    void test_ValidateContent(String content, String expErr) {
        // Arrange
        final var validator = new InsertValidator(content, null);

        // Act
        final var result = validator.validateContent();

        // Assert
        assertEquals(expErr.formatted(AppConstants.MINIMUM_TASK_CONTENT_LENGTH), result.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "'Zrobione', ''",
            "'Oczekujące', ''",
            "'Nieoczekujące', '- Nieznany status\n'",
            "'', '- Nieznany status\n'",
    })
    void validateStatus(String status, String expErr) {
        // Arrange
        final var validator = new InsertValidator(null, status);

        // Act
        final var result = validator.validateStatus();

        // Assert
        assertEquals(expErr, result.toString());
    }
}