package com.branches.cpu.controller;

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
    private TableView<BudgetItem> tvServicosAdiconados;
    @FXML
    private Text txtTotal;
    private TelaOrcamentosController telaOrcamentos;
    private final AbrirFxml abrirFxml = new AbrirFxml();
    private final BudgetItemService budgetItemService = new BudgetItemService();
    private final BudgetService budgetService = new BudgetService();
    private Budget budget;
    private BudgetItem itemSelecionado = null;

    @FXML
    void abrirTelaAdicionar(ActionEvent event) {
        abrirFxml.abrirTelaAdicionar("Adicionar Insumo", this);
    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {
        carregarBudgetItemsProcurados();
    }

    @FXML
    private void abrirTelaEditar(ActionEvent event) {
        abrirFxml.abrirTelaEditar("Editar Insumo", this, this.itemSelecionado);
        desativarBotoes();
    }

    @FXML
    private void excluirServico(ActionEvent event) {
        limparBarraPesquisa();

        budgetItemService.delete(itemSelecionado);

        carregarTodosBudgetItems();

        desativarBotoes();
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

    private void ativarBotoes() {
        btnEditar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private void desativarBotoes() {
        tvServicosAdiconados.getSelectionModel().clearSelection();

        btnEditar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    public void carregarTodosBudgetItems() {
        List<BudgetItem> budgetItems = budgetService.findItems(budget);

        tvServicosAdiconados.getItems().setAll(budgetItems);

        atualizarBudget();

        limparBarraPesquisa();

        atualizarValorTotalBudget();
    }

    public void carregarBudgetItemsProcurados() {
        String descricao = tfPesquisar.getText();

        List<BudgetItem> foundBudgetItems = budgetService.findItemsBySupplyDescription(budget, descricao);

        atualizarBudget();

        tvServicosAdiconados.getItems().setAll(foundBudgetItems);

        atualizarValorTotalBudget();
    }

    public void limparBarraPesquisa() {
        if (!tfPesquisar.getText().isEmpty()) tfPesquisar.clear();
    }

    public void atualizarValorTotalBudget() {
        Double totalValue = budget.getTotalValue();

        txtTotal.setText(Monetary.formatarValorBRL(totalValue));
    }

    public void atualizarBudget() {
        budget = budgetService.findById(budget.getId());

        if (telaOrcamentos != null) telaOrcamentos.atualizarTabela();
    }

    public void setOrcamento(Budget budget) {
        this.budget = budget;

        carregarTodosBudgetItems();
    }

    public void setTelaOrcamentos(TelaOrcamentosController telaOrcamentos) {
        this.telaOrcamentos = telaOrcamentos;
    }

    public Budget getBudget() {
        return budget;
    }
}
