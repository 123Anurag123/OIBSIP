import java.util.*;
class User {
    private String userId;
    private String pin;
    private double balance;
    private Map<String, Double> transactionHistory;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        addToTransactionHistory("Deposit", amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            addToTransactionHistory("Withdrawal", amount);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            addToTransactionHistory("Transfer to " + recipient.getUserId(), amount);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void addToTransactionHistory(String transactionType, double amount) {
        transactionHistory.put(transactionType, amount);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Map.Entry<String, Double> entry : transactionHistory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Current Balance: " + balance);
    }
}

public class ATMInterface {
    private static final Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        initializeUsers();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM Interface!");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (authenticateUser(userId, pin)) {
            User currentUser = users.get(userId);
            handleATMOperations(currentUser, scanner);
        } else {
            System.out.println("Invalid User ID or PIN. Exiting.");
        }

        scanner.close();
    }

    private static void initializeUsers() {
        users.put("user1", new User("user1", "1234", 1000.0));
        users.put("user2", new User("user2", "1234", 1500.0));
        users.put("user3", new User("user3", "1234", 3000.0));
        users.put("user4", new User("user4", "1234", 10000.0));
    }

    private static boolean authenticateUser(String userId, String pin) {
        if (users.containsKey(userId) && users.get(userId).getPin().equals(pin)) {
            System.out.println("Authentication successful!");
            return true;
        } else {
            return false;
        }
    }

    private static void handleATMOperations(User currentUser, Scanner scanner) {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser.displayTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    System.out.print("SUCESSFULLY DEPOSITED");
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentUser.withdraw(withdrawAmount);
                    System.out.print("SUCESSFULLY WITHDREW ");
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    if (users.containsKey(recipientId)) {
                        User recipient = users.get(recipientId);
                        currentUser.transfer(recipient, transferAmount);
                        System.out.print("SUCESSFULLY TRANSFERED MONEY");
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting ATM Interface. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void displayMenu() {
        System.out.println("\nATM Operations Menu:");
        System.out.println("1. Display Transactions History");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }
}

