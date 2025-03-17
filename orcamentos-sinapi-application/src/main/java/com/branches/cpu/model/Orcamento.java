package com.branches.cpu.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Orcamento {
    private Long id;
    private String nome;
    private String nomeCliente;
    private LocalDate dataCriacao;
}
