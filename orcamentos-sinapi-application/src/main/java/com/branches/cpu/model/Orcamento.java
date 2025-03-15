package com.branches.cpu.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Orcamento {
    private Long id;
    private String nome;
    private String nomeCliente;
    private LocalDate dataCriacao;
}
