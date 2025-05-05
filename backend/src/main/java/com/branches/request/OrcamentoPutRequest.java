package com.branches.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrcamentoPutRequest {
    private Long id;
    private String nome;
    private String nomeCliente;
    private LocalDate dataCriacao;
}
