import java.util.HashMap;
import java.util.Scanner;

class Stock {
    String name;
    double price;
    int quantity;

    Stock(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

class User {
    double balance;
    HashMap<String, Integer> portfolio = new HashMap<>();

    User(double balance) {
        this.balance = balance;
    }

    void buyStock(Stock stock, int qty) {
        double cost = stock.price * qty;
        if (balance >= cost && stock.quantity >= qty) {
            balance -= cost;
            portfolio.put(stock.name, portfolio.getOrDefault(stock.name, 0) + qty);
            stock.quantity -= qty;
            System.out.println("Success: Bought " + qty + " " + stock.name);
        } else {
            System.out.println("Failed: Low balance or stock unavailable!");
        }
    }

    void sellStock(Stock stock, int qty) {
        if (portfolio.getOrDefault(stock.name, 0) >= qty) {
            balance += stock.price * qty;
            portfolio.put(stock.name, portfolio.get(stock.name) - qty);
            stock.quantity += qty;
            System.out.println("Success: Sold " + qty + " " + stock.name);
        } else {
            System.out.println("Failed: Not enough stocks to sell!");
        }
    }

    void displayPortfolio() {
        System.out.println("----- Portfolio -----");
        System.out.println("Balance: ₹" + balance);
        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned");
        } else {
            portfolio.forEach((stock, qty) -> System.out.println(stock + ": " + qty + " shares"));
        }
        System.out.println("---------------------");
    }
}

public class StockTrading {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Stock s1 = new Stock("INFY", 1500, 100);
        Stock s2 = new Stock("TCS", 3500, 50);
        User user = new User(100000);

        while (true) {
            System.out.println("\n1. View Stocks\n2. Buy\n3. Sell\n4. Portfolio\n5. Exit");
            System.out.print("Choose option: ");
            int ch = sc.nextInt();
            
            switch (ch) {
                case 1:
                    System.out.println("INFY: ₹1500 | Available: " + s1.quantity);
                    System.out.println("TCS: ₹3500 | Available: " + s2.quantity);
                    break;
                case 2:
                    System.out.print("Buy: 1.INFY 2.TCS → ");
                    int b = sc.nextInt();
                    System.out.print("Quantity: ");
                    int bqty = sc.nextInt();
                    if (b == 1) user.buyStock(s1, bqty);
                    else user.buyStock(s2, bqty);
                    break;
                case 3:
                    System.out.print("Sell: 1.INFY 2.TCS → ");
                    int s = sc.nextInt();
                    System.out.print("Quantity: ");
                    int sqty = sc.nextInt();
                    if (s == 1) user.sellStock(s1, sqty);
                    else user.sellStock(s2, sqty);
                    break;
                case 4:
                    user.displayPortfolio();
                    break;
                case 5:
                    System.out.println("Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}