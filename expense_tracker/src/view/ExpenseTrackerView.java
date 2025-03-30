package view;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Transaction;

public class ExpenseTrackerView extends JFrame {
    // Transaction input components
    private JButton addTransactionBtn;
    private JFormattedTextField amountField;
    private JTextField categoryField;
    private JTable transactionsTable;
    private DefaultTableModel model;

    // Filter components
    private JTextField filterField;
    private JComboBox<String> filterTypeComboBox;
    private JButton applyFilterBtn;
    private JButton resetFilterBtn;

    public ExpenseTrackerView() {
        initializeFrame();
        initializeTable();
        createComponents();
        layoutComponents();
        setFrameProperties();
    }

    private void initializeFrame() {
        setTitle("Expense Tracker");
        setSize(600, 400);
    }

    private void initializeTable() {
        String[] columnNames = {"Serial", "Amount", "Category", "Date"};
        this.model = new DefaultTableModel(columnNames, 0);
        transactionsTable = new JTable(model);
    }

    private void createComponents() {
        // Transaction components
        addTransactionBtn = new JButton("Add Transaction");
        NumberFormat format = NumberFormat.getNumberInstance();
        amountField = new JFormattedTextField(format);
        amountField.setColumns(10);
        categoryField = new JTextField(10);

        // Filter components
        filterField = new JTextField(10);
        filterTypeComboBox = new JComboBox<>(new String[]{"Amount", "Category"});
        applyFilterBtn = new JButton("Apply Filter");
        resetFilterBtn = new JButton("Reset");
    }

    private void layoutComponents() {
        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        inputPanel.add(addTransactionBtn);

        // Create filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter by:"));
        filterPanel.add(filterTypeComboBox);
        filterPanel.add(new JLabel("Value:"));
        filterPanel.add(filterField);
        filterPanel.add(applyFilterBtn);
        filterPanel.add(resetFilterBtn);

        // Create top panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.add(inputPanel);
        topPanel.add(filterPanel);

        // Add to frame
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    }

    private void setFrameProperties() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void refreshTable(List<Transaction> transactions) {
        model.setRowCount(0);
        int rowNum = 0;
        double totalAmount = 0;

        for (Transaction t : transactions) {
            totalAmount += t.getAmount();
            model.addRow(new Object[]{
                ++rowNum,
                t.getAmount(),
                t.getCategory(),
                t.getTimestamp()
            });
        }

        // Add total row
        model.addRow(new Object[]{"Total", totalAmount, "", ""});
    }

    // Getters for transaction components
    public JButton getAddTransactionBtn() { return addTransactionBtn; }
    public double getAmountField() { 
        return amountField.getText().isEmpty() ? 0 : 
               Double.parseDouble(amountField.getText());
    }
    public String getCategoryField() { return categoryField.getText(); }

    // Getters for filter components
    public JButton getApplyFilterBtn() { return applyFilterBtn; }
    public JButton getResetFilterBtn() { return resetFilterBtn; }
    public String getFilterText() { return filterField.getText(); }
    public String getFilterType() { 
        return (String) filterTypeComboBox.getSelectedItem(); 
    }
    // Add this new method
    public void resetFilterFields() {
      filterField.setText("");
      filterTypeComboBox.setSelectedIndex(0);
  }
}