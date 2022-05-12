package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Client;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClientStorageService implements StorageService<Client> {
    private final List<Client> clients;

    public ClientStorageService() {
        this.clients = new ArrayList<>();
    }

    public ClientStorageService(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public Client store(Client client)
    {
        if (!containsClient(client)) {
            clients.add(client);
            return client;
        }

        return null;
    }

    private boolean containsClient(Client clientToCheck)
    {
        for (Client client : clients)
        {
            if (client.getName().equals(clientToCheck.getName()))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public Client getById(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }

        return null;
    }

    @Override
    public Client update(@NotNull Client toUpdate) {
        Client client = getById(toUpdate.getId());

        if (client != null) {
            clients.remove(client);
            clients.add(toUpdate);
        }

        return client;
    }

    @Override
    public void delete(Client object) {
        clients.remove(object);
    }

    public List<Client> getAll() {
        return new ArrayList<>(clients);
    }
}