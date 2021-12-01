package com.luxoft.bankapp.domain;

import java.util.UUID;

public interface Account
{
    public void deposit(double amount);

    public void withdraw(double amount);

    public double getBalance();

    public double maximumAmountToWithdraw();
}