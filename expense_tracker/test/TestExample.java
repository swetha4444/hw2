// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;  // Add this import

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.AmountFilter;
import model.CategoryFilter;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;


public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }

    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(50.00, "food"));
    
        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);
    }


    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "Groceries");
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    /**
     * Requirement 1: Add Transaction
     * - Steps: Add transaction with amount 50.00 and category "food"
     * - Expected: Transaction added to table, Total Cost updated
     */
    @Test
    public void testAddTransactionAndUpdateTotal() {
        // Pre-condition: Empty initial state
        assertEquals(0, model.getTransactions().size());
        assertEquals(0.0, getTotalCost(), 0.01);

        // Step: Add transaction with amount 50.00 and category "food"
        double amount = 50.00;
        String category = "food";
        boolean result = controller.addTransaction(amount, category);

        // Expected Output: Transaction added and total updated
        assertTrue(result);  // Addition successful
        assertEquals(1, model.getTransactions().size());  // One transaction added
        assertEquals(amount, getTotalCost(), 0.01);  // Total cost updated
        assertEquals(category, model.getTransactions().get(0).getCategory());
    }

    /**
     * Requirement 2: Invalid Input Handling
     * - Steps: Try adding invalid transactions
     * - Expected: Error messages shown, no state changes
     */
    @Test
    public void testInvalidInputHandling() {
        // Pre-condition: Record initial state
        int initialSize = model.getTransactions().size();
        double initialTotal = getTotalCost();

        // Steps: Attempt invalid transactions
        assertFalse(controller.addTransaction(-50.00, "food"));  // Invalid amount
        assertFalse(controller.addTransaction(0.00, "food"));    // Invalid amount
        assertFalse(controller.addTransaction(50.00, ""));       // Invalid category
        assertFalse(controller.addTransaction(50.00, "invalid")); // Invalid category

        // Expected Output: No changes to state
        assertEquals(initialSize, model.getTransactions().size());
        assertEquals(initialTotal, getTotalCost(), 0.01);
    }    

     /**
     * Requirement 3: Filter by Amount
     * - Steps: Add transactions with different amounts, filter
     * - Expected: Only matching amounts returned
     */
    @Test
    public void testFilterByAmount() {
        // Steps: Add transactions with different amounts
        controller.addTransaction(50.00, "food");
        controller.addTransaction(100.00, "travel");
        controller.addTransaction(50.00, "bills");

        // Step: Apply amount filter
        AmountFilter filter = new AmountFilter("50.00");
        List<Transaction> filtered = filter.filter(model.getTransactions());

        // Expected Output: Only matching transactions returned
        assertEquals(2, filtered.size());
        for (Transaction t : filtered) {
            assertEquals(50.00, t.getAmount(), 0.01);
        }
    }

    /**
     * Requirement 4: Filter by Category
     * - Steps: Add transactions with different categories, filter
     * - Expected: Only matching categories returned
     */
    @Test
    public void testFilterByCategory() {
        // Steps: Add transactions with different categories
        controller.addTransaction(50.00, "food");
        controller.addTransaction(100.00, "travel");
        controller.addTransaction(75.00, "food");

        // Step: Apply category filter
        CategoryFilter filter = new CategoryFilter("food");
        List<Transaction> filtered = filter.filter(model.getTransactions());

        // Expected Output: Only matching transactions returned
        assertEquals(2, filtered.size());
        for (Transaction t : filtered) {
            assertEquals("food", t.getCategory());
        }
    }
}