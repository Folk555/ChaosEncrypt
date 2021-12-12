package com.chaoticencoder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ControllerMenu {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnColorImgChoice;

    @FXML
    private Button btnGrayImgChoice;

    @FXML
    private Button btnTextChoice;

    @FXML
    void onColorImgBtnClick(ActionEvent event) {

    }

    @FXML
    void onGrayImgBtnClick(ActionEvent event) {

        Stage stage = (Stage) Stage.getWindows().get(0);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cryptGrayImg.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void onTextBtnClick(ActionEvent event) {
        Stage stage = (Stage) Stage.getWindows().get(0);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cryptText.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void initialize() {
        assert btnColorImgChoice != null : "fx:id=\"btnColorImgChoice\" was not injected: check your FXML file 'menu.fxml'.";
        assert btnGrayImgChoice != null : "fx:id=\"btnGrayImgChoice\" was not injected: check your FXML file 'menu.fxml'.";
        assert btnTextChoice != null : "fx:id=\"btnTextChoice\" was not injected: check your FXML file 'menu.fxml'.";
    }

}
