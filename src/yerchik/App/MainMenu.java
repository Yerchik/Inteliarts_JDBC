package yerchik.App;

import com.mysql.jdbc.Connection;
import yerchik.DB.AccountDB;
import yerchik.DB.SpendingDB;
import yerchik.Entity.Account;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Yerchik on 20.05.2017.
 */
public class MainMenu {

    public static void meinMenu() throws SQLException, IOException {
        Connection connection = (Connection) DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/IntelliartsTest?createDatabaseIfNotExist=true",
                        "root", "root");

        initializeDatabase(connection);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, User!");
        String switcher = "1";
        do {
            System.out
                    .println("Choose operation:\n 1 - Login;\n 2 - Create account;\n 3 - Exit");
            String operation = scanner.next();


            if (operation.equals("1")) {
                login(connection);
            }
            if (operation.equals("2")) {
                addNewAccount(connection);
            }
            if (operation.equals("3")) {
                System.exit(0);
            }
            if (!operation.equals("1") && !operation.equals("2") && !operation.equals("3"))
                System.out.println("put number of menu:");
            else {
                System.out
                        .println("Start with menu of bank?\n 1 - Yes \n 2 - No!");


                switcher = scanner.next();
            }

        } while (switcher.equals("1"));
        connection.close();
    }

    public static void addNewAccount(Connection connection) throws SQLException, IOException {
        System.out.println("Input your credentials:");
        Scanner scanner = new Scanner(System.in);
        Account account = new Account();
        do {
            System.out.println("Login = ");
            account.setLogin(scanner.next());
            if (!AccountDB.uniqueLogin(connection, account.getLogin()))
                System.out.println("You can't use this login, put another!");
        } while (!AccountDB.uniqueLogin(connection, account.getLogin()));
        System.out.println("Password = ");
        account.setPassword(scanner.next());
        System.out.println("Name = ");
        account.setName(scanner.next());
        System.out.println("Second name = ");
        account.setSecondName(scanner.next());
        AccountDB.createAccount(connection, account);
        SpendingMenu.menu(connection);
    }

    public static void login(Connection connection) throws SQLException, IOException {
        System.out.println("Input your credentials:");
        Scanner scanner = new Scanner(System.in);
        String login, password;
        do {
            System.out.println("Input login:");
            login = scanner.next();
            if (AccountDB.uniqueLogin(connection, login))
                System.out.println("Account with such login doesn't exist!");
        }while (AccountDB.uniqueLogin(connection, login));
        do {
            System.out.println("Input password:");
            password = scanner.next();
            if (!AccountDB.checkingOfPassword(connection, login, password))
                System.out.println("Wrong password!");
        }while (!AccountDB.checkingOfPassword(connection, login, password));
        SpendingMenu.menu(connection);

    }

    public static void initializeDatabase(Connection connection)
            throws SQLException {
        AccountDB.createAccountTable(connection);
        SpendingDB.createSpendingTable(connection);

    }
}
