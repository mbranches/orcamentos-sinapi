package com.branches.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InsumoGetResponse {
    private Long id;
    private Long codigo;
    private String descricao;
    private String unidadeMedida;
    private String origemPreco;
    private Double preco;
}
