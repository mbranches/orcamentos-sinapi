package com.branches.cpu.controller;

import com.branches.cpu.Dao.ServicoDao;
import com.branches.cpu.model.Servico;
import com.branches.cpu.model.ServicoAdicionado;
import com.branches.cpu.utils.TableViewProprieties;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.branches.cpu.utils.Monetary.formatarValorBRL;

public class TelaEditarController implements Initializable {
    @FXML
    private Button btnSalvarFechar;

    @FXML
    private TextField tfMostrarServico;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TableView<Servico> tvMostrarServico;

    @FXML
    private Text txtTotal;

    private TelaOrcamentoController telaPrincipal;

    private ServicoAdicionado servicoAEditar;

    private ObservableList observableList = javafx.collections.FXCollections.observableArrayList();

    @FXML
    void Fechar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarFechar(ActionEvent event) {
        telaPrincipal.atualizarServico(servicoAEditar);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarValorTotal(ActionEvent event) {
        setarValorTotal();

        servicoAEditar.setQuantidade( Integer.valueOf(tfQuantidade.getText()) );
        servicoAEditar.setarValorTotal();
    }

    public void setTelaPrincipal(TelaOrcamentoController telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

    public void setServicoAEditar(ServicoAdicionado servicoAEditar) {
        this.servicoAEditar = servicoAEditar;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarColunasTabela();
        tvMostrarServico.setFixedCellSize(95);
    }

    public void atualizarCampos() {
        tfMostrarServico.setText(servicoAEditar.getDescricao());
        tfQuantidade.setText(String.valueOf(servicoAEditar.getQuantidade()));
        setarItemTableView();
        setarValorTotal();
    }

    private void setarValorTotal() {
        double valorTotal = servicoAEditar.getPreco() * Integer.valueOf(tfQuantidade.getText());
        txtTotal.setText(formatarValorBRL(valorTotal));
    }

    private void setarItemTableView() {
        List<Servico> servicos = ServicoDao.consultarServicos(tfMostrarServico.getText());

        for (Servico servico : servicos) {
            observableList.add(servico);
        }

        tvMostrarServico.setItems(observableList);
    }

    private void criarColunasTabela() {
        TableColumn<Servico, Long> colunaCodigo =new TableColumn<>("Cód.");
        TableColumn<Servico, String> colunaDescricao =new TableColumn<>("Descrição");
        TableColumn<Servico, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<Servico, Double> colunaValor = new TableColumn<>("Valor Unitário");


        colunaCodigo.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.08));
        colunaDescricao.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.67));
        colunaUnidade.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.1));
        colunaValor.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.13));

        tvMostrarServico.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaValor);
        TableViewProprieties.noEditableColumns(tvMostrarServico);

        colunaCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colunaUnidade.setCellValueFactory(new PropertyValueFactory("unidadeMedida"));
        colunaValor.setCellValueFactory(new PropertyValueFactory("preco"));

        colunaValor.setCellFactory(col -> {
            return new TableCell<Servico, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(formatarValorBRL(item));
                    }
                }
            };
        });
    }
}
