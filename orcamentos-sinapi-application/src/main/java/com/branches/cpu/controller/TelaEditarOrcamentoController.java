package com.branches.cpu.controller;

import com.branches.cpu.model.Orcamento;
import com.branches.cpu.service.OrcamentoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Setter;

public class TelaEditarOrcamentoController {
    @FXML
    private Button btnSalvarFechar;

    @FXML
    private TextField tfNomeCliente;

    @FXML
    private TextField tfNomeOrcamento;

    private Orcamento orcamentoAEditar;

    private final OrcamentoService ORCAMENTO_SERVICE = new OrcamentoService();

    @FXML
    void Fechar(ActionEvent event) {

    }

    @FXML
    void salvarFechar(ActionEvent event) {
        orcamentoAEditar.setNome(tfNomeOrcamento.getText());
        orcamentoAEditar.setNomeCliente(tfNomeCliente.getText());

        ORCAMENTO_SERVICE.update(orcamentoAEditar);
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamentoAEditar = orcamento;

        atualizarCampos();
    }

    private void atualizarCampos() {
        tfNomeOrcamento.setText(orcamentoAEditar.getNome());
        tfNomeCliente.setText(orcamentoAEditar.getNomeCliente());
    }
}
