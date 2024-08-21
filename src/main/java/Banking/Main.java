package Banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nWelcome to the Bank!");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account holder's name: ");
                    scanner.nextLine(); // consume newline
                    String name = scanner.nextLine();
                    System.out.println("Choose Account Type (1: Savings, 2: Checking): ");
                    int typeChoice = scanner.nextInt();
                    String accountType = (typeChoice == 1) ? "Savings" : "Checking";
                    bank.createAccount(name, accountType);
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    int accountNumber = scanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    double amount = scanner.nextDouble();
                    bank.deposit(accountNumber, amount);
                    break;

                case 3:
                    exit = true;
                    System.out.println("Exiting the banking system. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
