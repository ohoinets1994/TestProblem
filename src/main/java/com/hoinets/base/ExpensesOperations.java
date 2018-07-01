package com.hoinets.base;

import java.util.Date;

public interface ExpensesOperations {
    void add(Date date, Expense expense);
    void list();
    void clear(Date date);
    void total(String currency);
}
