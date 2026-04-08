package es.uji.al449178.kmeans;

import es.uji.al449178.table.Row;
import es.uji.al449178.table.Table;

import java.util.*;

public class KMeans implements Algorithm<Table, List<Double>, Integer> {
    private final int numClusters;
    private final int numIterations;
    private final long seed; //semilla aleatoria
    private List<Row> representantes = new ArrayList<>(); //los representantes de cada grupo
    private Map<Integer, List<Row>> clusters = new HashMap<>(); //grupos (indice, datos pertenencientes al grupo)

    public KMeans(int numClusters, int numIterations, long seed){
        this.numClusters=numClusters; //número de grupos
        this.numIterations=numIterations; //número de iteraciones
        this.seed=seed;
    }

    @Override
    public void train (Table datos) throws InvalidClusterNumberException {
        if (this.numClusters>datos.getRowCount()){
            throw new InvalidClusterNumberException(numClusters, datos.getRowCount());
        }

        Random random = new Random(seed); //generamos semilla
        while (representantes.size()< numClusters){
            Row repre = datos.getRowAt(random.nextInt(datos.getRowCount())); //sacamos de forma aleatoria un representante
            if(!representantes.contains(repre)){
                representantes.add(repre);
            }
        }

        for(int a=0; a<numIterations; a++){//empezamos a asignar a cada dato su grupo
            for (int i =1; i <= numClusters; i++){
                clusters.put(i, new ArrayList<>());
            }

            for (int j=0; j < datos.getRowCount(); j++){
                Row fila = datos.getRowAt(j);
                int clusterCercano = estimate(fila.getData());
                clusters.get(clusterCercano).add(fila);
            }

            for(int i=0; i < numClusters; i++){
                List<Row> FilasDelCluster = clusters.get(i+1);
                if (!FilasDelCluster.isEmpty()){

                    representantes.set(i, calcCentroide(FilasDelCluster));
                }
            }

        }

    }

    @Override
    public Integer estimate(List<Double> dato){ //sacamos el indice del representante que encontramos mas cerca a dicho punto
        int IndiceRepreCercano = -1;
        double minDist = Double.MAX_VALUE;

        for(int i = 0; i < representantes.size(); i++){
            double dist = calcDist(dato, representantes.get(i).getData());
            if (dist < minDist){
                minDist = dist;
                IndiceRepreCercano=i;
            }
        }
        return IndiceRepreCercano +1;
    }


    //calculamos el centroide (promedio por coordenada) del grupo
    private Row calcCentroide(List<Row> FilasMismoCluster){
        int parametros = FilasMismoCluster.get(0).getData().size();
        double[] sumas = new double[parametros];
        for (Row fila : FilasMismoCluster){
            for (int i= 0; i<parametros; i++){
                sumas[i] += fila.getData().get(i);
            }
        }
        List<Double> CentroCluster = new ArrayList<>();
        for (double param : sumas){
            CentroCluster.add(param/FilasMismoCluster.size());
        }
        return new Row(CentroCluster);
    }


    private double calcDist(List<Double> a, List<Double> b){ //calculo basico de la distancia (tambien usado en el knn)
        double suma=0;
        for (int i=0; i< a.size(); i++){
            double diff = a.get(i) - b.get(i);
            suma += diff*diff;
        }
        return Math.sqrt(suma);
    }
}
