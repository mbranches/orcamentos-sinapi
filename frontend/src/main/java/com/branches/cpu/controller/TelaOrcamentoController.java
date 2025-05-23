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
    private TableView<BudgetItem> tvBudgetItemsAdicionados;
    @FXML
    private Text txtTotal;
    private TelaVisualizarOrcamentosController telaVisualizarOrcamentos;
    private final AbrirFxmlUtils abrirFxmlUtils = new AbrirFxmlUtils();
    private final BudgetItemService budgetItemService = new BudgetItemService();
    private final BudgetService budgetService = new BudgetService();
    private Budget budget;
    private BudgetItem itemSelecionado = null;

    @FXML
    void abrirTelaAdicionar(ActionEvent event) {
        abrirFxmlUtils.abrirTelaAdicionar("Adicionar Insumo", this);
    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {
        carregarBudgetItemsProcurados();
    }

    @FXML
    private void abrirTelaEditar(ActionEvent event) {
        abrirFxmlUtils.abrirTelaEditar("Editar Insumo", this, this.itemSelecionado);
        desativarBotoes();
    }

    @FXML
    private void excluirBudgetItem(ActionEvent event) {
        limparBarraPesquisa();

        budgetItemService.delete(itemSelecionado);

        carregarTodosBudgetItems();

        desativarBotoes();
    }

    @FXML
    void selecionarBudgetItem(MouseEvent event) {
        itemSelecionado = tvBudgetItemsAdicionados.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) ativarBotoes();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvBudgetItemsAdicionados.getPlaceholder().setStyle("-fx-font-size: 15px");

        tvBudgetItemsAdicionados.setFixedCellSize(80);
        tvBudgetItemsAdicionados.setEditable(false);

        criarColunasTabela();
    }

    private void criarColunasTabela() {
        TableColumn<BudgetItem, Long> colunaCodigo = new TableColumn<>("Cód.");
        TableColumn<BudgetItem, String> colunaDescricao = new TableColumn<>("Descrição");
        TableColumn<BudgetItem, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<BudgetItem, Integer> colunaQtd = new TableColumn<>("Qtd.");
        TableColumn<BudgetItem, Double> colunaTotal = new TableColumn<>("Total");

        colunaCodigo.prefWidthProperty().bind(tvBudgetItemsAdicionados.widthProperty().multiply(0.05));
        colunaDescricao.prefWidthProperty().bind(tvBudgetItemsAdicionados.widthProperty().multiply(0.685));
        colunaUnidade.prefWidthProperty().bind(tvBudgetItemsAdicionados.widthProperty().multiply(0.07));
        colunaQtd.prefWidthProperty().bind(tvBudgetItemsAdicionados.widthProperty().multiply(0.06));
        colunaTotal.prefWidthProperty().bind(tvBudgetItemsAdicionados.widthProperty().multiply(0.11));

        tvBudgetItemsAdicionados.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaQtd, colunaTotal);
        TableViewUtils.noEditableColumns(tvBudgetItemsAdicionados);

        colunaCodigo.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getSupply().getCode()).asObject());

        colunaDescricao.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSupply().getDescription()));

        colunaUnidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSupply().getUnitMeasurement()));

        colunaQtd.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colunaTotal.setCellValueFactory(new PropertyValueFactory<>("totalValue"));

        TableColumnUtils.columnFormatoMonetario(colunaTotal);
    }

    private void ativarBotoes() {
        btnEditar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private void desativarBotoes() {
        tvBudgetItemsAdicionados.getSelectionModel().clearSelection();

        btnEditar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    public void carregarTodosBudgetItems() {
        List<BudgetItem> budgetItems = budgetService.findItems(budget);
        if (budgetItems.isEmpty()) tvBudgetItemsAdicionados.setPlaceholder(new Label("Nenhum insumo adicionado até o momento."));

        tvBudgetItemsAdicionados.getItems().setAll(budgetItems);

        atualizarBudget();

        limparBarraPesquisa();

        atualizarValorTotalBudget();
    }

    public void carregarBudgetItemsProcurados() {
        String descricao = tfPesquisar.getText();

        List<BudgetItem> foundBudgetItems = budgetService.findItemsBySupplyDescription(budget, descricao);
        if (foundBudgetItems.isEmpty()) tvBudgetItemsAdicionados.setPlaceholder(new Label("Nenhum insumo encontrado."));

        atualizarBudget();

        tvBudgetItemsAdicionados.getItems().setAll(foundBudgetItems);

        atualizarValorTotalBudget();
    }

    public void limparBarraPesquisa() {
        tfPesquisar.clear();
    }

    public void atualizarValorTotalBudget() {
        Double totalValue = budget.getTotalValue();

        txtTotal.setText(NumberUtils.formatarValorBRL(totalValue));
    }

    public void atualizarBudget() {
        budget = budgetService.findById(budget.getId());

        if (telaVisualizarOrcamentos != null) telaVisualizarOrcamentos.carregarOrcamentos();
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public void setTelaVisualizarOrcamentos(TelaVisualizarOrcamentosController telaOrcamentos) {
        this.telaVisualizarOrcamentos = telaOrcamentos;
    }

    public Budget getBudget() {
        return budget;
    }
}
