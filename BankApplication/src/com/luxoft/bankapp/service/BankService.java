package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.Client;

public class BankService
{
    public static void addClient(Bank bank, Client client) {
        bank.addClient(client);
    }

    public void printMaximumAmountToWithdraw(Bank bank) {
        System.out.format("%n---Maximum Balance to withdraw for all clients---%n");

        for (Client client : bank.getClients()) {
            System.out.println("Client: " + client.toString());
            for (Account account : client.getAccounts())
            {
                System.out.format(" maximum amount to withdraw: %.2f%n", account.maximumAmountToWithdraw());
            }
        }
    }
}
