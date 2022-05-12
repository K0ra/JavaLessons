package com.luxoft.bankapp.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client extends Indexed
{
    private final String name;
    private final Gender gender;
    private ArrayList<Account> accounts;

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.accounts = new ArrayList<Account>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getName() { return this.name; }

    public Gender getGender() { return this.gender; }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(this.accounts);
    }

    public String getClientGreeting() {
        if (gender != null)
            return this.gender.getGreeting() + " " + this.name;

        return this.name;
    }

    @Override
    public String toString() {
        return getClientGreeting();
    }

}
