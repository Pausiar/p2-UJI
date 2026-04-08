package es.uji.al449178.kmeans;

public class InvalidClusterNumberException  extends Exception {
    private final int numClusters;
    private final int numDatos;

    public InvalidClusterNumberException(int numClusters, int numDatos){
        super("numero de clusters ("+ numClusters+") es mayor que el numero de datos en la tabla ("+numDatos+")");
        this.numClusters = numClusters;
        this.numDatos = numDatos;
    }

    public int getNumberOfClusters() {
        return numClusters;
    }

    public int getNumberOfDatos() {
        return numDatos;
    }
}
