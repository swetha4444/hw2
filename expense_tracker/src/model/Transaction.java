package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an immutable transaction in the expense tracker.
 * This class is made final to prevent inheritance and ensure immutability.
 * No setter methods - prevents state changes
 * All fields are primitive or immutable types
 */
public final class Transaction {
    // All fields are private and final to ensure immutability
    private final double amount;
    private final String category;
    private final String timestamp;

    /**
     * Creates a new Transaction with the specified amount and category.
     * The timestamp is automatically generated at creation time.
     *
     * @param amount The transaction amount
     * @param category The transaction category
     */
    public Transaction(double amount, String category) {
        this.amount = amount;
        this.category = category;
        this.timestamp = generateTimestamp();
    }

    /**
     * @return The transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return The transaction category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * @return The transaction timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Generates a timestamp for the transaction in dd-MM-yyyy HH:mm format
     * @return The formatted timestamp string
     */
    private String generateTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");  
        return sdf.format(new Date());
    }

    /**
     * Returns a string representation of the Transaction
     */
    @Override
    public String toString() {
        return "Transaction [amount=" + amount + 
               ", category=" + category + 
               ", timestamp=" + timestamp + "]";
    }
}