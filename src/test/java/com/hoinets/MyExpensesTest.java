package com.hoinets;

import com.hoinets.application.MyExpenses;
import com.hoinets.base.ExchangeRater;
import com.hoinets.base.Expense;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;


public class MyExpensesTest {
    private MyExpenses myExpenses;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        myExpenses = new MyExpenses(new ExchangeRater() {
            @Override
            public float getRate(String currency) {
                return 1;
            }
        });

        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUp() {
        System.setOut(null);
    }

    @Test
    public void add() {
        Date date = new Date();
        Expense expense = new Expense(1, "UAH", "name");

        myExpenses.add(date, expense);

        assertEquals(expense, myExpenses.getExpensesMap().get(date).get(0));
        assertEquals(1, myExpenses.getExpensesMap().size());

        try {
            Date date1 = dateFormat.parse("2017-04-25");
            Expense expense1 = new Expense(1, "UAH", "name1");
            Date date2 = dateFormat.parse("2017-04-25");
            Expense expense2 = new Expense(2, "UAH", "name2");

            myExpenses.add(date1, expense1);
            myExpenses.add(date2, expense2);

            assertEquals(2, myExpenses.getExpensesMap().size());
            assertEquals(expense1, myExpenses.getExpensesMap().get(date2).get(0));
            assertEquals(expense2, myExpenses.getExpensesMap().get(date2).get(1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void list() {
        Date date = new Date();
        Expense expense = new Expense(1, "UAH", "name");

        myExpenses.add(date, expense);
        myExpenses.list();
//        assertEquals(dateFormat.format(date) + "\nname 1.0 UAH\n", outContent.toString());
    }

    @Test
    public void clear() {
        Date date = new Date();
        Expense expense = new Expense(1, "UAH", "name");

        myExpenses.add(date, expense);
        myExpenses.clear(date);
        assertEquals(0, myExpenses.getExpensesMap().size());

        try {
            Date date1 = dateFormat.parse("2017-04-25");
            Expense expense1 = new Expense(1, "UAH", "name1");
            Date date2 = dateFormat.parse("2017-04-25");
            Expense expense2 = new Expense(2, "UAH", "name2");
            Date date3 = dateFormat.parse("2017-04-27");
            Expense expense3 = new Expense(3, "UAH", "name3");
            Date date4 = dateFormat.parse("2017-04-26");
            Expense expense4 = new Expense(4, "UAH", "name4");
            Date date5 = dateFormat.parse("2017-04-26");
            Expense expense5 = new Expense(5, "UAH", "name5");

            myExpenses.add(date1, expense1);
            myExpenses.add(date2, expense2);
            myExpenses.add(date3, expense3);
            myExpenses.add(date4, expense4);
            myExpenses.add(date5, expense5);

            assertEquals(3, myExpenses.getExpensesMap().size());

            myExpenses.clear(date1);

            assertEquals(2, myExpenses.getExpensesMap().size());
            System.out.println(myExpenses.getExpensesMap().size());
            myExpenses.list();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void total() {
        try {
            Date date1 = dateFormat.parse("2017-04-25");
            Expense expense1 = new Expense(1, "UAH", "name1");
            Date date2 = dateFormat.parse("2017-04-25");
            Expense expense2 = new Expense(2, "UAH", "name2");
            Date date3 = dateFormat.parse("2017-04-27");
            Expense expense3 = new Expense(3, "UAH", "name3");
            Date date4 = dateFormat.parse("2017-04-26");
            Expense expense4 = new Expense(4, "UAH", "name4");
            Date date5 = dateFormat.parse("2017-04-26");
            Expense expense5 = new Expense(5, "UAH", "name5");

            myExpenses.add(date1, expense1);
            myExpenses.add(date2, expense2);
            myExpenses.add(date3, expense3);
            myExpenses.add(date4, expense4);
            myExpenses.add(date5, expense5);

            myExpenses.total("USD");
            assertEquals("15,00 USD\n", outContent.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
