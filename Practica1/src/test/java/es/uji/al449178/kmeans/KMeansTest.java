package es.uji.al449178.kmeans;

import es.uji.al449178.table.Row;
import es.uji.al449178.table.Table;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KMeansTest {

    private KMeans kmeans;
    private Table table;

    @BeforeEach
    void setUp() throws InvalidClusterNumberException {
        table = new Table();
        table.setHeaders(List.of("x", "y"));

        // cluster 1: alrededor de (1, 1)
        table.addRow(new Row(List.of(1.0, 1.0)));
        table.addRow(new Row(List.of(1.1, 0.9)));
        table.addRow(new Row(List.of(0.9, 1.1)));

        // cluster 2: alrededor de (10, 10)
        table.addRow(new Row(List.of(10.0, 10.0)));
        table.addRow(new Row(List.of(10.1, 9.9)));
        table.addRow(new Row(List.of(9.9, 10.1)));

        // cluster 3: alrededor de (20, 20)
        table.addRow(new Row(List.of(20.0, 20.0)));
        table.addRow(new Row(List.of(20.1, 19.9)));
        table.addRow(new Row(List.of(19.9, 20.1)));

        kmeans = new KMeans(3, 100, 42);
        kmeans.train(table);
    }

    @AfterEach
    void tearDown() {
        kmeans = null;
        table = null;
    }

    @Test
    @DisplayName("KMeans - estimate assigns same cluster to nearby points")
    void estimateSameCluster() {
        Integer c1a = kmeans.estimate(List.of(1.0, 1.0));
        Integer c1b = kmeans.estimate(List.of(0.9, 1.1));
        assertEquals(c1a, c1b);

        Integer c2a = kmeans.estimate(List.of(10.0, 10.0));
        Integer c2b = kmeans.estimate(List.of(10.1, 9.9));
        assertEquals(c2a, c2b);

        Integer c3a = kmeans.estimate(List.of(20.0, 20.0));
        Integer c3b = kmeans.estimate(List.of(20.1, 19.9));
        assertEquals(c3a, c3b);
    }

    @Test
    @DisplayName("KMeans - estimate assigns different clusters to distant points")
    void estimateDifferentClusters() {
        Integer c1 = kmeans.estimate(List.of(1.0, 1.0));
        Integer c2 = kmeans.estimate(List.of(10.0, 10.0));
        Integer c3 = kmeans.estimate(List.of(20.0, 20.0));

        assertNotEquals(c1, c2);
        assertNotEquals(c2, c3);
        assertNotEquals(c1, c3);
    }

    @Test
    @DisplayName("KMeans - estimate returns valid cluster number")
    void estimateValidClusterNumber() {
        Integer cluster = kmeans.estimate(List.of(5.0, 5.0));
        assertTrue(cluster >= 1 && cluster <= 3);
    }

    @Test
    @DisplayName("KMeans - estimate new point near known cluster")
    void estimateNewPoint() {
        Integer clusterOriginal = kmeans.estimate(List.of(1.0, 1.0));
        Integer clusterNew = kmeans.estimate(List.of(1.2, 0.8));
        assertEquals(clusterOriginal, clusterNew);
    }

    @Test
    @DisplayName("KMeans - InvalidClusterNumberException when clusters > data")
    void invalidClusterNumber() {
        KMeans badKmeans = new KMeans(100, 10, 42);
        assertThrows(InvalidClusterNumberException.class, () -> badKmeans.train(table));
    }

    @Test
    @DisplayName("KMeans - InvalidClusterNumberException has correct values")
    void invalidClusterNumberValues() {
        KMeans badKmeans = new KMeans(100, 10, 42);
        InvalidClusterNumberException ex = assertThrows(
                InvalidClusterNumberException.class,
                () -> badKmeans.train(table)
        );
        assertEquals(100, ex.getNumberOfClusters());
        assertEquals(9, ex.getNumberOfDatos());
    }
}
