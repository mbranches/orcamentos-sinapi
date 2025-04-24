package com.branches.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsumoPostResponse {
    private Long id;
    private Long codigo;
    private String descricao;
    private String unidadeMedida;
    private String origemPreco;
    private Double preco;
}
