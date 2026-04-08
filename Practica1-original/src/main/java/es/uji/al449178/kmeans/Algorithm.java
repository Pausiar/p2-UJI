package es.uji.al449178.kmeans;

import es.uji.al449178.table.Table;

import java.io.IOException;

public interface Algorithm<T extends Table, W, R> {
    void train(T datos) throws InvalidClusterNumberException, IOException;
    R estimate(W dato);
}
