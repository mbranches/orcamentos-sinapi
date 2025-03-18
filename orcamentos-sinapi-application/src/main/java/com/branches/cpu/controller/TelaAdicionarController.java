package com.branches.cpu.controller;

import com.branches.cpu.model.Insumo;
import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.service.InsumoService;
import com.branches.cpu.components.AutoCompleteTextField;
import com.branches.cpu.utils.Monetary;
import com.branches.cpu.utils.TableColumnConfig;
import com.branches.cpu.utils.TableViewProprieties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Branches
 */
@Setter
public class TelaAdicionarController implements Initializable {
    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnAdicionarFechar;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private Text txtTotal;

    @FXML
    private TableView<Insumo> tvMostrarServico;

    @FXML
    private VBox sugestions;

    AutoCompleteTextField autoCompletePesquisar = new AutoCompleteTextField();

    private Insumo servicoSelecionado;

    List<Insumo> resultadoBusca = new ArrayList<>();

    private TelaOrcamentoController telaPrincipal;

    private InsumoService service = new InsumoService();

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void PesquisarServico(ActionEvent event) {
        atualizarTabela();
        tfQuantidade.disableProperty().setValue(false);
    }

    @FXML
    void adicionarFechar(ActionEvent event) {
        if (!validarCampoQuantidade()) return;
        adicionar();
        fecharPagina(event);
    }

    @FXML
    void adicionarServico(ActionEvent event) {
        if (!validarCampoQuantidade()) return;
        adicionar();
        tfQuantidade.setDisable(true);
        limparCampos();
    }



    @FXML
    void setarValorTotal(KeyEvent event) {
        if (tfQuantidade.getText() != null) {
            double valorTotal = resultadoBusca.get(0).getPreco() * Integer.parseInt(tfQuantidade.getText());
            txtTotal.setText(Monetary.formatarValorBRL(valorTotal));
        } else {
            txtTotal.setText(Monetary.formatarValorBRL(0));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        criarColunasTabela();

        tvMostrarServico.setFixedCellSize(95);
        tvMostrarServico.setPlaceholder(new Label("Nenhum serviço selecionado até o momento."));
        tvMostrarServico.getPlaceholder().setStyle("-fx-font-size: 15px");

        autoCompletePesquisar.getEntries().addAll(
                service.findAll()
                        .stream()
                        .map(Insumo::getDescricao)
                        .collect(Collectors.toList())
        );
        autoCompletePesquisar.setPrefHeight(30);
        autoCompletePesquisar.setOnAction(this::PesquisarServico);
        sugestions.getChildren().add(autoCompletePesquisar);
    }

    private void atualizarTabela() {
        tvMostrarServico.getItems().clear();
        resultadoBusca = service.findByName(autoCompletePesquisar.getText());
        servicoSelecionado = resultadoBusca.get(0);

        tvMostrarServico.getItems().add(servicoSelecionado);
    }

    private void criarColunasTabela() {
        TableColumn<Insumo, Long> colunaCodigo =new TableColumn<>("Cód.");
        TableColumn<Insumo, String> colunaDescricao =new TableColumn<>("Descrição");
        TableColumn<Insumo, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<Insumo, Double> colunaValor = new TableColumn<>("Valor Unitário");


        colunaCodigo.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.08));
        colunaDescricao.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.67));
        colunaUnidade.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.1));
        colunaValor.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.13));

        tvMostrarServico.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaValor);
        TableViewProprieties.noEditableColumns(tvMostrarServico);

        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaUnidade.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumnConfig.columnFomatoMonetario(colunaValor);
    }

    private void limparCampos() {
        autoCompletePesquisar.clear();
        tfQuantidade.clear();
        txtTotal.setText("R$ 0,00");
        tvMostrarServico.getItems().clear();
    }

    private void adicionar() {
        ItemOrcamento itemOrcamento = new ItemOrcamento();
        itemOrcamento.setInsumo(servicoSelecionado);
        itemOrcamento.setQuantidade(Integer.parseInt(tfQuantidade.getText()));
        itemOrcamento.setarValorTotal();

        telaPrincipal.adicionarServico(itemOrcamento);
        telaPrincipal.ativarBtnSalvar();
    }

    private boolean validarCampoQuantidade() {
        String quantidade = tfQuantidade.getText();

        if (quantidade.isEmpty()) {
            Alerta.error("\"Quantidade\" é um campo obrigatório.");
            return false;
        }
        if (!Validador.isValidNumber(quantidade)) {
            Alerta.error("\"Quantidade\" só aceita números.");
            return false;
        }
        if (Double.parseDouble(quantidade) == 0) {
            Alerta.error("Digite uma quantidade válida.");
            return false;
        }
        return true;
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
