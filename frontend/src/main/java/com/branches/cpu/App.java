package com.branches.cpu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("fxml/tela-inicial.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load());

        try(InputStream inputStream = getClass().getResourceAsStream("/com/branches/cpu/Images/application-icon.png")) {
            Image icon = new Image(inputStream);

            stage.setTitle("Branches Orçamentos");
            stage.setMinWidth(720);
            stage.setMinHeight(480);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.getIcons().setAll(icon);
            stage.show();
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela inicial");
            throw e;
        }
    }
    
    public static void main(String[] args) {
        launch();
    }

}