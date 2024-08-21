package Banking;

import java.sql.*;

public class Bank {
    private Connection conn;
    private String url = "jdbc:sqlite:resources/bank.db";  // SQLite database file location
    private int nextAccountNumber;

    public Bank() {
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                createTable();
                nextAccountNumber = getNextAccountNumber();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS accounts (" +
                "account_number INTEGER PRIMARY KEY, " +
                "account_holder_name TEXT NOT NULL, " +
                "account_type TEXT NOT NULL, " +  // Added account_type column
                "balance REAL DEFAULT 0.0)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private int getNextAccountNumber() {
        String sql = "SELECT MAX(account_number) FROM accounts";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.getInt(1) + 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;  // If no records, start from 1
    }

    public void createAccount(String accountHolderName, String accountType) {
        String sql = "INSERT INTO accounts(account_number, account_holder_name, account_type, balance) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nextAccountNumber);
            pstmt.setString(2, accountHolderName);
            pstmt.setString(3, accountType);  // Store account type in the database
            pstmt.setDouble(4, 0.0);
            pstmt.executeUpdate();
            System.out.println("Account created successfully! Account Number: " + nextAccountNumber);
            nextAccountNumber++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Account getAccount(int accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_number"),
                        rs.getString("account_holder_name"),
                        rs.getString("account_type"),  // Retrieve account type
                        rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Account not found.");
        return null;
    }

    public void deposit(int accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            updateBalance(account);
        }
    }

    private void updateBalance(Account account) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, account.getBalance());
            pstmt.setInt(2, account.getAccountNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
