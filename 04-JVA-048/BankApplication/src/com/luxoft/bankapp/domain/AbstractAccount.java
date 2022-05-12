package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.UUID;

public abstract class AbstractAccount implements Account {
    protected UUID id = null;
    protected double balance;

    @Override
    public double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {
        validateAmount(amount);
        this.balance += amount;
    }

    public void withdraw(double amount) throws IllegalArgumentException, NotEnoughFundsException {
        validateAmount(amount);
        checkLegalAmount(amount);
        this.balance -= amount;
    }

    @Override
    public double maximumAmountToWithdraw() {
        return getBalance();
    }

    protected void checkLegalAmount(double amount) throws NotEnoughFundsException {
        if (amount > maximumAmountToWithdraw()) { throw new NotEnoughFundsException(this.id, this.balance, amount); }
    }

    protected void validateAmount(double amount) throws IllegalArgumentException {
        if (amount < 0) { throw new IllegalArgumentException("Cannot deposit/withdraw a negative amount."); }
    }
}
