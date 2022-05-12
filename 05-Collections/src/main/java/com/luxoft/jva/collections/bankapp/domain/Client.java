package main.java.com.luxoft.jva.collections.bankapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import main.java.com.luxoft.jva.collections.bankapp.utils.Params;

public class Client implements Serializable {
		
	private static final long serialVersionUID = -6343841891631428291L;
	private String name;
	private Gender gender;
	private Set<Account> accounts;
	private String city;

	public Client(String name, Gender gender) {
		this(name, gender, new ArrayList<Account>());
	}
	
	public Client(String name, Gender gender, Collection<Account> accounts) {
		this.accounts = new HashSet<>(); // TODO initialize this!
		this.name = name;
		this.gender = gender;
		this.accounts.addAll(accounts);
	}
	
	public Client(String name, Gender gender, Account[] accounts){
        this(name, gender, Arrays.asList(accounts));
    }
	
	public Client(final String name, final Gender gender, Account account) {
		this(name, gender, new Account[]{account});
	}
	
	public void addAccount(final Account account) {
		accounts.add(account);
	}
	
	public String getName() {
		return name;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Collection<Account> getAccounts() {
		// TODO You need to implement this in proper way
		return Collections.unmodifiableCollection(accounts);
	}
	
	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getClientGreeting())
		  .append("\n");
		
		for (Account account : getAccounts()) {
			sb.append(account)
			  .append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Client other = (Client) obj;
		if (gender != other.gender)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public static Client parseClient(String str){
		Params params = new Params(str.split(";"));
		
        Client client = new Client(
                params.get("name"),
                Gender.parse(params.get("gender")),
                AbstractAccount.parse(params));
        
        client.setCity(params.get("city"));

        return client;
    }

	

}
