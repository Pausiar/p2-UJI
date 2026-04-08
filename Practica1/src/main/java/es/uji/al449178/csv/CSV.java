package es.uji.al449178.csv;

import es.uji.al449178.table.Row;
import es.uji.al449178.table.RowWithLabel;
import es.uji.al449178.table.Table;
import es.uji.al449178.table.TableWithLabels;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV {

    public Table readTable(String fileName) throws IOException {
        try (BufferedReader reader = openReader(fileName)) {
            Table table = new Table();
            table.setHeaders(parseHeaders(readRequiredLine(reader, fileName)));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                table.addRow(new Row(parseNumericValues(values, values.length)));
            }
            return table;
        }
    }

    public TableWithLabels readTableWithLabels(String fileName) throws IOException {
        try (BufferedReader reader = openReader(fileName)) {
            List<String> headers = new ArrayList<>(parseHeaders(readRequiredLine(reader, fileName)));
            String extraHeader = headers.remove(headers.size() - 1);

            TableWithLabels table = new TableWithLabels();
            table.setHeaders(headers);
            table.setHeaderExtra(extraHeader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                List<Double> data = parseNumericValues(values, values.length - 1);
                String label = values[values.length - 1];
                table.addRow(new RowWithLabel(data, label));
            }
            return table;
        }
    }

    public List<String> readNames(String fileName) throws IOException {
        try (BufferedReader reader = openReader(fileName)) {
            readRequiredLine(reader, fileName);

            List<String> names = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                names.add(line.trim());
            }
            return names;
        }
    }

    private BufferedReader openReader(String fileName) throws IOException {
        InputStream resource = getClass().getClassLoader().getResourceAsStream(fileName);
        if (resource == null) {
            throw new FileNotFoundException("resource not found: " + fileName);
        }
        return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
    }

    private String readRequiredLine(BufferedReader reader, String fileName) throws IOException {
        String line = reader.readLine();
        if (line == null) {
            throw new IOException("resource is empty: " + fileName);
        }
        return line;
    }

    private List<String> parseHeaders(String line) {
        return Arrays.asList(line.split(","));
    }

    private List<Double> parseNumericValues(String[] values, int size) {
        List<Double> data = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            data.add(Double.valueOf(values[index]));
        }
        return data;
    }
}
