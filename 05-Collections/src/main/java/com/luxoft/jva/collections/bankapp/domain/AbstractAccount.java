package main.java.com.luxoft.jva.collections.bankapp.domain;

import main.java.com.luxoft.jva.collections.bankapp.exceptions.NotEnoughFundsException;
import main.java.com.luxoft.jva.collections.bankapp.utils.Params;

import java.io.Serializable;

public abstract class AbstractAccount implements Account, Serializable {

	private static final long serialVersionUID = -2272551373694344386L;
	protected double balance;
	private int id;

	public AbstractAccount(int id, double amount) {
		this.id = id;
		this.balance = amount;
	}

	public static Account parse(Params params) {

		switch (params.get("accountType")) {
			case "s":
				return SavingAccount.parse(params);
			case "c":
				return CheckingAccount.parse(params);
		}
		return null;
	}

	@Override
	public void deposit(final double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot deposit a negative amount");
		}
		this.balance += amount;
	}

	@Override
	public void withdraw(final double amount) throws NotEnoughFundsException {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw a negative amount");
		}

		if (amount > maximumAmountToWithdraw()) {
			throw new NotEnoughFundsException(id, balance, amount, "Requested amount exceeds the maximum amount to withdraw");
		}

		this.balance -= amount;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public long decimalValue() {
		return Math.round(balance);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAccount other = (AbstractAccount) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
