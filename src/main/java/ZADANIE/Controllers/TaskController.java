package ZADANIE.Controllers;

import ZADANIE.Models.TableModel;
import ZADANIE.Services.TaskService;
import ZADANIE.Views.ControlPanel;
import ZADANIE.Views.DataPanel;
import ZADANIE.Views.InsertPanel;

import java.awt.event.ActionEvent;

public class TaskController {
    private final TaskService taskService;
    private final ControlPanel controlPanel;
    private final InsertPanel insertPanel;
    private final DataPanel dataPanel;
    private final TableModel tableModel;

    public TaskController(TaskService taskService, TableModel tableModel, ControlPanel controlPanel, InsertPanel insertPanel, DataPanel dataPanel) {
        this.taskService = taskService;
        this.controlPanel = controlPanel;
        this.insertPanel = insertPanel;
        this.dataPanel = dataPanel;
        this.tableModel = tableModel;
        bind();
        refresh();
    }

    private void bind() {
        controlPanel.setDeleteHandler(this::handleDelete);
        controlPanel.setRefreshHandler(this::handleRefresh);
        insertPanel.setInsertHandler(this::handleInsert);
        dataPanel.setTableModel(tableModel);
    }

    private void handleInsert(ActionEvent event) {
        final var content = insertPanel.getContent();
        final var status = insertPanel.getStatus();

        final var error = taskService.insert(content, status);
        if(error != null) insertPanel.displayValidationError(error);
        else refresh();
    }

    private void handleDelete(ActionEvent event) {
        final var selectedRow = dataPanel.getSelectedRow();
        final var taskId = tableModel.getTaskId(selectedRow);

        final var error = taskService.delete(selectedRow, taskId);
        if(error != null) controlPanel.displayValidationError(error);
        else refresh();
    }

    private void handleRefresh(ActionEvent event) {
        refresh();
    }

    private void refresh() {
        final var data = taskService.fetchAll();
        tableModel.setData(data);
    }
}
