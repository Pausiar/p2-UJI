package es.uji.al449178;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.al449178.knn", "es.uji.al449178.csv", "es.uji.al449178.table", "es.uji.al449178.kmeans", "es.uji.al449178.recsys"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
