package rest.controller.interfaces;

import rest.controller.classes.Table;

import java.io.*;

public interface IDataStorage {

    String[] readTable(Table table) throws IOException;

    public void writeTable(Table table, String line) throws IOException;
}