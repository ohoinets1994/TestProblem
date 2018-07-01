package com.hoinets.base;

public class Expense {
    private String name;
    private float cost;
    private String currency;

    public Expense(float cost, String currency, String name) {
        this.name = name;
        this.cost = cost;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        //todo implement
        return name +" " + cost + " " + currency;
    }
}
