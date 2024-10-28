package rest.controller.interfaces;

import rest.controller.classes.Column;
import rest.controller.classes.Index;
import rest.controller.classes.Instruction;
import rest.controller.classes.Table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ITable {
    List<Column> columns = List.of();
    List<Index> indexes = List.of();
    String name = "";

    void setName(String name);

    String getName();


    void removeColumn(String column) throws IOException;

    public void addIndex(String indexName, String text, Table t) throws IOException;

    public void removeIndex(String indexName) throws IOException;

    public void Select(String column, String condition,List<String> mas_col) throws IOException;

    public void SelectAll(String column, String condition) throws IOException;

    public void Insert(String[] text) throws IOException;

    public void Delete(String column, String condition) throws IOException;

    static void executeInstruction(Instruction instruction) throws IOException {

    }
}