package com.hoinets.application;

import com.hoinets.implementation.NetworkExchangeRater;

public class Starter {

    public static void main(String[] args) {
        new MyExpenses(new NetworkExchangeRater()).appStart();
    }
}
