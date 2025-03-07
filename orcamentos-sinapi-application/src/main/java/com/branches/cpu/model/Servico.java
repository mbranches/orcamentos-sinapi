package com.branches.cpu.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Branches
 */

@Getter @Setter @NoArgsConstructor
public class Servico {
    private long id;
    private long codigo;
    private String descricao;
    private String unidadeMedida;
    private String origemPreco;
    private double preco;

    @Override
    public String toString() {
        return descricao;
    }
}
