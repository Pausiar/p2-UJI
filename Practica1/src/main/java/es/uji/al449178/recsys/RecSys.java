package es.uji.al449178.recsys;

import es.uji.al449178.kmeans.Algorithm;
import es.uji.al449178.kmeans.InvalidClusterNumberException;
import es.uji.al449178.table.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class RecSys {
    private final Algorithm algorithm;
    private List<String> itemNames;
    private List<Integer> estimaciones;

    public RecSys(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    //delegamos el entrenamiento al algoritmo (KNN o KMeans)
    public void train(Table trainData) throws InvalidClusterNumberException, IOException {
        algorithm.train(trainData);
    }

    //estimamos el grupo/clase de cada muestra y almacenamos los resultados
    public void initialise(Table testData, List<String> testItemNames) {
        this.itemNames = testItemNames;
        this.estimaciones = new ArrayList<>();
        for (int i = 0; i < testData.getRowCount(); i++) {
            Integer resultado = (Integer) algorithm.estimate(testData.getRowAt(i).getData());
            estimaciones.add(resultado);
        }
    }

    //buscamos recomendaciones del mismo grupo/clase que el item gustado
    public List<String> recommend(String nameLikedItem, int numRecommendations) throws LikedItemNotFoundException {
        int itemIndex = itemNames.indexOf(nameLikedItem);
        if (itemIndex == -1) {
            throw new LikedItemNotFoundException(nameLikedItem);
        }
        Integer claseGustada = estimaciones.get(itemIndex);
        List<String> recomendaciones = new ArrayList<>();
        for (int i = 0; i < estimaciones.size() && recomendaciones.size() < numRecommendations; i++) {
            if (i != itemIndex && estimaciones.get(i).equals(claseGustada)) {
                recomendaciones.add(itemNames.get(i));
            }
        }
        return recomendaciones;
    }
}
