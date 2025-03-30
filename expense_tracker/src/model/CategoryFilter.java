package model;

import java.util.List;
import java.util.stream.Collectors;
import controller.InputValidation;

public class CategoryFilter implements TransactionFilter {
    private final String category;

    public CategoryFilter(String category) throws IllegalArgumentException {
        if (!InputValidation.isValidCategory(category)) {
            throw new IllegalArgumentException("Invalid category: must be one of [food, travel, bills, entertainment, other]");
        }
        this.category = category.toLowerCase();
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                         .filter(t -> t.getCategory().toLowerCase().equals(category))
                         .collect(Collectors.toList());
    }
}