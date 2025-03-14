package com.branches.cpu.controller;

import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.model.Orcamento;
import com.branches.cpu.request.ItemOrcamentoPostRequest;
import com.branches.cpu.request.OrcamentoPostRequest;
import com.branches.cpu.service.ItemOrcamentoService;
import com.branches.cpu.service.OrcamentoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.List;

@Setter
public class TelaSalvarOrcamentoController {
    @FXML
    private Button btnSalvarFechar;

    @FXML
    private TextField tfNomeOrcamento;

    private List<ItemOrcamento> itemOrcamentoPostRequests;

    private OrcamentoService orcamentoService = new OrcamentoService();

    private ItemOrcamentoService itemOrcamentoService = new ItemOrcamentoService();

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void salvarFechar(ActionEvent event) {
        OrcamentoPostRequest orcamentoPostRequest = OrcamentoPostRequest.builder().nome(tfNomeOrcamento.getText()).build();
        Orcamento orcamento = orcamentoService.save(orcamentoPostRequest);
        itemOrcamentoPostRequests.forEach(item -> item.setOrcamento(orcamento));
        itemOrcamentoService.saveAll(itemOrcamentoPostRequests);
        fecharPagina(event);
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
