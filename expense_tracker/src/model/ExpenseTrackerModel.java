package model;

import java.util.ArrayList;
import java.util.Collections; // Added for unmodifiableList
import java.util.List;

public class ExpenseTrackerModel {
    // Changed to private for encapsulation
    // Added final to prevent reference modification
    private final List<Transaction> transactions;

    public ExpenseTrackerModel() {
        transactions = new ArrayList<>(); 
    }

    // Modified to take primitive types instead of Transaction object
    // This prevents external code from creating Transaction objects directly
    public boolean addTransaction(double amount, String category) {
      if (amount <= 0 || category == null || category.trim().isEmpty()) {
          return false;
      }
      Transaction t = new Transaction(amount, category);
      return transactions.add(t);
  }

    // Removed removeTransaction method to maintain immutability
    // External code should not be able to modify the transactions list

    /**
     * Returns an unmodifiable view of the transactions list.
     * Using Collections.unmodifiableList ensures:
     * 1. External code cannot modify the list
     * 2. Any attempt to modify the list will throw UnsupportedOperationException
     * 3. Original list remains encapsulated and protected
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}