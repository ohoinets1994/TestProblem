package com.hoinets.implementation;

import com.hoinets.base.Expense;
import com.hoinets.base.ExpensesOperations;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommandLineParser {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ExpensesOperations expensesOperations;
    private BufferedReader bufferedReader;

    public void startParsing(ExpensesOperations expensesOperations) {
        this.expensesOperations = expensesOperations;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        parseCommands();
    }

    private void parseCommands() {
        try {
            String text;
            while (!(text = bufferedReader.readLine()).equals("ESC")) {
                List<String> list = Arrays.asList(text.split(" "));

                switch (list.get(0)) {
                    case "add":
                        parseAddCommand(list);
                        break;
                    case "list":
                        parseListCommand(list);
                        break;
                    case "clear":
                        parseClearCommand(list);
                        break;
                    case "total":
                        parseTotalCommand(list);
                        break;
                    default:
                        System.out.println("Command is invalid.");
                        break;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseAddCommand(List<String> list) {
        try {
            Date date = dateFormat.parse(list.get(1));
            float cost = Float.parseFloat(list.get(2));
            String currency = list.get(3);

            StringBuilder stringName = new StringBuilder();
            for (int i = 4; i < list.size(); i++) {
                stringName.append(list.get(i));
                if (list.size() > 5) {
                    stringName.append(" ");
                }
            }

            String name = stringName.toString();
            if (name.contains("\"")) {
                name = name.substring(1, name.length() - 2);
            }

            expensesOperations.add(date, new Expense(cost, currency, name));
            expensesOperations.list();
        } catch (Exception e) {
            System.out.println("Command is invalid.\nExample: add 2017-04-25 2 USD Jogurt");
        }
    }

    private void parseListCommand(List<String> list) {
        if (list.size() > 1) {
            System.out.println("Command is invalid.\nExample: list");
        } else {
            expensesOperations.list();
        }
    }

    private void parseClearCommand(List<String> list) {
        try {
            if (list.size() < 2) {
                System.out.println("Command is invalid.\nExample: clear 2017-04-27");
            } else {
                Date date = dateFormat.parse(list.get(1));
                expensesOperations.clear(date);
                expensesOperations.list();
            }
        } catch (ParseException e) {
            System.out.println("Command is invalid.\nExample: clear 2017-04-27");
        }
    }

    private void parseTotalCommand(List<String> list) {
        if (list.size() < 2) {
            System.out.println("Command is invalid.\nExample: total PLN");
        } else {
            expensesOperations.total(list.get(1).toUpperCase());
        }
    }
}
