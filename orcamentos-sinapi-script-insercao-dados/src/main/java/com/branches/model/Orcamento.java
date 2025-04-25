package com.branches.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Orcamento {
    private Long id;
    private String nome;
    private String nomeCliente;
    private LocalDate dataCriacao;
}
