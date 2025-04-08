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
    
}