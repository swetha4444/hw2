# Expense Tracker Application

A Java application for tracking and managing expenses with filtering capabilities.

## Features

- Add and remove transactions with amount and category
- Input validation for transaction data
  - Amount must be between 0 and 1000
  - Category must be one of: food, travel, bills, entertainment, other
- Filter transactions by:
  - Amount
  - Category
- Immutable transaction records
- Encapsulated data management

## Building and Running

```bash
cd expense_tracker
ant compile
java -cp bin ExpenseTrackerApp
```

## Running Tests

```bash
cd expense_tracker
ant test
```

## Implementation Details

### Data Model
- Immutable Transaction objects
- Encapsulated transaction list
- Input validation
- Read-only access to transaction data

### Filters
- Amount-based filtering
- Category-based filtering
- Support for multiple filters
- Validation of filter parameters

## Recent Changes
- Added transaction filtering
- Improved data encapsulation
- Enhanced input validation
- Added comprehensive test cases