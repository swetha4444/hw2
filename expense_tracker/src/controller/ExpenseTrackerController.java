package controller;

import java.util.List;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.TransactionFilter;
import view.ExpenseTrackerView;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    boolean success = model.addTransaction(amount, category);
    if (success) {
      // view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
      refresh();  // This will update the view through proper channel
    }
    return success;
  }
  
  // Other controller methods

  public void applyFilter(TransactionFilter filter) {
    List<Transaction> allTransactions = model.getTransactions();
    List<Transaction> filteredTransactions = filter.filter(allTransactions);
    view.refreshTable(filteredTransactions);
  }

  public void resetFilter() {
    view.resetFilterFields();  // Reset the filter input fields
    refresh();                 // Show all transactions
  }
  
}