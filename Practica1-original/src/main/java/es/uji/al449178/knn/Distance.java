package es.uji.al449178.knn;

import java.util.List;

//aquí hay que hacer el cálculo de la posición
public class Distance {
    public static double medir_dist(List<Double> a, List<Double> b){
        double sum = 0;
        for (int i = 0; i < a.size(); i++) {
            double diff = a.get(i) - b.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
