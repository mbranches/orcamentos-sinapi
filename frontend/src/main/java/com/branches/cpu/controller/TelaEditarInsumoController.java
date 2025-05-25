package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.Supply;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.service.BudgetService;
import com.branches.cpu.service.SupplyService;
import com.branches.cpu.utils.NumberUtils;
import com.branches.cpu.utils.TableColumnUtils;
import com.branches.cpu.utils.TableViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.branches.cpu.utils.NumberUtils.formatarValorBRL;

public class TelaEditarInsumoController implements Initializable {
    @FXML
    private Button btnSalvarFechar;
    @FXML
    private TextField tfMostrarInsumo;
    @FXML
    private TextField tfQuantidade;
    @FXML
    private TableView<Supply> tvMostrarInsumo;
    @FXML
    private Text txtTotal;
    private TelaOrcamentoController telaPrincipal;
    private BudgetItem insumoAEditar;
    private final BudgetService budgetService = new BudgetService();
    private final SupplyService service = new SupplyService();

    @FXML
    void Fechar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarFechar(ActionEvent event) {
        String quantidadeString = tfQuantidade.getText();
        if (!NumberUtils.isValidNumber(quantidadeString) || Double.parseDouble(quantidadeString) == 0) {
            Alerta.error("Quantidade inválida!", "Digite uma quantidade válida");
            return;
        }

        budgetService.updateBudgetItem(insumoAEditar);

        telaPrincipal.carregarTodosBudgetItems();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarValorTotal(KeyEvent event) {
        if (NumberUtils.isValidNumber(tfQuantidade.getText())) {
            setarValorTotal();
            insumoAEditar.setQuantity(Integer.parseInt(tfQuantidade.getText()));
        } else {
            txtTotal.setText(formatarValorBRL(0));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarColunasTabela();
        tvMostrarInsumo.setFixedCellSize(95);
    }

    public void atualizarCampos() {
        tfMostrarInsumo.setText(insumoAEditar.getSupply().getDescription());
        tfQuantidade.setText(String.valueOf(insumoAEditar.getQuantity()));
        setarItemTableView();
        setarValorTotal();
    }

    private void setarValorTotal() {
        double preco = insumoAEditar.getSupply().getPrice();
        double quantidade = Double.parseDouble(tfQuantidade.getText());
        double valorTotal = preco * quantidade;
        txtTotal.setText(formatarValorBRL(valorTotal));
    }

    private void setarItemTableView() {
        ObservableList<Supply> observableList = tvMostrarInsumo.getItems();
        List<Supply> insumos = service.findByName(tfMostrarInsumo.getText());

        observableList.addAll(insumos);

        tvMostrarInsumo.setItems(observableList);
    }

    private void criarColunasTabela() {
        TableColumn<Supply, Long> colunaCode = new TableColumn<>("Cód.");
        TableColumn<Supply, String> colunaDescription = new TableColumn<>("Descrição");
        TableColumn<Supply, String> colunaUnitMeasurement = new TableColumn<>("Unidade");
        TableColumn<Supply, Double> colunaPrice = new TableColumn<>("Valor Unitário");


        colunaCode.prefWidthProperty().bind(tvMostrarInsumo.widthProperty().multiply(0.08));
        colunaDescription.prefWidthProperty().bind(tvMostrarInsumo.widthProperty().multiply(0.67));
        colunaUnitMeasurement.prefWidthProperty().bind(tvMostrarInsumo.widthProperty().multiply(0.1));
        colunaPrice.prefWidthProperty().bind(tvMostrarInsumo.widthProperty().multiply(0.13));

        tvMostrarInsumo.getColumns().addAll(colunaCode, colunaDescription, colunaUnitMeasurement, colunaPrice);
        TableViewUtils.noEditableColumns(tvMostrarInsumo);

        colunaCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colunaDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colunaUnitMeasurement.setCellValueFactory(new PropertyValueFactory<>("unitMeasurement"));
        colunaPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumnUtils.columnFormatoMonetario(colunaPrice);
    }

    public void setTelaPrincipal(TelaOrcamentoController telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

    public void setInsumoAEditar(BudgetItem insumoAEditar) {
        this.insumoAEditar = insumoAEditar;
    }
}
