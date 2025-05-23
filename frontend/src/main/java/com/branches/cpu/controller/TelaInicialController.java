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
    void abrirTelaVisualizarBudgets(ActionEvent event) {
        abrirFxml.abrirTelaVisualizarOrcamentos("Visualizar Orçamentos");
    }

    @FXML
    void abrirTelaVisualizarClients(ActionEvent event) {
        abrirFxml.abrirTelaVisualizarClientes("Visualizar Clientes");
    }

    @FXML
    void abrirTelaCriarClient(ActionEvent event) {
        abrirFxml.abrirTelaCriarCliente("Cadastrar Cliente");
    }
}
