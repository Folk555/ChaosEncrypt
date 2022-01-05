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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml")); //загружаем XML с наполнением сцены (элементы node - узлы)
        Scene scene = new Scene(fxmlLoader.load());// Создаем сцену
        stage.setTitle("Шифратор");
        InputStream iconStream =
                getClass().getResourceAsStream("/files/icon.png"); //getClass() возвращает текущий класс.
        // getResourceAsStream открывает ресурсы модуля в котором находится текущий класс
        Image image = new Image(iconStream);
        stage.getIcons().add(image);

        stage.setScene(scene); //Загружаем сцену на  Stage.
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }


}