package com.branches.cpu.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class OrcamentoPostRequest {
    private String nome;
    private String nomeCliente;
}
