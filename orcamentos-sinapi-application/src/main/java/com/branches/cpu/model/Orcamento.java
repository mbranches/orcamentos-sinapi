package com.branches.cpu.model;

import java.time.LocalDate;

public class Orcamento {
    private Long id;
    private String nome;
    private String nomeCliente;
    private LocalDate dataCriacao;

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public LocalDate getDataCriacao() {
        return this.dataCriacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
