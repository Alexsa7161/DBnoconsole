package rest.controller.classes;
import rest.controller.interfaces.IDataStorage;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class DataStorage implements IDataStorage {
    public String directoryPath;

    public DataStorage() {
        this.directoryPath = ".\\src\\Classes\\tables";
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }
    @Override
    public String[] readTable(Table table) throws IOException {
        List<String> list = Files.readAllLines(Paths.get(".\\src\\Classes\\tables\\" + table.name + ".txt"));
        String[] mas = new String[list.size()];
        int i = 0;
        for (String s:list)
        {
            mas[i] = s;
            i++;
        }
        Files.write(Paths.get(".\\src\\Classes\\tables\\" + table.name + ".txt"), List.of(), StandardOpenOption.TRUNCATE_EXISTING);
        return mas;
    }
    public void writeTable(Table table, String line) throws IOException {
        Files.write(Paths.get(".\\src\\Classes\\tables\\"+table.name+".txt"), (line + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    public BufferedReader readIndex(Index index) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(".\\src\\Classes\\tables\\"+index.name+".txt"));
        return reader;
    }
    public BufferedWriter writeIndex(Index index) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\Classes\\tables\\"+index.name+".txt"));
        return writer;
    }
    public void deleteTable(String tableName) throws IOException {
        Path path = Paths.get(directoryPath+"\\"+tableName+".txt");
        Files.delete(path);
    }
    public void createTable(Table table) throws IOException {
        Path path = Paths.get(directoryPath+"\\"+table.name+".txt");
        Files.createFile(path);
        String str = "";
        for(Column c:table.columns) {
            str = str +  c.getName() + " " + c.getDataType() + " ";
        }
        writeTable(table,str);
        writeTable(table," ");
    }
    public void createIndex(Index index) throws IOException {
        Path path = Paths.get(directoryPath+"\\"+index.name+".txt");
        Files.createFile(path);
        BufferedWriter bw = writeIndex(index);
        for(Column c:index.list) {
            bw.write(c.getName() + " " + c.getDataType() + " ");
        }
        bw.flush();
        bw.close();
    }
    public void deleteIndex(String indexName) throws IOException {
        Path path = Paths.get(directoryPath+"\\"+indexName+".txt");
        Files.delete(path);
    }
}