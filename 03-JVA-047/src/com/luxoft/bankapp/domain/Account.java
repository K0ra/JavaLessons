package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;


public interface Account
{
    public void deposit(double amount);

    public void withdraw(double amount) throws IllegalArgumentException, NotEnoughFundsException;

    public double getBalance();

    public double maximumAmountToWithdraw();
}