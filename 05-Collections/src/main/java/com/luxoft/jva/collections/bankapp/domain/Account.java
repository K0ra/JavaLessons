package main.java.com.luxoft.jva.collections.bankapp.domain;

import main.java.com.luxoft.jva.collections.bankapp.exceptions.NotEnoughFundsException;

public interface Account {
	public void deposit(double amount);
	public void withdraw(double amount) throws NotEnoughFundsException;
	public int getId();
	public double getBalance();
	public long decimalValue();
	public double maximumAmountToWithdraw();
}
