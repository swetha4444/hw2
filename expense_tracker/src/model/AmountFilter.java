package model;

import java.util.List;
import java.util.stream.Collectors;
import controller.InputValidation;

public class AmountFilter implements TransactionFilter {
    private final double amount;

    public AmountFilter(String amountStr) throws IllegalArgumentException {
        try {
            double parsedAmount = Double.parseDouble(amountStr);
            if (!InputValidation.isValidAmount(parsedAmount)) {
                throw new IllegalArgumentException("Invalid amount: must be between 0 and 1000");
            }
            this.amount = parsedAmount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a valid number");
        }
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                         .filter(t -> t.getAmount() == amount)
                         .collect(Collectors.toList());
    }
}