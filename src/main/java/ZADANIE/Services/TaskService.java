package ZADANIE.Services;

import ZADANIE.Dao.TaskDao;
import ZADANIE.Models.TaskModel;
import ZADANIE.Utils.DeleteValidator;
import ZADANIE.Utils.InsertValidator;

import java.util.List;

public class TaskService {
    private final TaskDao dao;

    public TaskService(TaskDao dao) {
        this.dao = dao;
    }

    public List<TaskModel> fetchAll() {
        return dao.fetchAll();
    }

    public String insert(String content, String status) {
        final String errors = (new InsertValidator(content, status)).validateAll().toString();
        if(!errors.isEmpty()) return errors;

        final var saved = dao.save(content.trim(), TaskModel.StatusType.fromString(status));
        if(saved) return null;
        else return "- Błąd bazy danych";
    }

    public String delete(int tableRow, int taskId) {
        final var errors = (new DeleteValidator(tableRow)).validateAll().toString();
        if(!errors.isEmpty()) return errors;

        final var deleted = dao.delete(taskId);
        if(deleted) return null;
        else return "- Błąd bazy danych";
    }
}
