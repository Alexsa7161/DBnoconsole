package rest.controller.classes;
import rest.controller.interfaces.ITransaction;
import java.io.IOException;
import java.util.List;

public class Transaction implements ITransaction {
    String queryText;
    private boolean inTransaction;
    Query q;
    public Transaction(Query q) {
        this.inTransaction = false;
        this.queryText = q.getQueryText();
        this.q = q;
    }

    public void startTransaction() throws IOException {
        if (!inTransaction) {
            inTransaction = true;
            System.out.println("Transaction started.");
        } else {
            System.out.println("Transaction is already in progress.");
            return;
        }
        Parser parser = new Parser();
        List<Instruction> list = parser.parse(q);
        for (Instruction i : list)
        {
            Table.executeInstruction(i);
        }
        System.out.println("Transaction ended.");
    }
}