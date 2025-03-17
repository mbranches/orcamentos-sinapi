package com.branches.cpu.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AbrirFxml {
    private final String PATH = "/com/branches/cpu/fxml/";

    private void abrirFxmlDefault(Parent root, String title, Integer minWidth, Integer minHeight, Boolean resizable) {
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

    public void abrirTelaOrcamento(String title){
        String fileName = "tela-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));

            Parent root = loader.load();
            abrirFxmlDefault(root, title, 900, 600, true);

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
            abrirFxmlDefault(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }
}
