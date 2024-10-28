package rest.controller.classes;

import java.io.IOException;
import java.util.Scanner;

public class DBInterface {
	public static void main (String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		Boolean check = true;
		while (check)
		{
			String input = scanner.nextLine().trim();
				if (input.split(" ")[0].equalsIgnoreCase("connect")) {
		        	DBConnection con = new DBConnection();
		        	if (con.connect(input.replace("connect ","")))
		        	{
		        		DBConsole db =  new DBConsole(con);
		        		db.start();
		        	}
		        }
				else
				{
					System.out.println("Введены неверные команды");
				}
		}
    }
}