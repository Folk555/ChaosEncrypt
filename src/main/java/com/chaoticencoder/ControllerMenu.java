package com.chaoticencoder;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    }

    @FXML
    void onTextBtnClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnColorImgChoice != null : "fx:id=\"btnColorImgChoice\" was not injected: check your FXML file 'menu.fxml'.";
        assert btnGrayImgChoice != null : "fx:id=\"btnGrayImgChoice\" was not injected: check your FXML file 'menu.fxml'.";
        assert btnTextChoice != null : "fx:id=\"btnTextChoice\" was not injected: check your FXML file 'menu.fxml'.";

    }

}
