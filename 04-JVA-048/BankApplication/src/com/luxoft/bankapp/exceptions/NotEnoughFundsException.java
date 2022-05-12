package com.luxoft.bankapp.exceptions;

import java.util.UUID;

public class NotEnoughFundsException extends Exception
{
    private UUID id;
    private double balance;
    private double amount;

    public NotEnoughFundsException(UUID id, double balance, double amount, String message) {
        super(message);
        this.id = id;
        this.balance = balance;
        this.amount = Math.round(amount * 100) / 100d;
    }

    public NotEnoughFundsException(UUID id, double balance, double amount) {
        super(String.format("Not enough funds for account %s, balance: %.2f, tried to extract amount: %.2f%n",
                            id.toString(), balance, amount));
        this.id = id;
        this.balance = balance;
        this.amount = Math.round(amount * 100) / 100d;
    }

    public UUID getId() {
        return id;
    }

    public double getBalance() { return balance; }

    public double getAmount() {
        return amount;
    }
}
