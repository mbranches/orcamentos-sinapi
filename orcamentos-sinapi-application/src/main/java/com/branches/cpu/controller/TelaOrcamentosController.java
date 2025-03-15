package com.branches.cpu.controller;

import com.branches.cpu.model.Orcamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class TelaOrcamentosController {
    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    @FXML
    private ImageView imageEditar;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableView<Orcamento> tvOrcamentos;

    @FXML
    void abrirTelaAdicionar(ActionEvent event) {

    }

    @FXML
    void abrirTelaEditar(ActionEvent event) {

    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {

    }

    @FXML
    void excluirServico(ActionEvent event) {

    }

    @FXML
    void selecionarServicoAdicionado(MouseEvent event) {

    }
}
