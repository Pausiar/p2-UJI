package es.uji.al449178.recsys;

import es.uji.al449178.csv.CSV;
import es.uji.al449178.kmeans.KMeans;
import es.uji.al449178.knn.KNN;
import es.uji.al449178.table.Table;
import es.uji.al449178.table.TableWithLabels;

import java.util.List;
import java.util.Scanner;

public class SongRecSys {
    private static final int NUM_CLUSTERS = 5;
    private static final int NUM_ITERATIONS = 100;
    private static final long SEED = 42;
    private static final int NUM_RECOMMENDATIONS = 5;

    private static final String TRAIN_DATA = "recommender/songs_train_withoutnames.csv";
    private static final String TRAIN_DATA_LABELS = "recommender/songs_train.csv";
    private static final String TEST_DATA = "recommender/songs_test_withoutnames.csv";
    private static final String TEST_NAMES = "recommender/songs_test_names.csv";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            CSV csv = new CSV();
            RecSys recSys = crearSistema(csv, scanner);
            inicializarSistema(csv, recSys);
            mostrarRecomendaciones(scanner, recSys);
        } catch (LikedItemNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private static RecSys crearSistema(CSV csv, Scanner scanner) throws Exception {
        System.out.println("Selecciona algoritmo: 1) KMeans  2) KNN");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        if (opcion == 1) {
            return crearConKMeans(csv);
        }
        return crearConKNN(csv);
    }

    private static void inicializarSistema(CSV csv, RecSys recSys) throws Exception {
        Table testData = csv.readTable(TEST_DATA);
        List<String> testNames = csv.readNames(TEST_NAMES);
        recSys.initialise(testData, testNames);
    }

    private static void mostrarRecomendaciones(Scanner scanner, RecSys recSys) throws LikedItemNotFoundException {
        System.out.println("Introduce el nombre de una cancion que te guste:");
        String songName = scanner.nextLine();
        List<String> recommendations = recSys.recommend(songName, NUM_RECOMMENDATIONS);
        System.out.println("Recomendaciones:");
        for (String recommendation : recommendations) {
            System.out.println("  - " + recommendation);
        }
    }

    @SuppressWarnings("unchecked")
    private static RecSys crearConKMeans(CSV csv) throws Exception {
        Table trainData = csv.readTable(TRAIN_DATA);
        KMeans kmeans = new KMeans(NUM_CLUSTERS, NUM_ITERATIONS, SEED);
        RecSys recSys = new RecSys(kmeans);
        recSys.train(trainData);
        return recSys;
    }

    @SuppressWarnings("unchecked")
    private static RecSys crearConKNN(CSV csv) throws Exception {
        TableWithLabels trainData = csv.readTableWithLabels(TRAIN_DATA_LABELS);
        KNN knn = new KNN();
        RecSys recSys = new RecSys(knn);
        recSys.train(trainData);
        return recSys;
    }
}
