package com.luxoft.bankapp.domain;

import java.util.UUID;

public abstract class AbstractAccount implements Account {
    protected UUID id = null;
    protected double balance;

    @Override
    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount)
            balance -= amount;
    }

    @Override
    public double maximumAmountToWithdraw() {
        return getBalance();
    }
}
