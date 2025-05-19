package com.branches.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrcamentoPostRequest {
    private String descricao;
    private Long idCliente;
}