package main.java.com.luxoft.jva.collections.bankapp.service;

import main.java.com.luxoft.jva.collections.bankapp.domain.Account;
import main.java.com.luxoft.jva.collections.bankapp.domain.Bank;
import main.java.com.luxoft.jva.collections.bankapp.domain.CheckingAccount;
import main.java.com.luxoft.jva.collections.bankapp.domain.Client;

import java.util.*;

public class BankReport {

	private Bank bank;

	public BankReport(Bank bank) {
		this.bank = bank;
	}

	public Bank getBank() {
		return bank;
	}

	/* Returns the number of bank clients */
	public int getNumberOfClients() {
		// TODO add implementation
		return bank.getClients().size();
	}

	/* Returns the total number of accounts for all bank clients */
	public int getNumberOfAccounts() {
		// TODO add implementation
		return bank.getAccounts().size();
	}

	/* Returns the set of clients in alphabetical order */
	public SortedSet<Client> getClientsSorted() {
		// TODO add implementation
		SortedSet<Client> clients = new TreeSet<>((client1, client2) -> {
			if (client1.getName() == null) {
				return 1;
			}
			return client1.getName().compareTo(client2.getName());
		});
		clients.addAll(bank.getClients());
		return clients;
	}

	/* Returns the total sum (balance) from the accounts of all bank clients */
	public double getTotalSumInAccounts() {
		// TODO add implementation
		double totalBalance = 0.0;
		for (Account account: bank.getAccounts())
			totalBalance += account.getBalance();
		// We return double, but what about BigDecimal?
		return Math.round(totalBalance * 100) / 100d;
	}

	/* Returns the set of all accounts. The list is ordered by current account balance */
	public SortedSet<Account> getAccountsSortedBySum() {
		// TODO add implementation
		SortedSet<Account> accounts = new TreeSet<>(
				(account1, account2) -> (int) Math.round(account1.getBalance() - account2.getBalance())
		);
		accounts.addAll(bank.getAccounts());
		return accounts;
	}

	/* Returns the total amount of credits granted to the bank clients. That is, the sum of all values above account balance for CheckingAccount */
	public double getBankCreditSum() {
		// TODO add implementation
		double result = 0.0;
		for (Account account: bank.getAccounts())
			if (account instanceof CheckingAccount) {
				if (account.getBalance() < 0) {
					result -= account.getBalance();
				}
			}
		return Math.round(result * 100) / 100d;
	}

	/* Returns a map of client accounts */
	public Map<Client, Collection<Account>> getCustomerAccounts() {
		// TODO add implementation
		Map<Client, Collection<Account>> customerAccounts = new HashMap<>();
		for (Client client: bank.getClients())
			customerAccounts.put(client, client.getAccounts());
		return customerAccounts;
	}

	/* Returns a map of cities and clients */
	public Map<String, List<Client>> getClientsByCity() {
		// TODO add implementation
		Set<Client> sortedClients = new HashSet<>(bank.getClients());
		Map<String, List<Client>> clientsByCity = new TreeMap<>();
		for (Client client: sortedClients) {
			String city = client.getCity();
			if (!clientsByCity.containsKey(city)) {
				List<Client> clientsNotYetInMap = new ArrayList<>();
				clientsNotYetInMap.add(client);
				clientsByCity.put(city, clientsNotYetInMap);
			} else {
				clientsByCity.get(city).add(client);
			}
		}
		return clientsByCity;
	}


}
