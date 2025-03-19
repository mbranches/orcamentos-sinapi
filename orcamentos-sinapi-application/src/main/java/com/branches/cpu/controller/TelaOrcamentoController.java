/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.branches.cpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.model.Orcamento;
import com.branches.cpu.service.ItemOrcamentoService;
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
import lombok.Setter;

/**
 *
 * @author Branches
 */
@Setter
public class TelaOrcamentoController implements Initializable{

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
    private TableView<ItemOrcamento> tvServicosAdiconados;
    @FXML
    private Text txtTotal;

    private List<ItemOrcamento> resultadoBusca = new ArrayList<>();

    private List<ItemOrcamento> itemsOrcamento = new ArrayList<>();

    private List<ItemOrcamento> itemsToBeDeleted = new ArrayList<>();

    private AbrirFxml abrirFxml = new AbrirFxml();

    ItemOrcamentoService itemOrcamentoService = new ItemOrcamentoService();

    private double valorTotal = 0;

    private Orcamento orcamento;

    private ItemOrcamento itemSelecionado = null;

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

        if (orcamento == null) abrirFxml.abrirTelaSalvarOrcamento("Salvar Orçamento", this, this.itemsOrcamento);
        else {
            List<ItemOrcamento> itensSalvos = itemOrcamentoService.saveAll(itemsOrcamento);
            itemOrcamentoService.deleteAll(itemsToBeDeleted);

            itemsOrcamento.clear();
            itemsOrcamento.addAll(itensSalvos);
            Alerta.informacao(orcamento.getNome(), "Orçamento salvo com sucesso!");
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
        atualizarValorTotal();

        desativarBotoes();

        if (orcamento == null && itemsOrcamento.isEmpty()) desativarBtnSalvar();
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

            if (resultadoBusca.isEmpty()) {
                tvServicosAdiconados.getItems().clear();
            } else {
                tvServicosAdiconados.getItems().setAll(resultadoBusca);
            }
        }
    }

    private List<ItemOrcamento> consultarEmServicosAdicionados(String descricao) {
        List<ItemOrcamento> servicosPesquisados= Lists.containsInList(itemsOrcamento, s -> s.getInsumo().getDescricao().toLowerCase().contains(descricao.toLowerCase()));

        return servicosPesquisados;
    }

    private void atualizarValorTotal() {
        valorTotal = 0;
        for (ItemOrcamento itemOrcamento : tvServicosAdiconados.getItems()) {
            valorTotal += itemOrcamento.getValorTotal();
        }

        txtTotal.setText(Monetary.formatarValorBRL(valorTotal));
    }

    private void criarColunasTabela() {
        TableColumn<ItemOrcamento, Long> colunaCodigo =new TableColumn<>("Cód.");
        TableColumn<ItemOrcamento, String> colunaDescricao =new TableColumn<>("Descrição");
        TableColumn<ItemOrcamento, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<ItemOrcamento, Integer> colunaQtd = new TableColumn<>("Qtd.");
        TableColumn<ItemOrcamento, Double> colunaTotal = new TableColumn<>("Total");

        colunaCodigo.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.05));
        colunaDescricao.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.685));
        colunaUnidade.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.07));
        colunaQtd.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.06));
        colunaTotal.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.11));

        tvServicosAdiconados.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaQtd, colunaTotal);
        TableViewProprieties.noEditableColumns(tvServicosAdiconados);

        colunaCodigo.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getInsumo().getCodigo()).asObject());

        colunaDescricao.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInsumo().getDescricao()));

        colunaUnidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInsumo().getUnidadeMedida()));

        colunaQtd.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunaTotal.setCellValueFactory(new PropertyValueFactory("valorTotal"));

        TableColumnConfig.columnFormatoMonetario(colunaTotal);
    }

    public void adicionarServico(ItemOrcamento itemOrcamento) {
        itemOrcamento.setOrcamento(orcamento);
        itemsOrcamento.add(itemOrcamento);

        limparBarraPesquisa();
        atualizarTabela();
        atualizarValorTotal();
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

    public void atualizarServico(ItemOrcamento servicoNovo) {
        for (ItemOrcamento servico : itemsOrcamento) {
            if (servico.equals(servicoNovo)) {
                servico.setQuantidade(servicoNovo.getQuantidade());
                servico.setarValorTotal();
            }
        }

        limparBarraPesquisa();
        atualizarTabela();
        atualizarValorTotal();
        ativarBtnSalvar();
    }

    public void limparBarraPesquisa() {
        if (!tfPesquisar.getText().isEmpty()) tfPesquisar.clear();
    }


    public void setItemsOrcamento(List<ItemOrcamento> itemOrcamentos) {
        itemsOrcamento.clear();
        itemsOrcamento.addAll(itemOrcamentos);

        itemsOrcamento.forEach(ItemOrcamento::setarValorTotal);

        atualizarTabela();
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;

        List<ItemOrcamento> itensDoOrcamento = itemOrcamentoService.findByOrcamento(orcamento);
        itensDoOrcamento.forEach(ItemOrcamento::setarValorTotal);

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
