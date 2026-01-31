package ZADANIE;

import ZADANIE.Config.DatabaseConfig;
import ZADANIE.Controllers.TaskController;
import ZADANIE.Dao.TaskDao;
import ZADANIE.Models.TableModel;
import ZADANIE.Services.TaskService;
import ZADANIE.Views.ControlPanel;
import ZADANIE.Views.DataPanel;
import ZADANIE.Views.Frame;
import ZADANIE.Views.InsertPanel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

class Main {
    private Connection dbConnection;

    private Main() {
        initFlatLaf();
        initApp();
    }

    private void initFlatLaf() {
        FlatDarkLaf.registerCustomDefaultsSource("themes");
        FlatMacDarkLaf.setup();
    }

    private void initApp() {
        try {
            dbConnection = new DatabaseConfig().getConnection();
            final var content = bindAndPrepare(dbConnection);
            final var frame = new Frame(this::handleWindowClosing);
            frame.display(content);
        } catch (Exception e) {
            System.out.println("Could not initialise the application.");
            throw new RuntimeException(e);
        }
    }

    private Frame.Content bindAndPrepare(Connection dbConnection) {
        final var controlPanel = new ControlPanel();
        final var insertPanel = new InsertPanel();
        final var dataPanel = new DataPanel();

        final var tableModel = new TableModel();
        final var taskDao = new TaskDao(dbConnection);
        final var taskService = new TaskService(taskDao);

        new TaskController(taskService, tableModel, controlPanel, insertPanel, dataPanel);
        return new Frame.Content(controlPanel, insertPanel, dataPanel);
    }

    public void handleWindowClosing(WindowEvent event) {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println("Could not close the database connection.");
            throw new RuntimeException(e);
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        new Main();
    }
}
