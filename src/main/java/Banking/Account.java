package Banking;

public class Account {

    private int accountNumber;
    private String accountHolderName;
    private String accountType;
    private double balance;

    public Account(int accountNumber, String accountHolderName, double balance) {

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {

        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful");
            System.out.println("Deposited amount: " + amount );
            System.out.println("Deposited " + amount + " to " + accountType);
            System.out.println("New balance: " + balance);

        } else {
            System.out.println("Deposit failed! Check if amount is greater than zero");
        }
    }
}
