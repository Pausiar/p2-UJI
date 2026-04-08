package es.uji.al449178.recsys;

import es.uji.al449178.kmeans.KMeans;
import es.uji.al449178.kmeans.InvalidClusterNumberException;
import es.uji.al449178.table.Row;
import es.uji.al449178.table.Table;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecSysTest {

    private RecSys recSys;
    private Table trainData;
    private Table testData;
    private List<String> testItemNames;

    @BeforeEach
    void setUp() throws InvalidClusterNumberException, IOException {
        trainData = new Table();
        trainData.setHeaders(List.of("feature1", "feature2"));

        // cluster a: valores bajos
        trainData.addRow(new Row(List.of(1.0, 1.0)));
        trainData.addRow(new Row(List.of(1.5, 1.5)));
        trainData.addRow(new Row(List.of(2.0, 2.0)));

        // cluster b: valores altos
        trainData.addRow(new Row(List.of(10.0, 10.0)));
        trainData.addRow(new Row(List.of(10.5, 10.5)));
        trainData.addRow(new Row(List.of(11.0, 11.0)));

        testData = new Table();
        testData.setHeaders(List.of("feature1", "feature2"));

        testData.addRow(new Row(List.of(1.2, 1.2)));
        testData.addRow(new Row(List.of(1.8, 1.8)));
        testData.addRow(new Row(List.of(10.2, 10.2)));
        testData.addRow(new Row(List.of(10.8, 10.8)));
        testData.addRow(new Row(List.of(1.0, 1.0)));
        testData.addRow(new Row(List.of(11.0, 11.0)));

        testItemNames = List.of("SongA1", "SongA2", "SongB1", "SongB2", "SongA3", "SongB3");

        KMeans kmeans = new KMeans(2, 100, 42);
        recSys = new RecSys(kmeans);
        recSys.train(trainData);
        recSys.initialise(testData, testItemNames);
    }

    @AfterEach
    void tearDown() {
        recSys = null;
        trainData = null;
        testData = null;
        testItemNames = null;
    }

    @Test
    @DisplayName("RecSys - recommend returns items from the same cluster")
    void recommendSameCluster() throws LikedItemNotFoundException {
        List<String> recs = recSys.recommend("SongA1", 2);
        assertNotNull(recs);
        assertFalse(recs.isEmpty());
        assertFalse(recs.contains("SongA1"));
        assertTrue(recs.contains("SongA2") || recs.contains("SongA3"));
    }

    @Test
    @DisplayName("RecSys - recommend from cluster B")
    void recommendClusterB() throws LikedItemNotFoundException {
        List<String> recs = recSys.recommend("SongB1", 2);
        assertNotNull(recs);
        assertFalse(recs.isEmpty());
        assertFalse(recs.contains("SongB1"));
        assertTrue(recs.contains("SongB2") || recs.contains("SongB3"));
    }

    @Test
    @DisplayName("RecSys - recommend does not include liked item")
    void recommendExcludesLikedItem() throws LikedItemNotFoundException {
        List<String> recs = recSys.recommend("SongA1", 5);
        assertFalse(recs.contains("SongA1"));
    }

    @Test
    @DisplayName("RecSys - recommend respects max number of recommendations")
    void recommendRespectsMax() throws LikedItemNotFoundException {
        List<String> recs = recSys.recommend("SongA1", 1);
        assertTrue(recs.size() <= 1);
    }

    @Test
    @DisplayName("RecSys - recommend throws LikedItemNotFoundException for unknown item")
    void recommendUnknownItem() {
        assertThrows(LikedItemNotFoundException.class, () -> recSys.recommend("UnknownSong", 3));
    }

    @Test
    @DisplayName("RecSys - LikedItemNotFoundException has correct item name")
    void likedItemNotFoundExceptionValues() {
        LikedItemNotFoundException ex = assertThrows(
                LikedItemNotFoundException.class,
                () -> recSys.recommend("UnknownSong", 3)
        );
        assertEquals("UnknownSong", ex.getItemName());
    }
}
