package es.uji.al449178.table;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> headers;
    protected List<Row> rows;

    public Table() {
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    public Row getRowAt(int index) {
        return rows.get(index);
    }

    public int getRowCount() {
        return rows.size();
    }

    public List<Double> getColumnAt(int index) {
        List<Double> column = new ArrayList<>();
        for (Row row : rows) {
            column.add(row.getData().get(index));
        }
        return column;
    }
}
