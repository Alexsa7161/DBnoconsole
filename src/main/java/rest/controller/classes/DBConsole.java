package rest.controller.classes;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import rest.controller.interfaces.IDBConnection;
import rest.controller.interfaces.IQuery;
import rest.controller.interfaces.IQueryParcer;
import rest.controller.interfaces.ITransaction;

public class DBConsole {
    private DBConnection connection;
    private Scanner scanner;

    public DBConsole(DBConnection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        boolean running = true;

        while (running) {
            System.out.print("\nВведите команду или SQL-запрос (exit для выхода): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                running = false;
                connection.disconnect();
                System.out.println("Отключено от БД и выход из программы.");
            }
            try {
                QueryParcer parcer = new QueryParcer();
                List<Query> list = parcer.parce(input);
                for (Query q : list) {
                    Transaction t = new Transaction(q);
                    t.startTransaction();
                }
            }
            catch (Exception e)
            {
                System.out.println("Ошибка!");
            }
        }

        scanner.close();
    }
}