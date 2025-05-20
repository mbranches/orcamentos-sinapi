package com.branches.cpu.utils;

import com.branches.cpu.controller.*;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.model.Budget;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class AbrirFxml {
    private final String PATH = "/com/branches/cpu/fxml/";

    private void abrirFxml(Parent root, String title, Integer minWidth, Integer minHeight, Boolean resizable) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
            stage.centerOnScreen();
            stage.setResizable(resizable);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            System.out.println("Não foi possível abrir a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaOrcamento(String title, Budget budget){
        String fileName = "tela-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            if (budget != null) {
                TelaOrcamentoController controller = loader.getController();
                controller.setOrcamento(budget);
            }

            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaOrcamentos(String title){
        String fileName = "tela-orcamentos";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));

            Parent root = loader.load();
            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }


    public void abrirTelaAdicionar(String titulo, TelaOrcamentoController telaOrcamentoController) {
        String fileName = "tela-adicionar";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaAdicionarController telaAdicionarController = loader.getController();
            telaAdicionarController.setTelaPrincipal(telaOrcamentoController);

            abrirFxml(root, titulo, 720, 400, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaEditar(String titulo, TelaOrcamentoController telaOrcamentoController, BudgetItem itemAEditar) {
        String fileName = "tela-editar";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaEditarController telaEditarController = loader.getController();
            telaEditarController.setTelaPrincipal(telaOrcamentoController);
            telaEditarController.setServicoAEditar(itemAEditar);
            telaEditarController.atualizarCampos();

            abrirFxml(root, titulo, 720, 400, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaSalvarOrcamento(String titulo, TelaOrcamentoController telaOrcamentoController, List<BudgetItem> itemsASalvar) {
        String fileName = "tela-salvar-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaSalvarOrcamentoController controller = loader.getController();
            controller.setOrcamentoController(telaOrcamentoController);
            controller.setItemOrcamentoPostRequests(itemsASalvar);

            abrirFxml(root, titulo, 660, 320, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaEditarOrcamento(String titulo, Budget budgetAEditar, TelaOrcamentosController telaOrcamentosController) {
        String fileName = "tela-editar-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaEditarOrcamentoController controller = loader.getController();
            controller.setOrcamento(budgetAEditar);
            controller.setTelaOrcamentosController(telaOrcamentosController);

            abrirFxml(root, titulo, 660, 320, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }
}
