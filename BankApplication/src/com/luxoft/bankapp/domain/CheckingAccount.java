package com.luxoft.bankapp.domain;

import java.util.UUID;

public class CheckingAccount extends AbstractAccount
{
    private double overdraft;

    public CheckingAccount(UUID id, double balance, double overdraft) {
        this.id = id;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > this.balance) {
            balance += overdraft;
        }
        balance -= amount;
    }

    public double maximumAmountToWithdraw() {
        return getBalance() + this.overdraft;
    }
}
