package com.branches.cpu.request;

import com.branches.cpu.model.Client;
import com.branches.cpu.model.ClientType;

public record ClientPostRequest (String name, ClientType clientType) {
    public static ClientPostRequest of(Client client) {
        return new ClientPostRequest(client.getName(), client.getClientType());
    }
}
