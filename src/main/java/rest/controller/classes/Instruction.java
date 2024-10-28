package rest.controller.classes;

import rest.controller.interfaces.IInstruction;
public class Instruction implements IInstruction {
    String text;
    String operationType;
    String tableName;
    public Instruction(String queryText) {
        this.text = queryText;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType.toUpperCase();
    }
}