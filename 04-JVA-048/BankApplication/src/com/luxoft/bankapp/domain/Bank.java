package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.utils.ClientRegistrationListener;

import java.util.ArrayList;

public class Bank
{
    private ArrayList<Client> clients;
    private ArrayList<ClientRegistrationListener> listeners;
    private int printedClients;
    private int emailedClients;
    private int debuggedClients;

    public Bank() {
        this.clients = new ArrayList<Client>();
        this.listeners = new ArrayList<ClientRegistrationListener>();
        printedClients = 0;
        emailedClients = 0;
        debuggedClients = 0;

        class DebugListener implements ClientRegistrationListener {
            @Override
            public void onClientAdded(Client c) {
                System.out.println(c.toString() + ", time: " + java.time.LocalDate.now());
            }
        }

        this.listeners.add(new PrintClientListener());
        this.listeners.add(new EmailNotificationListener());
        this.listeners.add(new DebugListener());
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void addClient(Client client) {
        clients.add(client);
        for (ClientRegistrationListener listener : listeners) {
            listener.onClientAdded(client);
        }
    }

    public class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println(c.toString());
        }
    }

    public class EmailNotificationListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println("Notification email for client " + c.getName() + " to be sent.");
        }
    }

}