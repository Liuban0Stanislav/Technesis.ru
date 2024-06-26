package org.lyuban.technesis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TechnesisApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TechnesisApplication.class.getResource("mainV2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 345);
        stage.setTitle("Technesis: \"Cоздание заявок\" - тестовое задание");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}