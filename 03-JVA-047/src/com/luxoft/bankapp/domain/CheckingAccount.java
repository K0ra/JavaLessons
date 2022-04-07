package com.luxoft.bankapp.domain;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;

import java.util.UUID;

public class CheckingAccount extends AbstractAccount
{
    private double overdraft;

    public CheckingAccount(UUID id, double balance, double overdraft) {
        this.id = id;
        this.balance = balance;

        if(overdraft < 0) {
            throw new IllegalArgumentException("Cannot create an account with a starting negative overdraft");
        }
        this.overdraft = overdraft;
    }

    @Override
    public void withdraw(double amount) throws OverdraftLimitExceededException {
        try {
            super.withdraw(amount);
            if (amount > this.balance) {
                balance += this.overdraft;
            }
        } catch (NotEnoughFundsException notEnoughFundsException) {
            throw new OverdraftLimitExceededException(notEnoughFundsException, this.overdraft);
        }
    }

    public double getOverdraft() { return this.overdraft; }

    @Override
    public double maximumAmountToWithdraw() {
        return getBalance() + this.overdraft;
    }
}
