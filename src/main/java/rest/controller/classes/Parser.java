package rest.controller.classes;
import rest.controller.interfaces.IParser;
import java.util.ArrayList;
import java.util.List;

public class Parser implements IParser {

    public List<Instruction> parse(Query query)
    {
        List<Instruction> instructions = new ArrayList<>();

        if (query.getQueryText() == null || query.getQueryText().isEmpty()) {
            throw new IllegalArgumentException("Query text cannot be null or empty");
        }
        String lowerCaseQuery = query.getQueryText().trim().toLowerCase();
        Instruction instruction = new Instruction(query.getQueryText());

        if (lowerCaseQuery.startsWith("select *")) {
            instruction.setOperationType("SELECT ALL");
        }
        else if (lowerCaseQuery.startsWith("select")) {
            instruction.setOperationType("SELECT");
        }
        else if (lowerCaseQuery.startsWith("drop table")) {
            instruction.setOperationType("DROP TABLE");
        }
        else if (lowerCaseQuery.startsWith("create table")) {
            instruction.setOperationType("CREATE TABLE");
        }
        else if (lowerCaseQuery.startsWith("rename table")) {
            instruction.setOperationType("RENAME TABLE");
        }
        else if (lowerCaseQuery.startsWith("rename column")) {
            instruction.setOperationType("RENAME COLUMN");
        }
        else if (lowerCaseQuery.startsWith("delete column")) {
            instruction.setOperationType("DELETE COLUMN");
        }
        else if (lowerCaseQuery.startsWith("add column")) {
            instruction.setOperationType("ADD COLUMN");
        }
        else if (lowerCaseQuery.startsWith("delete index")) {
            instruction.setOperationType("DELETE INDEX");
        }
        else if (lowerCaseQuery.startsWith("add index")) {
            instruction.setOperationType("ADD INDEX");
        }
        else if (lowerCaseQuery.startsWith("rename index")) {
            instruction.setOperationType("RENAME INDEX");
        }
        else if (lowerCaseQuery.startsWith("insert")) {
            instruction.setOperationType("INSERT"); 
        } 
        else if (lowerCaseQuery.startsWith("delete")) {
            instruction.setOperationType("DELETE"); 
        }
        else {
            System.out.println("Unsupported query: " + query.getQueryText());
            return instructions;
        }
        String[] mas = query.getQueryText().split(" ");
        for (int i=0;i<mas.length;i++)
        {
            if (mas[i].equalsIgnoreCase("table"))
            {
                instruction.tableName = mas[i+1];
                break;
            }
        }
        instructions.add(instruction);
        return instructions;	
    }
}