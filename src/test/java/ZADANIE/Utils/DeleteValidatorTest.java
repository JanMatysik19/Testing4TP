package ZADANIE.Utils;

import ZADANIE.Config.AppConstants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "0, ''",
            "1, ''",
            "-12, '- Brak wybranego zadania do usunięcia\n'",
            "-1, '- Brak wybranego zadania do usunięcia\n'",
    })
    void validateRow(int tableRow, String expErr) {
        // Arrange
        final var validator = new DeleteValidator(tableRow);
        // Act
        final var result = validator.validateRow();
        // Assert
        assertEquals(expErr, result.toString());
    }
}