package es.uji.al449178.csv;

import es.uji.al449178.table.Row;
import es.uji.al449178.table.RowWithLabel;
import es.uji.al449178.table.Table;
import es.uji.al449178.table.TableWithLabels;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV {

    public Table readTable(String fileName) throws IOException {
        String ref;
        BufferedReader br;
        try{
            ref=getClass().getClassLoader().getResource(fileName).toURI().getPath();
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }

        try{
            br = new BufferedReader(new FileReader(ref));
        } catch (FileNotFoundException e ){
            throw new RuntimeException(e);
        }

        String line = br.readLine();
        List<String> headers = Arrays.asList(line.split(","));
        Table table = new Table();
        table.setHeaders(headers);

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            List<Double> data = new ArrayList<>();
            for (String value : values) {
                data.add(Double.valueOf(value));
            }
            table.addRow(new Row(data));
        }
        br.close();
        return table;
    }

    public TableWithLabels readTableWithLabels(String fileName) throws IOException {
        String ref;
        BufferedReader br;
        try{
            ref=getClass().getClassLoader().getResource(fileName).toURI().getPath();
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }

        try{
            br = new BufferedReader(new FileReader(ref));
        } catch (FileNotFoundException e ){
            throw new RuntimeException(e);
        }

        String line = br.readLine();
        List<String> headers = new ArrayList<>(Arrays.asList(line.split(",")));
        // separar header extra (label)
        String HeaderExtra= headers.get(headers.size()-1);
        headers.remove(headers.size() - 1);
        TableWithLabels table = new TableWithLabels();
        table.setHeaders(headers);
        table.setHeaderExtra(HeaderExtra);
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < values.length - 1; i++) {
                data.add(Double.valueOf(values[i]));
            }
            String label = values[values.length - 1];
            table.addRow(new RowWithLabel(data, label));
        }

        br.close();
        return table;
    }

    public List<String> readNames(String fileName) throws IOException {
        String ref;
        BufferedReader br;
        try{
            ref=getClass().getClassLoader().getResource(fileName).toURI().getPath();
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }

        try{
            br = new BufferedReader(new FileReader(ref));
        } catch (FileNotFoundException e ){
            throw new RuntimeException(e);
        }

        br.readLine();
        List<String> names = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            names.add(line.trim());
        }
        br.close();
        return names;
    }
}
