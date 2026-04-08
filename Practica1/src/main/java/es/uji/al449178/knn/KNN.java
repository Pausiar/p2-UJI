package es.uji.al449178.knn;

import es.uji.al449178.kmeans.Algorithm;
import es.uji.al449178.table.RowWithLabel;
import es.uji.al449178.table.TableWithLabels;

import java.util.List;
import java.util.NoSuchElementException;

public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {
    private TableWithLabels data;

    @Override
    public void train(TableWithLabels data) {
        this.data=data;
    }

    @Override
    public Integer estimate(List<Double> sample) {
        if(sample.isEmpty()) {
            throw new NoSuchElementException();
        }
        double minDist=-1;
        Integer bestlabel=null;

        RowWithLabel row = data.getRowAt(0);
        double distance = Distance.euclideanDistance(sample,row.getData());
        minDist=distance;
        bestlabel=data.getLabelAsInteger(row.getLabel());

        for(int i=1; i<data.getRowCount(); i++){
            row = data.getRowAt(i);
            distance = Distance.euclideanDistance(sample,row.getData());
            if (distance < minDist){
                minDist=distance;
                bestlabel=data.getLabelAsInteger(row.getLabel());
            }
        }
        return bestlabel;
    }
}
