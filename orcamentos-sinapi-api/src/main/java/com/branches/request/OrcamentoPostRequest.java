package com.branches.request;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class OrcamentoPostRequest {
    private String nome;
    private String nomeCliente;
}