package com.luxoft.bankapp.main;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankService;

import java.util.UUID;

public class BankApplication
{
    private static Bank bank;

    private static void modifyBank(Account account) {
        account.deposit(5);
        try {
            account.withdraw(100);
        } catch (OverdraftLimitExceededException e) {
            System.out.format("Not enough funds for account %s, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printBalance() {
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

        Account account1 = new SavingAccount(UUID.randomUUID(), 10.0);
        Account account2 = new CheckingAccount(UUID.randomUUID(), 10.0, 20.0);

        Client client1 = new Client("Tim Cook", Gender.MALE);
        client1.addAccount(account1);

        Client client2 = new Client("Robert Smith", Gender.MALE);
        client2.addAccount(account2);

        bank.addClient(client1);
        bank.addClient(client2);

        modifyBank(account1);

        printBalance();

        BankService service = new BankService();
        service.printMaximumAmountToWithdraw(bank);
    }
}