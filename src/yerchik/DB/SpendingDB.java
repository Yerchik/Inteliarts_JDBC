package yerchik.DB;

import com.mysql.jdbc.PreparedStatement;
import yerchik.Entity.Spending;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Yerchik on 20.05.2017.
 */
public class SpendingDB {
    private static java.sql.PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static void createSpendingTable(java.sql.Connection connection)
            throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("create table if not exists spending(id int primary key auto_increment, amount double not null," +
                        " description varchar(100) not null, date varchar(20) not null, currency varchar(20) not null, id_account int not null );");
        preparedStatement.execute();
    }

    public static void add(Connection connection, Spending spending) throws SQLException {
        preparedStatement = connection
                .prepareStatement("insert into spending(amount, description, date, currency, id_account) VALUES (?,?,?,?,?)");
        preparedStatement.setDouble(1, spending.getAmount());
        preparedStatement.setString(2, spending.getDescription());
        preparedStatement.setString(3, spending.getDate());
        preparedStatement.setString(4, spending.getCurrency());
        preparedStatement.setInt(5, spending.getAccountId());
        preparedStatement.execute();
        System.out.println("You have added!");
    }

    public static List<Spending> getAll(Connection connection) throws SQLException {
        List<Spending> all = new ArrayList<>();
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from spending where id_account like ?");
        preparedStatement.setInt(1, AccountDB.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            all.add(new Spending(resultSet.getDouble("amount"), resultSet.getString("description"),
                    resultSet.getString("date"), resultSet.getString("currency")));
        }
        return all;
    }

    public static Set<String> getAllDate(Connection connection) throws SQLException {
        Set<String> allDate = new TreeSet<>();
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from spending where id_account like ?");
        preparedStatement.setInt(1, AccountDB.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            allDate.add(resultSet.getString("date"));
        }
        return allDate;
    }

    public static void deleteByDate(Connection connection, int j) throws SQLException {
        String date = "";
        int i = 1;
        Iterator<String> iterator = getAllDate(connection).iterator();
        while (iterator.hasNext()) {
            date = iterator.next();
            if (i == j) break;
            i++;
        }
        preparedStatement = (java.sql.PreparedStatement) connection
                .prepareStatement("DELETE from spending WHERE id_account = ? AND date = ?");
        preparedStatement.setInt(1, AccountDB.getId());
        preparedStatement.setString(2, date);
        preparedStatement.executeUpdate();
    }

    public static Set<String> getAllCurrency(Connection connection) throws SQLException {
        Set<String> allCurrency = new LinkedHashSet<>();
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from spending where id_account like ?");
        preparedStatement.setInt(1, AccountDB.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            allCurrency.add(resultSet.getString("currency"));
        }
        return allCurrency;
    }

    public static void totalByCurrency(Connection connection, int j) throws SQLException {
        double total = 0;
        String currency = "";
        int i = 1;
        Iterator<String> iterator = getAllCurrency(connection).iterator();
        while (iterator.hasNext()) {
            currency = iterator.next();
            if (i == j) break;
            i++;
        }
        preparedStatement = (java.sql.PreparedStatement) connection
                .prepareStatement("SELECT * from spending WHERE id_account = ? AND currency = ?");
        preparedStatement.setInt(1, AccountDB.getId());
        preparedStatement.setString(2, currency);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            total += resultSet.getDouble("amount");
        }
        System.out.println("Total: " + total + " " + currency);
    }


}
