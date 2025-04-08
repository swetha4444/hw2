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

    /**
     * Tests adding a transaction using the encapsulated method
     * Uses primitive types instead of Transaction objects
     */
    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction using primitive types
        double amount = 50.00;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
        assertEquals(amount, getTotalCost(), 0.01);
    }


    /**
     * Tests removing a transaction using the encapsulated method
     * Uses primitive types instead of Transaction objects
     */
    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Add a transaction using encapsulated method
        double amount = 50.00;
        String category = "food";
        controller.addTransaction(amount, category);
    
        // Verify transaction was added
        assertEquals(1, model.getTransactions().size());
    
        // Remove the transaction using encapsulated method
        assertTrue(model.removeTransaction(amount, category));
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        assertEquals(0.00, getTotalCost(), 0.01);
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

    
    // Additional Tests
    /**
     * Test multiple filters combined
     * Verifies that filters can be chained/combined
     */
    @Test
    public void testMultipleFilters() {
        // Setup test data
        controller.addTransaction(50.00, "food");
        controller.addTransaction(50.00, "travel");
        controller.addTransaction(100.00, "food");

        // Apply both filters
        AmountFilter amountFilter = new AmountFilter("50.00");
        CategoryFilter categoryFilter = new CategoryFilter("food");
        
        List<Transaction> filtered = amountFilter.filter(model.getTransactions());
        filtered = categoryFilter.filter(filtered);

        // Verify results
        assertEquals(1, filtered.size());
        assertEquals(50.00, filtered.get(0).getAmount(), 0.01);
        assertEquals("food", filtered.get(0).getCategory());
    }

    /**
     * Test boundary conditions for amount validation
     */
    @Test
    public void testAmountBoundaries() {
        // Test boundary values
        assertFalse(controller.addTransaction(-0.01, "food")); // Just below 0
        assertTrue(controller.addTransaction(0.01, "food"));    // Just above 0
        assertTrue(controller.addTransaction(999.99, "food"));  // Just below 1000
        assertFalse(controller.addTransaction(1000.01, "food")); // Just above 1000
    }

    /**
     * Test immutability of returned transaction list
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testTransactionListImmutability() {
        // Add a transaction
        controller.addTransaction(50.00, "food");
        
        // Try to modify the returned list
        List<Transaction> transactions = model.getTransactions();
        transactions.clear(); // Should throw UnsupportedOperationException
    }

    /**
     * Test empty filter results
     */
    @Test
    public void testEmptyFilterResults() {
        // Add transactions
        controller.addTransaction(50.00, "food");
        controller.addTransaction(75.00, "travel");
        
        // Filter with non-matching criteria
        AmountFilter filter = new AmountFilter("100.00");
        List<Transaction> filtered = filter.filter(model.getTransactions());
        
        // Verify empty result handling
        assertTrue(filtered.isEmpty());
        assertEquals(0, filtered.size());
    }
}