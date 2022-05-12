package main.java.com.luxoft.jva.collections.bankapp.domain;

import java.util.Formatter;
import java.util.Locale;

import main.java.com.luxoft.jva.collections.bankapp.exceptions.NotEnoughFundsException;
import main.java.com.luxoft.jva.collections.bankapp.exceptions.OverdraftLimitExceededException;
import main.java.com.luxoft.jva.collections.bankapp.utils.Params;

public class CheckingAccount extends AbstractAccount {
	
	private static final long serialVersionUID = 7922392307762434334L;
	private double overdraft;

	public CheckingAccount(int id, double amount, double overdraft) {
		super(id, amount);
		if (overdraft < 0) {
			throw new IllegalArgumentException("Cannot create an account with a starting negative overdraft");
		}
		this.overdraft = overdraft;
	}
	
	@Override
    public void withdraw(double value) throws OverdraftLimitExceededException {
        try {
            super.withdraw(value);
        } catch (NotEnoughFundsException notEnoughFundsException) {
            throw new OverdraftLimitExceededException(notEnoughFundsException, overdraft);
        }
    }

	public double getOverdraft() {
		return overdraft;
	}
	
	public double maximumAmountToWithdraw(){
        return getBalance() + overdraft;
    }
	
	@Override
	public String toString() {
		Formatter fmt = new Formatter(Locale.US);
		String stringAccount = fmt.format("Checking account %d, balance: %.2f, overdraft: %.2f", getId(), getBalance(), getOverdraft()).toString(); 
		fmt.close();
		return stringAccount;
	}
	
	public static Account parse(Params params) {
        return new CheckingAccount(
		        Integer.parseInt(params.get("id")),
		        Double.parseDouble(params.getOrDefault("balance", "0")),
		        Double.parseDouble(params.getOrDefault("overdraft", "0")));
    }
}
