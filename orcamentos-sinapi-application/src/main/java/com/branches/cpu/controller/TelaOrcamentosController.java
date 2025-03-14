package com.branches.cpu.controller;

import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.model.Orcamento;
import com.branches.cpu.service.OrcamentoService;
import com.branches.cpu.utils.TableColumnConfig;
import com.branches.cpu.utils.TableViewProprieties;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
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

    private OrcamentoService orcamentoService = new OrcamentoService();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvOrcamentos.setPlaceholder(new Label("Nenhum orçamento criado até o momento."));
        tvOrcamentos.getPlaceholder().setStyle("-fx-font-size: 15px");

        tvOrcamentos.setFixedCellSize(40);
        tvOrcamentos.setEditable(false);

        criarColunasTabela();

        atualizarTabela();
    }

    private void atualizarTabela() {
        orcamentoService.findAll().forEach(orcamento -> tvOrcamentos.getItems().add(orcamento));
    }

    private void criarColunasTabela() {
        TableColumn<Orcamento, String> colunaNome = new TableColumn<>("Orçamento");
        TableColumn<Orcamento, String> colunaNomeCliente = new TableColumn<>("Cliente");
        TableColumn<Orcamento, LocalDate> colunaDataCriacao = new TableColumn<>("Data de Criação");

        colunaNome.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.5));
        colunaNomeCliente.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.28));
        colunaDataCriacao.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.2));

        tvOrcamentos.getColumns().addAll(colunaNome, colunaNomeCliente, colunaDataCriacao);
        TableViewProprieties.noEditableColumns(tvOrcamentos);

        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaDataCriacao.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
        colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
    }
}
