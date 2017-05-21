package yerchik.Entity;

/**
 * Created by Yerchik on 20.05.2017.
 */
public class Spending {
    private int id;
    private double amount;
    private String description;
    private String date;
    private String currency;
    private int accountId;

    public Spending() {
    }

    public Spending(double amount, String description, String date, String currency) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return description + " - " + amount + " " + currency + "\n";
    }
}
