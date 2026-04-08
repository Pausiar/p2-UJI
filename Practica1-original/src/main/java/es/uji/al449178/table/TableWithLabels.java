package es.uji.al449178.table;

import java.util.HashMap;
import java.util.Map;
//terminado
public class TableWithLabels extends Table {

    private Map<String, Integer> labelsToIndex;
    private String HeaderExtra; //Añadido el headerextra como atributo
    public TableWithLabels() {
        super();
        labelsToIndex = new HashMap<>();
    }
//Arreglado el uso de un cast Innecesario
    public void addRow(RowWithLabel row) {
        super.addRow(row);
        addLabel(row.getLabel());
    }
    public void setHeaderExtra(String name){
        this.HeaderExtra= name;
    }

    private void addLabel(String label) {
        if (!labelsToIndex.containsKey(label)) {
            labelsToIndex.put(label, labelsToIndex.size());
        }
    }

    @Override
    public RowWithLabel getRowAt(int rowNumber) { return (RowWithLabel) rows.get(rowNumber);
    }

    public Integer getLabelAsInteger(String label) {
        return labelsToIndex.get(label);
    }
}
