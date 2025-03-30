package model;

import java.util.List;

/**
 * Strategy interface for filtering transactions
 */
public interface TransactionFilter {
    List<Transaction> filter(List<Transaction> transactions);
}