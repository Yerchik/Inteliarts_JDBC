package yerchik.App;

import yerchik.DB.AccountDB;
import yerchik.DB.SpendingDB;
import yerchik.Entity.Spending;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Yerchik on 21.05.2017.
 */
public class SpendingMenu {
    public static void menu(Connection connection) throws SQLException, IOException {
        System.out.println("Hello " + AccountDB.getName() + " "
                + AccountDB.getSecondName());
        boolean a = true;
        while (a) {

            System.out
                    .println("Menu accaunta:\n 1 - Add;\n 2 - List;\n 3 - Clear by date;\n 4 - Total by currency;\n 5 - Exit");
            Scanner scanner = new Scanner(System.in);
            String operation = scanner.next();
            if (operation.equals("1")) {
                add(connection);
            } else if (operation.equals("2")) {
                list(connection);
            } else if (operation.equals("3")) {
                clear(connection);
            } else if (operation.equals("4")) {
                total(connection);
            } else if (operation.equals("5"))
                a = false;
            else {
                System.out.println("Put number of menu:");
            }
        }
    }

    public static void add(Connection connection) throws SQLException {
        Spending spending = new Spending();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input date(it should look like - 'year-month-day'):");
        spending.setDate(scanner.next());
        System.out.println("Input amount:");
        spending.setAmount(scanner.nextDouble());
        System.out.println("Input currency:");
        spending.setCurrency(scanner.next());
        System.out.println("Input description:");
        spending.setDescription(scanner.next());
        spending.setAccountId(AccountDB.getId());
        SpendingDB.add(connection, spending);
    }

    public static void list(Connection connection) throws SQLException, IOException {
        List<Spending> all = SpendingDB.getAll(connection);
        List<String> date = new ArrayList<>();
        Iterator<String> iterator = SpendingDB.getAllDate(connection).iterator();
        while (iterator.hasNext()) {
            date.add(iterator.next());
        }
        for (String s : date) {
            System.out.println(s);
            for (Spending spending : all) {
                if (spending.getDate().equals(s)) System.out.println(spending);
            }
        }
        System.out.println("Pres any button:");
        System.in.read();
    }

    public static void clear(Connection connection) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        do {
            int i = 0;
            System.out.println("Select a date: ");
            Iterator<String> iterator = SpendingDB.getAllDate(connection).iterator();
            while (iterator.hasNext()) {
                i++;
                System.out.println(i + " - " + iterator.next());
            }
            if (i == 0) {
                System.out.println("Your list is clear");
                break;
            }
            String operation = scanner.next();
            try {
                if (Integer.valueOf(operation) <= i) {
                    SpendingDB.deleteByDate(connection, Integer.valueOf(operation));
                    System.out.println("Date was deleted!");
                    break;
                }
            } catch (Exception e) {

            }
        } while (true);
        System.out.println("Pres any button:");
        System.in.read();
    }

    public static void total(Connection connection) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int i = 0;
            System.out.println("Select a currency: ");
            Iterator<String> iterator = SpendingDB.getAllCurrency(connection).iterator();
            while (iterator.hasNext()) {
                i++;
                System.out.println(i + " - " + iterator.next());
            }
            if (i == 0) {
                System.out.println("Your list is clear");
                break;
            }
            String operation = scanner.next();
            try {
                if (Integer.valueOf(operation) <= i) {
                    SpendingDB.totalByCurrency(connection, Integer.valueOf(operation));
                    break;
                }
            } catch (Exception e) {

            }
        }
        System.out.println("Pres any button:");
        System.in.read();
    }
}
