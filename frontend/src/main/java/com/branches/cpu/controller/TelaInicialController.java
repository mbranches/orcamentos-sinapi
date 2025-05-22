package com.branches.cpu.controller;

import com.branches.cpu.utils.AbrirFxml;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class TelaInicialController {
    private final AbrirFxml abrirFxml = new AbrirFxml();
    @FXML
    void abrirTelaCriarOrcamento(ActionEvent event) {
        abrirFxml.abrirTelaOrcamento("Criar Orçamento", null, null);
    }

    @FXML
    void abrirTelaOrcamentos(ActionEvent event) {
        abrirFxml.abrirTelaOrcamentos("Orçamentos");
    }
}
