package main.java.com.luxoft.jva.collections.bankapp.main;

import main.java.com.luxoft.jva.collections.bankapp.domain.Account;
import main.java.com.luxoft.jva.collections.bankapp.domain.Bank;
import main.java.com.luxoft.jva.collections.bankapp.domain.CheckingAccount;
import main.java.com.luxoft.jva.collections.bankapp.domain.Client;
import main.java.com.luxoft.jva.collections.bankapp.domain.Gender;
import main.java.com.luxoft.jva.collections.bankapp.domain.SavingAccount;
import main.java.com.luxoft.jva.collections.bankapp.email.EmailService;
import main.java.com.luxoft.jva.collections.bankapp.exceptions.ClientExistsException;
import main.java.com.luxoft.jva.collections.bankapp.exceptions.NotEnoughFundsException;
import main.java.com.luxoft.jva.collections.bankapp.exceptions.OverdraftLimitExceededException;
import main.java.com.luxoft.jva.collections.bankapp.net.BankClient;
import main.java.com.luxoft.jva.collections.bankapp.net.BankServer;
import main.java.com.luxoft.jva.collections.bankapp.service.BankDataLoaderService;
import main.java.com.luxoft.jva.collections.bankapp.service.BankReport;
import main.java.com.luxoft.jva.collections.bankapp.service.BankService;
import main.java.com.luxoft.jva.collections.bankapp.utils.Arguments;
import main.java.com.luxoft.jva.collections.bankapp.exceptions.ClientExistsException;

public class BankApplication {
	
	private static Bank bank;
	
	public static void main(final String[] args) throws InterruptedException {
		Arguments arguments = new Arguments(args);
		
		
		if (arguments.hasKey("-server")) {
			BankServer server = new BankServer(arguments);
		    server.run();
        } else if (arguments.hasKey("-client")) {
            new BankClient().run();
            System.exit(0);
        } else if (arguments.hasKey("-loadfeed")) {
			String fileName = args[1];
			BankService.setSerializationFileName(args[2]);
			bank = new Bank();
			EmailService emailService = new EmailService();
			bank.setEmailService(emailService);
			BankDataLoaderService bankDataLoaderService = new BankDataLoaderService(bank);
			bankDataLoaderService.readClients(fileName);
			printBalance();
			emailService.close();
		} else  if (arguments.hasKey("-loadbank")) {
			BankService.setSerializationFileName(args[1]);
			bank = BankService.readBank();	
			printBalance();
		}
		// TODO add support for -statistic parameter
		// Add print of bank statistics using BankReport class.
		else if (arguments.hasKey("-statistic")) {
			BankService.setSerializationFileName(args[1]);
			bank = BankService.readBank();
			BankReport bankReport = new BankReport(bank);
			// Numeric values
			System.out.println("Number of bank clients: " + bankReport.getNumberOfClients());
			System.out.println("Total number of accounts for all bank clients: " + bankReport.getNumberOfAccounts());
			System.out.println("Total sum in accounts: " + bankReport.getTotalSumInAccounts());
			System.out.println("Bank credit sum: " + bankReport.getBankCreditSum());
			// Object values
			System.out.println("Bank clients sorted by name: " + bankReport.getClientsSorted());
			System.out.println("Bank accounts sorted by sum: " + bankReport.getAccountsSortedBySum());
			System.out.println("Bank accounts: " + bankReport.getCustomerAccounts());
			System.out.println("Bank clients by city: " + bankReport.getClientsByCity());
		}
		else {
			bank = new Bank();
			modifyBank();
			BankService.printMaximumAmountToWithdraw(bank);
		}
		
	}
	
	private static void modifyBank() {
		Client client1 = new Client("John", Gender.MALE);
		Account account1 = new SavingAccount(1, 100);
		Account account2 = new CheckingAccount(2, 100, 20);
		client1.addAccount(account1);
		client1.addAccount(account2);
		
		try {
		   BankService.addClient(bank, client1);
		} catch(ClientExistsException e) {
			System.out.format("Cannot add an already existing client: %s%n", client1);
	    } 

		account1.deposit(100);
		try {
		  account1.withdraw(10);
		} catch (OverdraftLimitExceededException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(90);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(100);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  BankService.addClient(bank, client1);
		} catch(ClientExistsException e) {
		  System.out.format("Cannot add an already existing client: %s%n", client1);
	    } 
	}
	
	private static void printBalance() {
		System.out.format("%nPrint balance for all clients%n");
		for(Client client : bank.getClients()) {
			System.out.println("Client: " + client);
		}
	}

}
