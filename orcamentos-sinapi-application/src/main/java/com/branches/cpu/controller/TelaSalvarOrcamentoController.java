package com.branches.cpu.controller;

import com.branches.cpu.request.OrcamentoPostRequest;
import com.branches.cpu.service.ItemOrcamentoService;
import com.branches.cpu.service.OrcamentoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaSalvarOrcamentoController {
    @FXML
    private Button btnSalvarFechar;

    @FXML
    private TextField tfNomeOrcamento;

    private OrcamentoService orcamentoService = new OrcamentoService();

    private ItemOrcamentoService itemOrcamentoService = new ItemOrcamentoService();

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void salvarFechar(ActionEvent event) {
        OrcamentoPostRequest orcamentoPostRequest = OrcamentoPostRequest.builder().nome(tfNomeOrcamento.getText()).build();
        orcamentoService.save(orcamentoPostRequest);
        fecharPagina(event);
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
