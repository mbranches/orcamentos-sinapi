/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.model.Budget;
import com.branches.cpu.service.BudgetItemService;
import com.branches.cpu.service.BudgetService;
import com.branches.cpu.utils.*;
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
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TelaOrcamentoController implements Initializable {

    @FXML
    private ImageView imageEditar;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnSalvar;
    @FXML
    private TableView<BudgetItem> tvServicosAdiconados;
    @FXML
    private Text txtTotal;

    private List<BudgetItem> resultadoBusca = new ArrayList<>();

    private List<BudgetItem> itemsOrcamento = new ArrayList<>();

    private List<BudgetItem> itemsToBeDeleted = new ArrayList<>();

    private AbrirFxml abrirFxml = new AbrirFxml();

    private BudgetItemService budgetItemService = new BudgetItemService();

    private BudgetService budgetService = new BudgetService();

    private double valorTotal = 0;

    private Budget budget;

    private BudgetItem itemSelecionado = null;

    @FXML
    void abrirTelaAdicionar(ActionEvent event) {
        abrirFxml.abrirTelaAdicionar("Adicionar Insumo", this);
    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {
        atualizarTabela();
    }

    @FXML
    void salvarOrcamento(ActionEvent event) {

        if (budget == null) abrirFxml.abrirTelaSalvarOrcamento("Salvar Orçamento", this, this.itemsOrcamento);
        else {
            List<BudgetItem> itensSalvos = budgetItemService.saveAll(itemsOrcamento);
            budgetItemService.deleteAll(itemsToBeDeleted);

            itemsOrcamento.clear();
            itemsOrcamento.addAll(itensSalvos);
            Alerta.informacao(budget.getDescription(), "Orçamento salvo com sucesso!");
        }

        desativarBtnSalvar();
    }

    @FXML
    private void abrirTelaEditar(ActionEvent event) {
        abrirFxml.abrirTelaEditar("Editar Insumo", this, this.itemSelecionado);
        desativarBotoes();
    }

    @FXML
    private void excluirServico(ActionEvent event) {
        if (itemSelecionado.getId() != null) itemsToBeDeleted.add(itemSelecionado);

        itemsOrcamento.remove(itemSelecionado);

        limparBarraPesquisa();

        atualizarTabela();

        desativarBotoes();

        if (budget == null && itemsOrcamento.isEmpty()) desativarBtnSalvar();
        else ativarBtnSalvar();
    }

    @FXML
    void selecionarServicoAdicionado(MouseEvent event) {
        itemSelecionado = tvServicosAdiconados.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) ativarBotoes();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvServicosAdiconados.setPlaceholder(new Label("Nenhum serviço adicionado até o momento."));
        tvServicosAdiconados.getPlaceholder().setStyle("-fx-font-size: 15px");

        tvServicosAdiconados.setFixedCellSize(80);
        tvServicosAdiconados.setEditable(false);

        criarColunasTabela();

        atualizarTabela();
    }

    private void atualizarTabela() {
        tvServicosAdiconados.getItems().clear();
        resultadoBusca.clear();
        String descricao = tfPesquisar.getText();

        if (descricao.isEmpty()) {
            tvServicosAdiconados.getItems().addAll(itemsOrcamento);
        } else {
            resultadoBusca = consultarEmServicosAdicionados(descricao);

            tvServicosAdiconados.getItems().setAll(resultadoBusca);
        }
    }

    private List<BudgetItem> consultarEmServicosAdicionados(String descricao) {
        List<BudgetItem> servicosPesquisados = budgetService.findItemsBySupplyDescription(budget, descricao);

        return servicosPesquisados;
    }

    private void criarColunasTabela() {
        TableColumn<BudgetItem, Long> colunaCodigo = new TableColumn<>("Cód.");
        TableColumn<BudgetItem, String> colunaDescricao = new TableColumn<>("Descrição");
        TableColumn<BudgetItem, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<BudgetItem, Integer> colunaQtd = new TableColumn<>("Qtd.");
        TableColumn<BudgetItem, Double> colunaTotal = new TableColumn<>("Total");

        colunaCodigo.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.05));
        colunaDescricao.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.685));
        colunaUnidade.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.07));
        colunaQtd.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.06));
        colunaTotal.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.11));

        tvServicosAdiconados.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaQtd, colunaTotal);
        TableViewProprieties.noEditableColumns(tvServicosAdiconados);

        colunaCodigo.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getSupply().getCode()).asObject());

        colunaDescricao.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSupply().getDescription()));

        colunaUnidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSupply().getUnitMeasurement()));

        colunaQtd.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colunaTotal.setCellValueFactory(new PropertyValueFactory<>("totalValue"));

        TableColumnConfig.columnFormatoMonetario(colunaTotal);
    }

    public void adicionarServico(BudgetItem budgetItem) {
        budgetItem.setBudget(budget);
        itemsOrcamento.add(budgetItem);

        limparBarraPesquisa();
        atualizarTabela();
    }

    private void ativarBotoes() {
        btnEditar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private void desativarBotoes() {
        tvServicosAdiconados.getSelectionModel().clearSelection();

        btnEditar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    public void atualizarItensTabela() {
        List<BudgetItem> budgetItems = budgetService.findItems(budget);

        itemsOrcamento.clear();
        itemsOrcamento.addAll(budgetItems);

        limparBarraPesquisa();
        atualizarTabela();
        ativarBtnSalvar();
    }

    public void limparBarraPesquisa() {
        if (!tfPesquisar.getText().isEmpty()) tfPesquisar.clear();
    }


    public void setItemsOrcamento(List<BudgetItem> budgetItems) {
        itemsOrcamento.clear();
        itemsOrcamento.addAll(budgetItems);


        atualizarTabela();
    }

    public void setOrcamento(Budget budget) {
        this.budget = budget;

        List<BudgetItem> itensDoOrcamento = budgetService.findItems(budget);

        itemsOrcamento.clear();
        itemsOrcamento.addAll(itensDoOrcamento);

        atualizarTabela();
    }

    public void ativarBtnSalvar() {
        btnSalvar.setDisable(false);
    }

    public void desativarBtnSalvar() {
        btnSalvar.setDisable(true);
    }
}
