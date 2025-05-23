package com.branches.cpu.model;

public class Client {
    private Long id;
    private String name;
    private ClientType clientType;

    public Client(Long id, String name, ClientType clientType) {
        this.id = id;
        this.name = name;
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        return name;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
