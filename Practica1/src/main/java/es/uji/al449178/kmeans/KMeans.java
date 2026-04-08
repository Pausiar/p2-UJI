package es.uji.al449178.kmeans;

import es.uji.al449178.knn.Distance;
import es.uji.al449178.table.Row;
import es.uji.al449178.table.Table;

import java.util.*;

public class KMeans implements Algorithm<Table, List<Double>, Integer> {
    private final int numClusters;
    private final int numIterations;
    private final long seed;
    private final List<Row> representatives = new ArrayList<>();
    private final Map<Integer, List<Row>> clusters = new HashMap<>();

    public KMeans(int numClusters, int numIterations, long seed){
        this.numClusters=numClusters;
        this.numIterations=numIterations;
        this.seed=seed;
    }

    @Override
    public void train (Table data) throws InvalidClusterNumberException {
        validateClusterCount(data);
        representatives.clear();
        initialiseRepresentatives(data);

        for(int iteration = 0; iteration < numIterations; iteration++){
            resetClusters();
            assignRowsToClosestCluster(data);
            updateRepresentatives();
        }
    }

    @Override
    public Integer estimate(List<Double> sample){
        if (representatives.isEmpty()) {
            throw new IllegalStateException("kmeans must be trained before estimate");
        }

        int closestRepresentativeIndex = -1;
        double minDistance = Double.MAX_VALUE;

        for(int index = 0; index < representatives.size(); index++){
            double distance = Distance.euclideanDistance(sample, representatives.get(index).getData());
            if (distance < minDistance){
                minDistance = distance;
                closestRepresentativeIndex = index;
            }
        }
        return closestRepresentativeIndex + 1;
    }

    private void validateClusterCount(Table data) throws InvalidClusterNumberException {
        if (numClusters > data.getRowCount()) {
            throw new InvalidClusterNumberException(numClusters, data.getRowCount());
        }
    }

    private void initialiseRepresentatives(Table data) {
        Random random = new Random(seed);
        while (representatives.size() < numClusters) {
            Row representative = data.getRowAt(random.nextInt(data.getRowCount()));
            if (!representatives.contains(representative)) {
                representatives.add(representative);
            }
        }
    }

    private void resetClusters() {
        clusters.clear();
        for (int clusterId = 1; clusterId <= numClusters; clusterId++) {
            clusters.put(clusterId, new ArrayList<>());
        }
    }

    private void assignRowsToClosestCluster(Table data) {
        for (int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++) {
            Row row = data.getRowAt(rowIndex);
            int closestCluster = estimate(row.getData());
            clusters.get(closestCluster).add(row);
        }
    }

    private void updateRepresentatives() {
        for (int clusterIndex = 0; clusterIndex < numClusters; clusterIndex++) {
            List<Row> clusterRows = clusters.get(clusterIndex + 1);
            if (!clusterRows.isEmpty()) {
                representatives.set(clusterIndex, calculateCentroid(clusterRows));
            }
        }
    }

    private Row calculateCentroid(List<Row> clusterRows){
        int dimensions = clusterRows.get(0).getData().size();
        double[] sums = new double[dimensions];
        for (Row row : clusterRows){
            for (int index = 0; index < dimensions; index++){
                sums[index] += row.getData().get(index);
            }
        }
        List<Double> centroid = new ArrayList<>();
        for (double sum : sums){
            centroid.add(sum / clusterRows.size());
        }
        return new Row(centroid);
    }
}
