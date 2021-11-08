package com.chaoticencoder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cryptGrayImg.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Шифратор");
        InputStream iconStream =
                getClass().getResourceAsStream("/icon.png"); //getClass() возвращает текущий класс.
        // getResourceAsStream открывает ресурсы модуля в котором находится текущий класс
        Image image = new Image(iconStream);
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }


}