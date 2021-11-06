package com.chaoticencoder;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.chaoticencoder.Controller;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imgEncrypt.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Шифратор");
        InputStream iconStream =
                getClass().getResourceAsStream("/icon.png"); //getClass() возвращает текущий класс.
        // getResourceAsStream открывает ресурсы модуля в котором находится текущий класс
        Image image = new Image(iconStream);
        stage.getIcons().add(image);


        /*char c='A';
        switch (c){
            case 'a': System.out.println("a"); break;
            case 'b': System.out.println("b"); break;
            case 'c': System.out.println("c"); break;
        }*/

        /*int nn=514, sz_imgX=512;
        if (nn>=0) {if ((nn >= sz_imgX)) nn = nn % sz_imgX;}
        else nn = (sz_imgX + (nn % -sz_imgX));
        System.out.println( nn);*/

        //System.out.println( -257 % 255);
        //System.out.println((255 - (-5 % -255)) % 255);


        /*int color = -16185079;
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        System.out.println(blue+" "+green+" "+red);*/

        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }


}