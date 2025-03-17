package com.branches.cpu.controller;

import com.branches.cpu.model.Orcamento;
import com.branches.cpu.service.OrcamentoService;
import com.branches.cpu.utils.AbrirFxml;
import com.branches.cpu.utils.TableViewProprieties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TelaOrcamentosController implements Initializable {
    @FXML
    private Button btnAbrir;

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

    private Orcamento orcamentoSelecionado;

    private OrcamentoService orcamentoService = new OrcamentoService();

    private AbrirFxml abrirFxml = new AbrirFxml();

    @FXML
    void abrirOrcamento(ActionEvent event) {
        abrirFxml.abrirTelaOrcamento(orcamentoSelecionado.getNome(), orcamentoSelecionado);
        removerSelecao();
    }

    @FXML
    void abrirTelaEditar(ActionEvent event) {
        abrirFxml.abrirTelaEditarOrcamento("Editar " + orcamentoSelecionado.getNome(), orcamentoSelecionado, this);

        removerSelecao();
    }

    @FXML
    void excluirServico(ActionEvent event) {
        orcamentoService.delete(orcamentoSelecionado);

        removerSelecao();
        atualizarTabela();
    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {

    }

    @FXML
    void selecionarOrcamento(MouseEvent event) {
        orcamentoSelecionado = tvOrcamentos.getSelectionModel().getSelectedItem();

        if (orcamentoSelecionado != null) ativarBotoes();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvOrcamentos.setPlaceholder(new Label("Nenhum orçamento criado até o momento."));
        tvOrcamentos.getPlaceholder().setStyle("-fx-font-size: 15px");

        tvOrcamentos.setFixedCellSize(60);
        tvOrcamentos.setEditable(false);

        criarColunasTabela();

        atualizarTabela();
    }

    public void atualizarTabela() {
        tvOrcamentos.getItems().clear();
        orcamentoService.findAll()
                .forEach(orcamento -> tvOrcamentos.getItems().add(orcamento));
    }

    private void criarColunasTabela() {
        TableColumn<Orcamento, String> colunaNome = new TableColumn<>("Orçamento");
        TableColumn<Orcamento, String> colunaNomeCliente = new TableColumn<>("Cliente");
        TableColumn<Orcamento, LocalDate> colunaDataCriacao = new TableColumn<>("Data de Criação");

        colunaNome.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.5));
        colunaNomeCliente.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.28));
        colunaDataCriacao.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.19));

        tvOrcamentos.getColumns().addAll(colunaNome, colunaNomeCliente, colunaDataCriacao);
        TableViewProprieties.noEditableColumns(tvOrcamentos);

        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaDataCriacao.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
        colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
    }

    private void removerSelecao() {
        tvOrcamentos.getSelectionModel().clearSelection();
        desativarBotoes();
    }

    private void ativarBotoes() {
        btnAbrir.setDisable(false);
        btnEditar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private void desativarBotoes() {
        btnAbrir.setDisable(true);
        btnEditar.setDisable(true);
        btnExcluir.setDisable(true);
    }
}
