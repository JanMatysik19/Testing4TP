package ZADANIE.Dao;

import ZADANIE.Config.DatabaseConfig;
import ZADANIE.Models.TaskModel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private final Connection connection;
    private final String TABLE = DatabaseConfig.TABLE.TASKS;

    public TaskDao(Connection connection) {
        this.connection = connection;
    }

    public boolean save(TaskModel task) {
        return save(task.getContent(), TaskModel.StatusType.fromString(task.getStatus()));
    }
    public boolean save(String content, TaskModel.StatusType status) {
        final var sql = """
            INSERT INTO %s(Content, Status) VALUES(?, ?);
        """.formatted(TABLE);

        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, content);
            stmt.setString(2, status.value());

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Could not save the task.");
            return false;
        }
    }

    public boolean delete(TaskModel task) {
        return delete(task.getId());
    }
    public boolean delete(int id) {
        final var sql = """
            DELETE FROM %s WHERE Id = ?;
        """.formatted(TABLE);

        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Could not delete the task.");
            return false;
        }
    }

    public List<TaskModel> fetchAll() {
        final var sql = """
            SELECT Id, Content, Status FROM %s;
        """.formatted(TABLE);

        try (var stmt = connection.createStatement()) {
            List<TaskModel> out = new ArrayList<>();
            var rs = stmt.executeQuery(sql);

            while (rs.next()) out.add(new TaskModel(
                    rs.getInt("Id"), rs.getString("Content"), TaskModel.StatusType.fromString(rs.getString("Status"))
            ));

            return out;
        } catch (Exception e) {
            System.out.println("Could not fetch all the task.");
            return null;
        }
    }
}
