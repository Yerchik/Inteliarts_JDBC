package yerchik.DB;

import com.mysql.jdbc.PreparedStatement;
import yerchik.Entity.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yerchik on 20.05.2017.
 */
public class AccountDB {
    private static String name = "";
    private static String secondName = "";
    private static int id = 0;
    private static java.sql.PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static void createAccountTable(java.sql.Connection connection)
            throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("create table if not exists account(id int primary key auto_increment, " +
                        "login varchar(30) not null unique, password varchar(30) not null, name varchar(20) not null, secondName varchar(20) not null );");
        preparedStatement.execute();
    }

    public static boolean uniqueLogin(Connection connection, String login) throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from account where login like ?");
        preparedStatement.setString(1, login);

        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkingOfPassword(Connection connection, String login, String password) throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from account where login like ?");
        preparedStatement.setString(1, login);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            if (resultSet.getString("password").equals(password)) {
                id = resultSet.getInt("id");
                secondName = resultSet.getString("secondName");
                name = resultSet.getString("name");
                return true;
            } else {
                return false;
            }
        }else return false;
    }

    public static void createAccount(Connection connection, Account account) throws SQLException {
        preparedStatement = connection
                .prepareStatement("insert into account(login, password, name, secondName) VALUES (?,?,?,?)");
        preparedStatement.setString(1, account.getLogin());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.setString(3, account.getName());
        preparedStatement.setString(4, account.getSecondName());
        preparedStatement.execute();
        name = account.getName();
        secondName = account.getSecondName();
        id = account.getId();
        System.out.println("Your account have been created!");

    }

    public static String getName() {
        return name;
    }

    public static String getSecondName() {
        return secondName;
    }

    public static int getId() {
        return id;
    }
}
