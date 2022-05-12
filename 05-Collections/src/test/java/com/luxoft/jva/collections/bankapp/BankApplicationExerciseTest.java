package test.java.com.luxoft.jva.collections.bankapp;

import main.java.com.luxoft.jva.collections.bankapp.domain.Account;
import main.java.com.luxoft.jva.collections.bankapp.domain.Bank;
import main.java.com.luxoft.jva.collections.bankapp.domain.Client;
import main.java.com.luxoft.jva.collections.bankapp.domain.Gender;
import main.java.com.luxoft.jva.collections.bankapp.service.BankDataLoaderService;
import main.java.com.luxoft.jva.collections.bankapp.service.BankReport;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankApplicationExerciseTest {

	String clientsFile = "/Users/mbair13/IdeaProjects/JavaLessons/05-Collections/src/files/clients.txt";
	Bank bank = new Bank();
	BankReport bankReport;

	@Before
	public void initialize() {
		BankDataLoaderService bankDataLoaderService = new BankDataLoaderService(bank);
		bankDataLoaderService.readClients(clientsFile);
		bankReport = new BankReport(bank);
	}

	@Test
	public void testNumberOfClients() {
		assertEquals(10, bankReport.getNumberOfClients());
	}


	@Test
	public void testNumberOfAccounts() {
		assertEquals(10, bankReport.getNumberOfAccounts());
	}

	@Test
	public void testClientsSorted() {
		SortedSet<Client> clients = bankReport.getClientsSorted();

		assertEquals(10, clients.size());
		assertTrue(clients.first().equals(new Client(("Brian"), Gender.MALE)));
		assertTrue(clients.last().equals(new Client(("Michelle"), Gender.FEMALE)));
	}

	@Test
	public void testBankCreditSum() {
		assertEquals(1477.9, bankReport.getBankCreditSum(), 0);
	}

	@Test
	public void testCustomerAccounts() {
		Map<Client, Collection<Account>> customerAccounts = bankReport.getCustomerAccounts();

		assertEquals(10, customerAccounts.size());
		assertEquals("Saving account 4, balance: 487.72", customerAccounts.get(new Client(("Brian"), Gender.MALE)).iterator().next().toString());
	}

	@Test
	public void testClientsByCity() {
		Map<String, List<Client>> clientsByCity = bankReport.getClientsByCity();

		assertEquals(2, clientsByCity.get("New York").size());
		assertEquals(8, clientsByCity.get("Boston").size());
	}


	@Test
	public void booleanGrouping() throws Exception {
		List<String> strings = new ArrayList<>();
		strings.add("ala");
		strings.add("ela");
		strings.add("jan");

		strings.stream()
				.collect(
						Collectors.groupingBy(s -> s.endsWith("a")) // using function Obj -> Bool not predicate
				).entrySet()
				.stream()
				.collect(
						Collectors.toMap(
								e -> e.getKey() ? "Present" : "Past",
								e -> e.getValue().stream().collect(Collectors.joining(""))
						)
				);
	}
}
