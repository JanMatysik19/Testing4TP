package ZADANIE.Models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModel extends AbstractTableModel {
    private List<TaskModel> data;
    private final String[] COLUMN_NAMES;

    public TableModel() {
        data = new ArrayList<>();
        COLUMN_NAMES = new String[]{ "Id", "Content", "Status" };
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).getAt(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void setData(List<TaskModel> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public int getTaskId(int rowIndex) {
        if(rowIndex < 0 || rowIndex >= data.size()) return -1;
        else return data.get(rowIndex).getId();
    }
}
