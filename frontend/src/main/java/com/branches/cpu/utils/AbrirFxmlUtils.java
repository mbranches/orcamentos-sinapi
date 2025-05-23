package com.branches.cpu.utils;

import com.branches.cpu.controller.*;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.model.Budget;
import com.branches.cpu.model.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AbrirFxmlUtils {
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

    public void abrirTelaOrcamento(String title, Budget budget, TelaVisualizarOrcamentosController telaOrcamentos){
        String fileName = "tela-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            if (budget != null) {
                TelaOrcamentoController controller = loader.getController();
                controller.setBudget(budget);
                controller.carregarTodosBudgetItems();
                controller.setTelaVisualizarOrcamentos(telaOrcamentos);
            }

            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
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
                controller.setBudget(budget);
                controller.carregarTodosBudgetItems();
            }

            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaCriarOrcamento(String title) {
        String fileName = "tela-criar-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            abrirFxml(root, title, 660, 320, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaVisualizarOrcamentos(String title){
        String fileName = "tela-visualizar-orcamentos";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));

            Parent root = loader.load();
            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }


    public void abrirTelaAdicionar(String tite, TelaOrcamentoController telaOrcamentoController) {
        String fileName = "tela-adicionar-insumo";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaAdicionarInsumoController telaAdicionarInsumoController = loader.getController();
            telaAdicionarInsumoController.setTelaPrincipal(telaOrcamentoController);

            abrirFxml(root, tite, 720, 400, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaEditar(String tite, TelaOrcamentoController telaOrcamentoController, BudgetItem itemAEditar) {
        String fileName = "tela-editar-insumo";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaEditarInsumoController telaEditarInsumoController = loader.getController();
            telaEditarInsumoController.setTelaPrincipal(telaOrcamentoController);
            telaEditarInsumoController.setInsumoAEditar(itemAEditar);
            telaEditarInsumoController.atualizarCampos();

            abrirFxml(root, tite, 720, 400, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaEditarOrcamento(String tite, Budget budgetAEditar, TelaVisualizarOrcamentosController telaVisualizarOrcamentosController) {
        String fileName = "tela-editar-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaEditarOrcamentoController controller = loader.getController();
            controller.setBudget(budgetAEditar);
            controller.setTelaVisualizarOrcamentosController(telaVisualizarOrcamentosController);

            abrirFxml(root, tite, 660, 320, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaVisualizarClientes(String title){
        String fileName = "tela-visualizar-clientes";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));

            Parent root = loader.load();
            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaEditarCliente(String title, TelaVisualizarClientesController telaVisualizarClientesController, Client clientToEdit) {
        String fileName = "tela-editar-cliente";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaEditarClienteController telaEditarClienteController = loader.getController();
            telaEditarClienteController.setTelaVisualizarClientesController(telaVisualizarClientesController);
            telaEditarClienteController.setClientToEdit(clientToEdit);
            telaEditarClienteController.atualizarCampos();

            abrirFxml(root, title, 720, 400, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaCriarCliente(String title) {
        String fileName = "tela-criar-cliente";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            abrirFxml(root, title, 720, 400, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }
}
