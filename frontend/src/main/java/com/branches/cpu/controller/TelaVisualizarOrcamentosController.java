package com.branches.cpu.controller;

import com.branches.cpu.model.Budget;
import com.branches.cpu.model.Client;
import com.branches.cpu.service.BudgetService;
import com.branches.cpu.utils.AbrirFxmlUtils;
import com.branches.cpu.components.Alerta;
import com.branches.cpu.utils.TableColumnUtils;
import com.branches.cpu.utils.TableViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaVisualizarOrcamentosController implements Initializable {
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
    private TableView<Budget> tvOrcamentos;
    private Budget budgetSelecionado;
    private final BudgetService budgetService = new BudgetService();
    private final AbrirFxmlUtils abrirFxmlUtils = new AbrirFxmlUtils();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvOrcamentos.setFixedCellSize(60);
        tvOrcamentos.setEditable(false);
        TableViewUtils.noEditableColumns(tvOrcamentos);

        criarColunasTabela();

        carregarOrcamentos();
    }

    @FXML
    void abrirOrcamento(ActionEvent event) {
        abrirFxmlUtils.abrirTelaOrcamento(budgetSelecionado.getDescription(), budgetSelecionado, this);
        removerSelecao();
    }

    @FXML
    void abrirTelaEditar(ActionEvent event) {
        abrirFxmlUtils.abrirTelaEditarOrcamento("Editar orçamento", budgetSelecionado, this);

        removerSelecao();
    }

    @FXML
    void excluirOrcamento(ActionEvent event) {
        if (Alerta.confirmarExclusao("Orçamento", budgetSelecionado.getDescription())) {
            budgetService.delete(budgetSelecionado);

            Alerta.informacao("Sucesso!", "orçamento excluído com sucesso.");

            carregarOrcamentos();
        }

        removerSelecao();
    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {
        String orcamentoBuscado = tfPesquisar.getText();

        List<Budget> resultadoDaBusca = budgetService.findAllByName(orcamentoBuscado);

        if (resultadoDaBusca.isEmpty()) addPlaceholderNotFoundBudgets();

        tvOrcamentos.getItems().setAll(resultadoDaBusca);
    }

    @FXML
    void selecionarOrcamento(MouseEvent event) {
        budgetSelecionado = tvOrcamentos.getSelectionModel().getSelectedItem();

        if (budgetSelecionado != null) ativarBotoes();
    }

    public void carregarOrcamentos() {
        List<Budget> budgets = budgetService.findAll();

        if (budgets.isEmpty()) addPlaceholderNotCreatedBudgets();

        tvOrcamentos.getItems().setAll(budgets);
        clearSearchBar();
    }

    private void criarColunasTabela() {
        TableColumn<Budget, String> colunaDescription = new TableColumn<>("Orçamento");
        TableColumn<Budget, Client> colunaClient = new TableColumn<>("Cliente");
        TableColumn<Budget, Double> colunaTotalValue = new TableColumn<>("Valor Total");

        colunaDescription.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.5));
        colunaClient.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.28));
        colunaTotalValue.prefWidthProperty().bind(tvOrcamentos.widthProperty().multiply(0.19));

        colunaDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colunaDescription.setSortable(false);
        colunaClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        colunaClient.setSortable(false);
        colunaTotalValue.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
        colunaTotalValue.setSortable(false);

        tvOrcamentos.getColumns().addAll(colunaDescription, colunaClient, colunaTotalValue);

        TableViewUtils.noEditableColumns(tvOrcamentos);
        TableColumnUtils.columnFormatoMonetario(colunaTotalValue);
    }

    void clearSearchBar() {
        tfPesquisar.clear();
    }

    void addPlaceholderNotCreatedBudgets() {
        tvOrcamentos.setPlaceholder(new Label("Nenhum orçamento criado até o momento."));
        tvOrcamentos.getPlaceholder().setStyle("-fx-font-size: 15px");
    }

    void addPlaceholderNotFoundBudgets() {
        tvOrcamentos.setPlaceholder(new Label("Nenhum orçamento encontrado."));
        tvOrcamentos.getPlaceholder().setStyle("-fx-font-size: 15px");
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
