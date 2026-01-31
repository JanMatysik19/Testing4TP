package ZADANIE.Controllers;

import ZADANIE.Config.AppConstants;
import ZADANIE.Models.TableModel;
import ZADANIE.Services.TaskService;
import ZADANIE.Views.ControlPanel;
import ZADANIE.Views.DataPanel;
import ZADANIE.Views.InsertPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.ActionListener;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    @Mock private TaskService taskService;
    @Mock private TableModel tableModel;
    @Mock private ControlPanel controlPanel;
    @Mock private InsertPanel insertPanel;
    @Mock
    private DataPanel dataPanel;

    ArgumentCaptor<ActionListener> insertCaptor;
    ArgumentCaptor<ActionListener> deleteCaptor;
    ArgumentCaptor<ActionListener> refreshCaptor;

    @BeforeEach
    void setUp() {
        insertCaptor = ArgumentCaptor.forClass(ActionListener.class);
        deleteCaptor = ArgumentCaptor.forClass(ActionListener.class);
        refreshCaptor = ArgumentCaptor.forClass(ActionListener.class);

        new TaskController(taskService, tableModel, controlPanel, insertPanel, dataPanel);

        verify(insertPanel).setInsertHandler(insertCaptor.capture());
        verify(controlPanel).setDeleteHandler(deleteCaptor.capture());
        verify(controlPanel).setRefreshHandler(refreshCaptor.capture());
    }

    @ParameterizedTest
    @CsvSource({
            "'Zrobić to zadanie', 'Zrobione'",
            "'Nie zrobić tego zadania', 'Oczekujące'",
            "'Super zadanie', 'Oczekujące'"
    })
    void testSuccess_handleInsert(String content, String status) {
        // Arrange
        when(insertPanel.getContent()).thenReturn(content);
        when(insertPanel.getStatus()).thenReturn(status);
        when(taskService.insert(content, status)).thenReturn(null);

        // Act - Simulate the insert button click
        insertCaptor.getValue().actionPerformed(null);

        // Verify
        verify(taskService, atLeastOnce()).fetchAll();
        verify(taskService).insert(content, status);
        verify(insertPanel, never()).displayValidationError(any());
    }

    @ParameterizedTest
    @CsvSource({
            "'as', 'Zrobione', '- Treść musi mieć co najmniej %d znaków\n'",
            "'Zadanie do zrobienia', 'Niezrobione', '- Nieznany status\n'",
            "' as', 'Niezrobione', '- Treść musi mieć co najmniej %d znaków\n- Nieznany status\n'"
    })
    void testFailure_handleInsert(String content, String status, String error) {
        // Arrange
        when(insertPanel.getContent()).thenReturn(content);
        when(insertPanel.getStatus()).thenReturn(status);
        when(taskService.insert(content, status)).thenReturn(error.formatted(AppConstants.MINIMUM_TASK_CONTENT_LENGTH));

        // Act - Simulate the insert button click
        insertCaptor.getValue().actionPerformed(null);

        // Verify
        verify(taskService, atLeastOnce()).fetchAll();
        verify(taskService).insert(content, status);
        verify(insertPanel).displayValidationError(error.formatted(AppConstants.MINIMUM_TASK_CONTENT_LENGTH));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 3",
            "123, 1"
    })
    void testSuccess_handleDelete(int row, int id) {
        // Arrange
        when(dataPanel.getSelectedRow()).thenReturn(row);
        when(tableModel.getTaskId(row)).thenReturn(id);
        when(taskService.delete(row, id)).thenReturn(null);

        // Act - Simulate the delete button click
        deleteCaptor.getValue().actionPerformed(null);

        // Verify
        verify(taskService, atLeastOnce()).fetchAll();
        verify(taskService).delete(row, id);
        verify(controlPanel, never()).displayValidationError(any());
    }

    @ParameterizedTest
    @CsvSource({
            "-1, '- Brak wybranego zadania do usunięcia\n'",
            "-9241, '- Brak wybranego zadania do usunięcia\n'",
            "23451, '- Brak wybranego zadania do usunięcia\n'"
    })
    void testFailure_handleDelete(int row, String error) {
        // Arrange
        when(dataPanel.getSelectedRow()).thenReturn(row);
        when(tableModel.getTaskId(row)).thenReturn(-1);
        when(taskService.delete(row, -1)).thenReturn(error);

        // Act - Simulate the delete button click
        deleteCaptor.getValue().actionPerformed(null);

        // Verify
        verify(taskService, atMostOnce()).fetchAll();
        verify(taskService).delete(row, -1);
        verify(controlPanel).displayValidationError(error);
    }

    @Test
    void testSuccess_handleRefresh() {
        // Arrange

        // Act - Simulate the delete button click
        refreshCaptor.getValue().actionPerformed(null);

        // Verify
        verify(taskService, atLeastOnce()).fetchAll();
    }
}
