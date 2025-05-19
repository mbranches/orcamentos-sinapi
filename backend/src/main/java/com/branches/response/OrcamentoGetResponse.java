package com.branches.response;

import com.branches.model.TipoCliente;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrcamentoGetResponse {
    public record ClienteByOrcamentoGetResponse(Long id, String nome, TipoCliente tipoCliente){}

    private Long id;
    private String descricao;
    private ClienteByOrcamentoGetResponse cliente;
    private Double valorTotal;
}
