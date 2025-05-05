package com.branches.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrcamentoPostRequest {
    private String nome;
    private String nomeCliente;
    private LocalDate dataCriacao;
}
