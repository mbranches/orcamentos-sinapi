package com.branches.cpu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("fxml/tela-orcamento.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load());

        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }

}