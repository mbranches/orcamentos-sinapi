package com.branches.response;

import com.branches.model.TipoCliente;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientePostResponse {
    private Long id;
    private String nome;
    private TipoCliente tipoCliente;
}
