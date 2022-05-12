package main.java.com.luxoft.jva.collections.bankapp.domain;

import main.java.com.luxoft.jva.collections.bankapp.utils.Params;

import java.util.Formatter;
import java.util.Locale;


public class SavingAccount extends AbstractAccount {

	private static final long serialVersionUID = 9200460687227050240L;

	public SavingAccount(int id, double amount) {
		super(id, amount);
	}

	public static Account parse(Params params) {
		return new SavingAccount(Integer.parseInt(params.get("id")), Double.parseDouble(params.getOrDefault("balance", "0")));
	}

	public double maximumAmountToWithdraw() {
		return getBalance();
	}

	@Override
	public String toString() {
		Formatter fmt = new Formatter(Locale.US);
		String stringAccount = fmt.format("Saving account %d, balance: %.2f", getId(), getBalance()).toString();
		fmt.close();
		return stringAccount;
	}

}
