package com.branches.cpu.request;

import com.branches.cpu.model.ClientType;

public class ClientPostRequest {
    private String name;
    private ClientType clientType;

    public ClientPostRequest(String name, ClientType clientType) {
        this.name = name;
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
