package es.uji.al449178.knn;

import java.util.List;

public class Distance {
    public static double euclideanDistance(List<Double> a, List<Double> b){
        double sum = 0;
        for (int i = 0; i < a.size(); i++) {
            double diff = a.get(i) - b.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    public static double medir_dist(List<Double> a, List<Double> b){
        return euclideanDistance(a, b);
    }
}
