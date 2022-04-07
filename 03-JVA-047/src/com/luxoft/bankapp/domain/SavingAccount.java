package com.luxoft.bankapp.domain;

import java.util.UUID;

public class SavingAccount extends AbstractAccount
{
    public SavingAccount(UUID id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    @Override
    public double maximumAmountToWithdraw() {
        return super.maximumAmountToWithdraw();
    }
}
