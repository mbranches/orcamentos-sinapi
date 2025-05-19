package com.branches.response;

import com.branches.model.TipoCliente;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrcamentoPostResponse {
    public record ClienteByOrcamentoPostResponse(Long id, String nome, TipoCliente tipoCliente){}

    private Long id;
    private String descricao;
    private ClienteByOrcamentoPostResponse cliente;
    private Double valorTotal;
}
