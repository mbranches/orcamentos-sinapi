package com.branches.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrcamentoPostResponse {
    private Long id;
    private String nome;
    private String nomeCliente;
    private LocalDate dataCriacao;
}
