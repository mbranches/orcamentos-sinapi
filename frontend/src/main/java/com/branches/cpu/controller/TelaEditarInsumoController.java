package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.Supply;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.service.BudgetItemService;
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
    private TextField tfMostrarServico;
    @FXML
    private TextField tfQuantidade;
    @FXML
    private TableView<Supply> tvMostrarServico;
    @FXML
    private Text txtTotal;
    private TelaOrcamentoController telaPrincipal;
    private BudgetItem servicoAEditar;
    private final BudgetItemService budgetItemService = new BudgetItemService();
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

        budgetItemService.update(servicoAEditar);

        telaPrincipal.carregarTodosBudgetItems();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarValorTotal(KeyEvent event) {
        if (NumberUtils.isValidNumber(tfQuantidade.getText())) {
            setarValorTotal();
            servicoAEditar.setQuantity(Integer.parseInt(tfQuantidade.getText()));
        } else {
            txtTotal.setText(formatarValorBRL(0));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarColunasTabela();
        tvMostrarServico.setFixedCellSize(95);
    }

    public void atualizarCampos() {
        tfMostrarServico.setText(servicoAEditar.getSupply().getDescription());
        tfQuantidade.setText(String.valueOf(servicoAEditar.getQuantity()));
        setarItemTableView();
        setarValorTotal();
    }

    private void setarValorTotal() {
        double preco = servicoAEditar.getSupply().getPrice();
        double quantidade = Double.parseDouble(tfQuantidade.getText());
        double valorTotal = preco * quantidade;
        txtTotal.setText(formatarValorBRL(valorTotal));
    }

    private void setarItemTableView() {
        ObservableList<Supply> observableList = tvMostrarServico.getItems();
        List<Supply> servicos = service.findByName(tfMostrarServico.getText());

        observableList.addAll(servicos);

        tvMostrarServico.setItems(observableList);
    }

    private void criarColunasTabela() {
        TableColumn<Supply, Long> colunaCode = new TableColumn<>("Cód.");
        TableColumn<Supply, String> colunaDescription = new TableColumn<>("Descrição");
        TableColumn<Supply, String> colunaUnitMeasurement = new TableColumn<>("Unidade");
        TableColumn<Supply, Double> colunaPrice = new TableColumn<>("Valor Unitário");


        colunaCode.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.08));
        colunaDescription.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.67));
        colunaUnitMeasurement.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.1));
        colunaPrice.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.13));

        tvMostrarServico.getColumns().addAll(colunaCode, colunaDescription, colunaUnitMeasurement, colunaPrice);
        TableViewUtils.noEditableColumns(tvMostrarServico);

        colunaCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colunaDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colunaUnitMeasurement.setCellValueFactory(new PropertyValueFactory<>("unitMeasurement"));
        colunaPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumnUtils.columnFormatoMonetario(colunaPrice);
    }

    public void setTelaPrincipal(TelaOrcamentoController telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

    public void setServicoAEditar(BudgetItem servicoAEditar) {
        this.servicoAEditar = servicoAEditar;
    }
}
