package es.uji.al449178.table;

import java.util.HashMap;
import java.util.Map;

public class TableWithLabels extends Table {

    private Map<String, Integer> labelsToIndex;
    private String extraHeader;

    public TableWithLabels() {
        super();
        labelsToIndex = new HashMap<>();
    }

    public void addRow(RowWithLabel row) {
        super.addRow(row);
        addLabel(row.getLabel());
    }

    public void setHeaderExtra(String name){
        this.extraHeader = name;
    }

    private void addLabel(String label) {
        if (!labelsToIndex.containsKey(label)) {
            labelsToIndex.put(label, labelsToIndex.size());
        }
    }

    @Override
    public RowWithLabel getRowAt(int rowNumber) {
        return (RowWithLabel) rows.get(rowNumber);
    }

    public Integer getLabelAsInteger(String label) {
        return labelsToIndex.get(label);
    }
}
