package main.java.com.luxoft.jva.collections.bankapp.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.java.com.luxoft.jva.collections.bankapp.email.Email;
import main.java.com.luxoft.jva.collections.bankapp.email.EmailException;
import main.java.com.luxoft.jva.collections.bankapp.email.EmailService;
import main.java.com.luxoft.jva.collections.bankapp.exceptions.ClientExistsException;
import main.java.com.luxoft.jva.collections.bankapp.utils.ClientRegistrationListener;

public class Bank implements Serializable {
	
	private static final long serialVersionUID = -4157871135257285214L;
	private final Set<Client> clients;
	private final List<ClientRegistrationListener> listeners = new ArrayList<>();
	private EmailService emailService;

	private Client admin = new Client("Admin", Gender.MALE);
	private Client system = new Client("System", Gender.MALE);
	
	private int printedClients = 0;
	private int emailedClients = 0;
	private int debuggedClients = 0;
	
	public Bank() {
		clients = new HashSet<>(); // TODO Initialize it!
		listeners.add(new PrintClientListener());
		listeners.add(new EmailNotificationListener());
		listeners.add(new DebugListener());
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	public int getPrintedClients() {
		return printedClients;
	}

	public int getEmailedClients() {
		return emailedClients;
	}

	public int getDebuggedClients() {
		return debuggedClients;
	}
	
	public void addClient(final Client client) throws ClientExistsException {
		// TODO implement this method
		if (clients.contains(client)) {
			throw new ClientExistsException("Client already exists into the bank.");
		}
		clients.add(client);
		// TODO Uncomment line below after end of implementation
         notify(client);
	}
	
	private void notify(Client client) {
        for (ClientRegistrationListener listener: listeners) {
            listener.onClientAdded(client);
        }
    }
	
	public Collection<Client> getClients() {
		return Collections.unmodifiableCollection(clients);
	}
	
	public Client getClient(String name) {
        for (Client client: clients)
            if (client.getName().equals(name))
                return client;
        return null;
    }
	
	public Collection<Account> getAccounts() {
        Collection<Account> result = new HashSet<>();
        for (Client client: clients)
            result.addAll(client.getAccounts());
        return result;
    }
	
	class PrintClientListener implements ClientRegistrationListener, Serializable {
		private static final long serialVersionUID = 2777987742204604236L;

		@Override 
		public void onClientAdded(Client client) {
	        System.out.println("Client added: " + client.getName());
	    }

	}
	
	class EmailNotificationListener implements ClientRegistrationListener, Serializable {
		private static final long serialVersionUID = -2360873324733537279L;

		@Override 
		public void onClientAdded(Client client) {
	        System.out.println("Notification email for client " + client.getName() + " to be sent");
	        
	        if(null == emailService ) {
	        	return;
	        }
	        
	        try {
	        	emailService.sendNotificationEmail(
                        new Email()
                                .setFrom(system)
                                .setTo(admin)
                                .setCopy(client)
                                .setTitle("Client Added Notification")
                                .setBody("Client added: " + client)
                );
            } catch (EmailException e) {
                System.err.println(e.getMessage());
            }
	    }
	}
	
	class DebugListener implements ClientRegistrationListener, Serializable {
		private static final long serialVersionUID = -7600469994081192859L;

		@Override public void onClientAdded(Client client) {
            System.out.println("Client " + client.getName() + " added on: " + DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
        }
    }

}




