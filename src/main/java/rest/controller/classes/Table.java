package rest.controller.classes;

import rest.controller.interfaces.ITable;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Table implements ITable {
    List<Column> columns = List.of();
    List<Index> indexes = List.of();
    String name = "";

    public Table(String name) {
        this.name = name;
    }
    public static void executeInstruction(Instruction instruction) throws IOException {
        Table table = new Table(instruction.tableName);
        table.columns = new ArrayList<>();
        DataStorage ds = new DataStorage();
        try {
            String[] br = ds.readTable(new Table(instruction.tableName));
            for (int i = 0; i < br.length; i++) {
                if (i == 0) {
                    String[] massiv = br[i].split(" ");
                    for (int j = 0; j < massiv.length; j = j + 2) {
                        Column c = new Column(massiv[j], massiv[j + 1]);
                        table.columns.add(new Column(massiv[j], massiv[j + 1]));
                    }
                    ds.writeTable(table,br[i]);
                } else {
                    ds.writeTable(table,br[i]);
                }
            }
        }
        catch (Exception e)
        {
            table.columns = new ArrayList<>();
        }
        switch (instruction.operationType)
        {
            case  ("DROP TABLE"):
                table.dropTable(instruction.tableName);
                break;
            case  ("CREATE TABLE"):
                table.createTable(instruction.tableName, instruction.text);
                break;
            case  ("RENAME TABLE"): {
                String[] mas = instruction.text.split(" ");
                for(int i = 0;i < mas.length;i++)
                {
                    if (mas[i].equalsIgnoreCase("to"))
                    {
                        table.renameTable(instruction.tableName, mas[i+1]);
                        break;
                    }
                }
                break;
            }
            case  ("RENAME COLUMN"): {
                String[] mas = instruction.text.split(" ");
                String a = "";
                for(int i = 0;i < mas.length;i++)
                {
                    if (mas[i].equalsIgnoreCase("column"))
                    {
                        a = mas[i+1];
                        break;
                    }
                }
                for(int i = 0;i < mas.length;i++)
                {
                    if (mas[i].equalsIgnoreCase("to"))
                    {
                        table.renameColumn(a, mas[i+1]);
                        break;
                    }
                }
                break;
            }
            case  ("DELETE COLUMN"): {
                String[] mas = instruction.text.split(" ");
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("column")) {
                        table.removeColumn(mas[i + 1]);
                        break;
                    }
                }
                break;
            }
            case  ("ADD COLUMN"): {
                String[] mas = instruction.text.split(" ");
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("column")) {
                        table.addColumn(mas[i + 1],mas[i + 2]);
                        break;
                    }
                }
                break;
            }
            case  ("ADD INDEX"): {
                String[] mas = instruction.text.split(" ");
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("index")) {
                        table.addIndex(mas[i+1],instruction.text, table);
                        break;
                    }
                }
                break;
            }
            case  ("DELETE INDEX"): {
                String[] mas = instruction.text.split(" ");
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("index")) {
                        table.removeIndex(mas[i+1]);
                        break;
                    }
                }
                break;
            }
            case  ("RENAME INDEX"): {
                String old_name = "";
                String[] mas = instruction.text.split(" ");
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("index")) {
                        old_name = mas[i+1];
                        break;
                    }
                }
                for(int i = 0;i < mas.length;i++)
                {
                    if (mas[i].equalsIgnoreCase("to"))
                    {
                        table.renameIndex(old_name, mas[i+1]);
                        break;
                    }
                }
                break;
            }
            case  ("INSERT"): {
                String old_name = "";
                String[] mas = instruction.text
                        .substring(instruction.text.indexOf("(")+1,instruction.text.indexOf(")"))
                        .replaceAll("\\n", "").split(",");
                table.Insert(mas);
                break;
            }
            case  ("DELETE"): {
                String[] mas = instruction.text.split(" ");
                String column = "";
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("where")) {
                        column = mas[i+1];
                        break;
                    }
                }
                String condition = "";
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("=")) {
                        condition = mas[i+1];
                        break;
                    }
                }
                table.Delete(column, condition);
                break;
            }
            case  ("SELECT ALL"): {
                String[] mas = instruction.text.split(" ");
                String column = "";
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("where")) {
                        column = mas[i+1];
                        break;
                    }
                }
                String condition = "";
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("=")) {
                        condition = mas[i+1];
                        break;
                    }
                }
                table.SelectAll(column, condition);
                break;
            }
            case  ("SELECT"): {
                String[] mas = instruction.text.split(" ");
                String column = "";
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("where")) {
                        column = mas[i+1];
                        break;
                    }
                }
                String condition = "";
                for (int i = 0; i < mas.length; i++) {
                    if (mas[i].equalsIgnoreCase("=")) {
                        condition = mas[i+1];
                        break;
                    }
                }
                String[] arr = instruction.text.split(" ");
                List<String> res = new ArrayList<>(List.of());
                for (String s:arr)
                {
                    if (s.equalsIgnoreCase("select"))
                    {
                        continue;
                    }
                    else if (s.equalsIgnoreCase("from")) {
                        break;
                    }
                    else
                    {
                        String[] massiv = s.split(",");
                        res.addAll(Arrays.asList(massiv));
                    }
                }
                table.Select(column,condition,res);
                break;
            }
        }
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void addColumn(String column,String type) throws IOException {
        DataStorage ds = new DataStorage();
        String[] br = ds.readTable(this);
        for (int i = 0; i < br.length;i++)
        {
            if (i == 0) {
                br[i] = br[i] + column +" " + type+" ";
                ds.writeTable(this,br[i]);
            } else {
                ds.writeTable(this,br[i]);
            }
        }
    }

    public void removeColumn(String column) throws IOException {
        DataStorage ds = new DataStorage();
        String[] br = ds.readTable(new Table(this.name));
        for (int i = 0; i < br.length;i++) {
            if (i == 0) {
                String[] mas = br[i].split(" ");
                for (int j = 0; i < mas.length; i++) {
                    if (Objects.equals(mas[j], column)) {
                        mas[j] = "";
                        mas[j + 1] = "";
                        break;
                    }
                }
                br[i] = "";
                for (int j = 0; j < mas.length; j++) {
                    br[i] = br[i] + mas[j] + " ";
                }
                br[i] = br[i].replace("  ", "");
                ds.writeTable(this,br[i]);
            } else {
                ds.writeTable(this,br[i]);
            }
        }
    }

    public void addIndex(String indexName, String text, Table t) throws IOException {
        DataStorage ds = new DataStorage();
        Index x = new Index();
        x.name = t.name + "_" + "index_" + indexName.split("\\(")[0];
        x.index = new BTree(5);
        x.list = new ArrayList<>();
        String[] mas = text.substring(text.indexOf("(")+1,text.indexOf(")")).replaceAll("\\n", "").split(",");
        for (String s:mas)
        {
            for (Column c:t.columns)
            {
                if (Objects.equals(s, c.getName()))
                {
                    x.list.add(c);
                }
            }
        }
        ds.createIndex(x);
        String[] br = ds.readTable(new Table(this.name));
        for (int i = 0; i < br.length;i++)
        {
            if (i == 1) {
                br[i] = (br[i] == null ? "" : br[i]) + indexName +" ";
                ds.writeTable(this,br[i]);
            } else {
                ds.writeTable(this,br[i]);
            }
        }
    }

    public void removeIndex(String indexName) throws IOException {
        DataStorage ds = new DataStorage();
        ds.deleteIndex(this.name + "_" + "index_"+indexName);
        String[] br = ds.readTable(new Table(this.name));
        for (int i = 0; i < br.length;i++)
        {
            if (i == 1) {
                String[] mas = br[i].split(" ");
                for (int j = 0; j < mas.length; j++)
                {
                    if (mas[j].startsWith(indexName+"("))
                    {
                        mas[j] = "";
                    }
                }
                String s = "";
                for (int j = 0; j < mas.length; j++)
                {
                    s = s + mas[j];
                }
                ds.writeTable(this,s);
            } else {
                ds.writeTable(this,br[i]);
            }
        }
    }

    public void createTable(String tableName, String text) throws IOException {
        String[] mas = text.substring(text.indexOf("(")+1,text.indexOf(")")).replaceAll("\\n", "").split(",");
        List<Column> list = new ArrayList<>(List.of());
        for (String s:mas)
        {
            Column c = new Column(s.trim().split(" ")[0],s.trim().split(" ")[1]);
            list.add(c);
        }
        DataStorage ds = new DataStorage();
        Table t = new Table(tableName);
        t.columns = list;
        ds.createTable(t);
    }
    public void dropTable(String tableName) throws IOException {
        DataStorage ds = new DataStorage();
        ds.deleteTable(tableName);
    }
    public void renameTable(String old_name, String new_name)
    {
        DataStorage ds = new DataStorage();
        File file = new File(ds.directoryPath+"\\"+old_name+".txt");
        File rename = new File(ds.directoryPath+"\\"+new_name+".txt");
        boolean flag = file.renameTo(rename);
    }
    public void renameColumn(String old_name, String new_name) throws IOException {
        DataStorage ds = new DataStorage();
        String[] br = ds.readTable(new Table(this.name));
        for (int i = 0; i < br.length;i++)
        {
            if (i == 0) {
                String s_rep = br[i].replace(old_name,new_name);
                ds.writeTable(this,s_rep);
            } else {
                ds.writeTable(this,br[i]);
            }
        }
    }
    public void renameIndex(String old_name, String new_name) throws IOException {
        DataStorage ds = new DataStorage();
        String[] br = ds.readTable(new Table(this.name));
        for (int i = 0; i < br.length;i++)
        {
            if (i == 1) {
                String s_rep = br[i].replace(old_name,new_name);
                ds.writeTable(this,s_rep);
            } else {
                ds.writeTable(this,br[i]);
            }
        }
        File file = new File(ds.directoryPath+"\\"+this.name + "_" +"index_"+old_name+".txt");
        File rename = new File(ds.directoryPath+"\\"+this.name + "_" +"index_"+new_name+".txt");
        boolean flag = file.renameTo(rename);
    }
    public void SelectAll(String column, String condition) throws IOException {
        DataStorage ds = new DataStorage();
        String[] br = ds.readTable(new Table(this.name));
        int col_num = 0;
        List<String[]> result = new ArrayList<>();
        if (Objects.equals(column, ""))
        {
            for (int i = 0; i < br.length;i++)
            {
                if (Objects.equals(br[i], ""))
                {
                    continue;
                }
                if (i == 0) {
                    String[] mas = br[i].split(" ");
                    for (int j = 0; j < mas.length; j = j+2)
                    {
                        System.out.print(mas[j] + " ");
                    }
                    System.out.println("");
                    ds.writeTable(this,br[i]);
                }
                else if (i == 1) {
                    ds.writeTable(this,br[i]);
                }
                else {
                    ds.writeTable(this,br[i]);
                    String[] mas = br[i].split(" ");
                    result.add(mas);
                }
            }
            for (String[] mas:result)
            {
                System.out.println(Arrays.toString(mas));
            }
        }
        else {
            for (int i = 0; i < br.length;i++)
            {
                if (Objects.equals(br[i], ""))
                {
                    continue;
                }
                if (i == 0) {
                    String[] mas = br[i].split(" ");
                    for (int j = 0; j < mas.length; j = j+2)
                    {
                        if (Objects.equals(mas[j], column))
                        {
                            col_num = j/2;
                        }
                        System.out.print(mas[j] + " ");
                    }
                    System.out.println("");
                    ds.writeTable(this,br[i]);
                }
                else if (i == 1) {
                    ds.writeTable(this,br[i]);
                }
                else {
                    ds.writeTable(this,br[i]);
                    String[] mas = br[i].split(" ");
                    if (Objects.equals(mas[col_num], condition))
                    {
                        result.add(mas);
                    }
                }
            }
            for (String[] mas:result)
            {
                System.out.println(Arrays.toString(mas));
            }
        }
    }

    public void Select(String column, String condition,List<String> mas_col) throws IOException {
        DataStorage ds = new DataStorage();
        String[] br = ds.readTable(new Table(this.name));
        int col_num = 0;
        List<Integer> mas_col_num = new ArrayList<>(List.of());
        List<List<String>> result = new ArrayList<>();
        if (Objects.equals(column, ""))
        {
            for (int i = 0; i < br.length;i++)
            {
                if (Objects.equals(br[i], ""))
                {
                    continue;
                }
                if (i == 0) {
                    String[] mas = br[i].split(" ");
                    for (int j = 0; j < mas.length; j = j+2)
                    {
                        if (mas_col.contains(mas[j]))
                        {
                            mas_col_num.add(j/2);
                            System.out.print(mas[j] + " ");
                        }
                    }
                    System.out.println("");
                    ds.writeTable(this,br[i]);
                }
                else if (i == 1) {
                    ds.writeTable(this,br[i]);
                }
                else {
                    ds.writeTable(this,br[i]);
                    String[] mas = br[i].split(" ");
                    List<String> list = new ArrayList<>(List.of());
                    for (int j = 0; j < mas.length; j++)
                    {
                        if (mas_col_num.contains(j))
                        {
                            list.add(mas[j]);
                        }
                    }
                    result.add(list);
                }
            }
            for (List<String> mas:result)
            {
                for (String s:mas)
                {
                    System.out.print(s+" ");
                }
                System.out.println("");
            }
        }
        else {
            for (int i = 0; i < br.length;i++)
            {
                if (Objects.equals(br[i], ""))
                {
                    continue;
                }
                if (i == 0) {
                    String[] mas = br[i].split(" ");
                    for (int j = 0; j < mas.length; j = j+2)
                    {
                        if (mas_col.contains(mas[j]))
                        {
                            mas_col_num.add(j/2);
                            System.out.print(mas[j] + " ");
                        }
                    }
                    System.out.println("");
                    ds.writeTable(this,br[i]);
                    for (int j = 0; j < mas.length; j = j+2)
                    {
                        if (Objects.equals(mas[j], column))
                        {
                            col_num = j/2;
                        }
                    }
                }
                else if (i == 1) {
                    ds.writeTable(this,br[i]);
                }
                else {
                    ds.writeTable(this,br[i]);
                    String[] mas = br[i].split(" ");
                    if (!Objects.equals(mas[col_num], condition))
                    {
                        continue;
                    }
                    List<String> list = new ArrayList<>(List.of());
                    for (int j = 0; j < mas.length; j++)
                    {
                        if (mas_col_num.contains(j))
                        {
                            list.add(mas[j]);
                        }
                    }
                    result.add(list);
                }
            }
            for (List<String> mas:result)
            {
                for (String s:mas)
                {
                    System.out.print(s+" ");
                }
                System.out.println("");
            }
        }
    }

    public void Insert(String[] text) throws IOException {
        DataStorage ds = new DataStorage();
        String s = "";
        for (String str: text)
        {
            s = s + str + " ";
        }
        ds.writeTable(this,s);
    }
    public void Delete(String column, String condition) throws IOException {
        DataStorage ds = new DataStorage();
        String[] br = ds.readTable(new Table(this.name));
        int col_num = 0;
        if (Objects.equals(column, ""))
        {
            for (int i = 0; i < br.length;i++)
            {
                if (i == 0 || i == 1) {
                    ds.writeTable(this,br[i]);
                } else {
                    ds.writeTable(this,"");
                }
            }
        }
        else {
            for (int i = 0; i < br.length; i++)
            {
                if (i == 0 ) {
                    String[] mas = br[i].split(" ");
                    for (int j = 0; j < mas.length; j++)
                    {
                        if (Objects.equals(mas[j], column))
                        {
                            col_num = j;
                            break;
                        }
                    }
                    ds.writeTable(this,br[i]);
                }
                else if (i == 1) {
                    ds.writeTable(this,br[i]);
                }
                else {
                    if (Objects.equals(br[i], ""))
                    {continue;}
                    String[] mas = br[i].split(" ");
                    if (Objects.equals(condition, mas[col_num-1]))
                    {
                        ds.writeTable(this,"");
                    }
                    else
                    {
                        ds.writeTable(this,br[i]);
                    }
                }
            }
        }
    }
}