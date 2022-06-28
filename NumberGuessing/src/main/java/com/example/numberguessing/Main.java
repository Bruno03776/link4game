package com.example.numberguessing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 380);
        stage.setTitle("Link-4 Game");
        stage.setScene(scene);
        this.scene = scene;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}