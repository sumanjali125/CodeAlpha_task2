import java.util.HashMap;
import java.util.Scanner;
class Stock {
    String ticker;      
    double currentPrice; 
    int sharesAvailable; 
    Stock(String ticker, double currentPrice, int sharesAvailable) {
        this.ticker = ticker;
        this.currentPrice = currentPrice;
        this.sharesAvailable = sharesAvailable;
    }
}
class User {
    double cashBalance; 
    HashMap<String, Integer> myPortfolio = new HashMap<>(); 
    User(double startingCash) {
        this.cashBalance = startingCash; 
    }
    void buyShares(Stock stock, int quantity) {
        double totalAmount = stock.currentPrice * quantity;
        if (cashBalance < totalAmount) {
            System.out.println("Oops! You don't have enough cash. Your balance: $" + cashBalance);
            return;
        }
        if (stock.sharesAvailable < quantity) {
            System.out.println("Sorry, not enough shares available. Only " + stock.sharesAvailable + " left.");
            return;
        }
        
        cashBalance = cashBalance - totalAmount;
        
        int currentShares = myPortfolio.getOrDefault(stock.ticker, 0);
        myPortfolio.put(stock.ticker, currentShares + quantity);
        
        stock.sharesAvailable = stock.sharesAvailable - quantity;
        
        System.out.println("Great! You bought " + quantity + " shares of " + stock.ticker);
        System.out.println("Remaining cash: $" + cashBalance);
    }

    void sellShares(Stock stock, int quantity) {
        int sharesOwned = myPortfolio.getOrDefault(stock.ticker, 0);
        
        if (sharesOwned < quantity) {
            System.out.println("You can't sell that many. You only have " + sharesOwned + " shares.");
            return;
        }
        
        double saleValue = stock.currentPrice * quantity;
        cashBalance = cashBalance + saleValue;
        myPortfolio.put(stock.ticker, sharesOwned - quantity);
        
        stock.sharesAvailable = stock.sharesAvailable + quantity;
        
        System.out.println("Success! Sold " + quantity + " shares of " + stock.ticker);
        System.out.println("New balance: $" + cashBalance);
    }

    void showPortfolio() {
        System.out.println("\n===== MY PORTFOLIO =====");
        System.out.println("Cash Balance: $" + cashBalance);
        
        boolean hasStocks = false;
        for (String stockName : myPortfolio.keySet()) {
            int qty = myPortfolio.get(stockName);
            if (qty > 0) {
                System.out.println(stockName + " : " + qty + " shares");
                hasStocks = true;
            }
        }
        
        if (!hasStocks) {
            System.out.println("You don't own any stocks yet.");
        }
        System.out.println("========================\n");
    }
}

public class StockTrading {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        Stock infy = new Stock("INFY", 1500.0, 100);
        Stock tcs = new Stock("TCS", 3500.0, 50);
        
        User trader = new User(100000.0);
        
        System.out.println("=== Welcome to Simple Stock Trader ===");
        
        boolean running = true;
        while (running) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. View Available Stocks");
            System.out.println("2. Buy Stocks");
            System.out.println("3. Sell Stocks");
            System.out.println("4. View My Portfolio");
            System.out.println("5. Exit App");
            System.out.print("Enter choice 1-5: ");
            
            int choice = input.nextInt();
            
            if (choice == 1) {
                System.out.println("\n--- Market Stocks ---");
                System.out.println("INFY - Price: $" + infy.currentPrice + " | Available: " + infy.sharesAvailable);
                System.out.println("TCS  - Price: $" + tcs.currentPrice + " | Available: " + tcs.sharesAvailable);
            } 
            else if (choice == 2) {
                System.out.print("Which stock? 1 for INFY, 2 for TCS: ");
                int stockChoice = input.nextInt();
                System.out.print("How many shares? : ");
                int qty = input.nextInt();
                
                if (stockChoice == 1) {
                    trader.buyShares(infy, qty);
                } else if (stockChoice == 2) {
                    trader.buyShares(tcs, qty);
                } else {
                    System.out.println("Invalid stock choice. Please enter 1 or 2.");
                }
            } 
            else if (choice == 3) {
                System.out.print("Which stock to sell? 1 for INFY, 2 for TCS: ");
                int stockChoice = input.nextInt();
                System.out.print("How many shares to sell? : ");
                int qty = input.nextInt();
                
                if (stockChoice == 1) {
                    trader.sellShares(infy, qty);
                } else if (stockChoice == 2) {
                    trader.sellShares(tcs, qty);
                } else {
                    System.out.println("Invalid option. Choose 1 or 2.");
                }
            } 
            else if (choice == 4) {
                trader.showPortfolio();
            } 
            else if (choice == 5) {
                System.out.println("Thanks for using the app. Goodbye!");
                running = false; 
            } 
            else {
                System.out.println("Please enter a number between 1 and 5 only.");
            }
        }
        
        input.close(); 
    }
                               }
