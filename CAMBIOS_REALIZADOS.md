# cambios realizados

comparado entre `Practica1-original` y `Practica1`

## archivos creados

- `Practica1/src/main/java/es/uji/al449178/recsys/LikedItemNotFoundException.java` - se añadió la excepción para items no encontrados.
- `Practica1/src/main/java/es/uji/al449178/recsys/RecSys.java` - se añadió la clase genérica del sistema de recomendación.
- `Practica1/src/main/java/es/uji/al449178/recsys/SongRecSys.java` - se añadió la demo por consola para canciones.
- `Practica1/src/test/java/es/uji/al449178/kmeans/KMeansTest.java` - se añadieron tests de kmeans.
- `Practica1/src/test/java/es/uji/al449178/recsys/RecSysTest.java` - se añadieron tests de recsys.
- `Practica1/src/main/resources/recommender/songs_train.csv` - se añadieron datos de entrenamiento con etiquetas.
- `Practica1/src/main/resources/recommender/songs_train_withoutnames.csv` - se añadieron datos de entrenamiento sin nombres.
- `Practica1/src/main/resources/recommender/songs_train_names.csv` - se añadieron nombres de canciones de entrenamiento.
- `Practica1/src/main/resources/recommender/songs_test.csv` - se añadieron datos de test con etiquetas.
- `Practica1/src/main/resources/recommender/songs_test_withoutnames.csv` - se añadieron datos de test sin nombres.
- `Practica1/src/main/resources/recommender/songs_test_names.csv` - se añadieron nombres de canciones de test.

## archivos modificados

- `Practica1/pom.xml` - se ajustó la configuración de maven y junit.
- `Practica1/src/main/java/es/uji/al449178/csv/CSV.java` - se refactorizó la lectura de csv desde resources.
- `Practica1/src/main/java/es/uji/al449178/kmeans/InvalidClusterNumberException.java` - se cambió el mensaje de la excepción.
- `Practica1/src/main/java/es/uji/al449178/kmeans/KMeans.java` - se refactorizó el algoritmo y sus auxiliares.
- `Practica1/src/main/java/es/uji/al449178/knn/Distance.java` - se añadió `euclideanDistance`.
- `Practica1/src/main/java/es/uji/al449178/knn/KNN.java` - se adaptó al nuevo método de distancia.
- `Practica1/src/main/java/es/uji/al449178/table/Row.java` - limpieza de comentarios.
- `Practica1/src/main/java/es/uji/al449178/table/RowWithLabel.java` - limpieza de comentarios.
- `Practica1/src/main/java/es/uji/al449178/table/TableWithLabels.java` - ajuste de nombres y formato.
- `Practica1/src/test/java/es/uji/al449178/TestSuite.java` - se ampliaron los paquetes del suite para incluir kmeans y recsys.
- `Practica1/src/test/java/es/uji/al449178/csv/CSVTest.java` - se limpiaron TODOs y comentarios de plantilla.
- `Practica1/src/test/java/es/uji/al449178/knn/KNNTest.java` - se limpiaron TODOs y comentarios de plantilla.
- `Practica1/src/test/java/es/uji/al449178/table/TableTest.java` - se limpiaron TODOs y comentarios de plantilla.
- `Practica1/src/test/java/es/uji/al449178/table/TableWithLabelsTest.java` - se limpiaron TODOs y comentarios de plantilla.

## otros cambios en el repo

- `.gitignore` - se añadió para ignorar `Practica1/target/` y metadatos de macos.
- se eliminaron los archivos de metadatos `._KMeansTest.java` y `._RecSysTest.java`.

## resumen rápido

- se añadieron recsys, songrecsys y su excepción.
- se añadieron los tests que faltaban de kmeans y recsys.
- se añadieron los csv de `resources/recommender` para que songrecsys funcione.
- se refactorizó parte del código y se limpiaron restos de plantilla en los tests.