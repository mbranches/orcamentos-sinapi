package com.branches.cpu.controller;

import com.branches.cpu.utils.AbrirFxml;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TelaInicialController {
    private final AbrirFxml abrirFxml = new AbrirFxml();
    @FXML
    void abrirTelaCriarOrcamento(ActionEvent event) {
        abrirFxml.abrirFxml("tela-orcamento", "Criar Orçamento", 900, 600, true, null, null);
    }

    @FXML
    void abrirTelaOrcamentos(ActionEvent event) {
        abrirFxml.abrirFxml("tela-orcamentos", "Orçamentos", 900, 600, true, null, null);
    }
}
