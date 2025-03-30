import controller.ExpenseTrackerController;
import javax.swing.JOptionPane;
import model.AmountFilter;
import model.CategoryFilter;
import model.ExpenseTrackerModel;
import model.TransactionFilter;
import view.ExpenseTrackerView;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
     try {
        // Get transaction data from view
        double amount = view.getAmountField();
        String category = view.getCategoryField();
        // Call controller to add transaction
        boolean added = controller.addTransaction(amount, category);
              
        if (!added) {
          JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
          view.toFront();
        }
     } catch (Exception error) {
      JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
     }
    });

    // Add filter functionality
    view.getApplyFilterBtn().addActionListener(e -> {
      String filterType = view.getFilterType();
      String filterText = view.getFilterText();
      
      try {
          TransactionFilter filter = filterType.equals("Amount") 
              ? new AmountFilter(filterText)
              : new CategoryFilter(filterText);
          controller.applyFilter(filter);
      } catch (IllegalArgumentException ex) {
          JOptionPane.showMessageDialog(view, 
              ex.getMessage(), 
              "Filter Error", 
              JOptionPane.ERROR_MESSAGE);
      }
  });

  view.getResetFilterBtn().addActionListener(e -> {
    controller.resetFilter();
});

  }

}