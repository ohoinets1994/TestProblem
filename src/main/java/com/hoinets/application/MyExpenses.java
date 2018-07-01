package com.hoinets.application;

import com.hoinets.base.ExchangeRater;
import com.hoinets.base.Expense;
import com.hoinets.base.ExpensesOperations;
import com.hoinets.implementation.CommandLineParser;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyExpenses implements ExpensesOperations {
    private Map<Date, List<Expense>> expensesMap = new TreeMap<Date, List<Expense>>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ExchangeRater exchangeRater;

    public MyExpenses(ExchangeRater exchangeRater){
        this.exchangeRater = exchangeRater;
    }

    public void appStart() {
        CommandLineParser parser = new CommandLineParser();
        parser.startParsing(this);
    }

    @Override
    public void add(Date date, Expense expense) {
        List<Expense> list = expensesMap.getOrDefault(date, null);
        if (list == null) {
            list = new ArrayList<>();
            list.add(expense);
            expensesMap.put(date, list);
        } else {
            list.add(expense);
        }
    }

    @Override
    public void list() {
        expensesMap.forEach((key, value) -> {
            String dateOfPurchase = dateFormat.format(key);
            System.out.println(dateOfPurchase);
            value.forEach(System.out::println);
            System.out.println();
        });
    }

    @Override
    public void clear(Date date) {
        expensesMap.remove(date);
    }

    @Override
    public void total(String currency) {
        try {
            Map<String, Float> map = new HashMap<String, Float>();

            expensesMap.forEach((key, value) -> {
                value.forEach(expense -> {
                    String k = expense.getCurrency();
                    float v = expense.getCost();
                    if (map.containsKey(k)) {
                        map.put(k, map.get(k) + v);
                    } else {
                        map.put(k, v);
                    }
                });
            });

            double costsInEUR = 0;
            for (Map.Entry e : map.entrySet()) {
                costsInEUR += (Float) e.getValue() / exchangeRater.getRate(e.getKey().toString());
            }
            double result = costsInEUR * exchangeRater.getRate(currency);
// right now it outputs number with comma `,`, but that is dependant on your current language set on your computer,
// if force to Locale.US it will output with dot `.`
            System.out.format(Locale.US, "%.2f " + currency, result);
            System.out.println("\n");
        } catch (JSONException e) {
            System.out.println(currency + " - this currency is invalid!");
        }
    }

    public Map<Date, List<Expense>> getExpensesMap() {
        return expensesMap;
    }
}