package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Gender;
import com.luxoft.bankapp.service.BankService;

import java.util.UUID;

public class BankApplication
{
    private static Bank bank;

    public static void modifyBank(Account account) {
        account.deposit(100);
        account.withdraw(20);
    }

    public static void printBalance() {
        System.out.format("%n---Balance for all clients---%n");

        for (Client client : bank.getClients())
        {
            System.out.println("Client: " + client.getName());
            for (Account account : client.getAccounts())
            {
                System.out.format("Account balance: %.2f%n", account.getBalance());
            }
        }
    }

    public static void main(String[] args) {
        bank = new Bank();

        Account acc1 = new SavingAccount(UUID.randomUUID(), 1000.0);
        Account acc2 = new CheckingAccount(UUID.randomUUID(), 1000.0, 20.0);

        Client client1 = new Client("Tim Cook", Gender.MALE);
        client1.addAccount(acc1);

        Client client2 = new Client("Robert Smith", Gender.MALE);
        client2.addAccount(acc2);

        bank.addClient(client1);
        bank.addClient(client2);

        modifyBank(acc1);

        printBalance();

        BankService service = new BankService();
        service.printMaximumAmountToWithdraw(bank);
    }
}