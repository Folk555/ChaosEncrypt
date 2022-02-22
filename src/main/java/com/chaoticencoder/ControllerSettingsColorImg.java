package com.chaoticencoder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSettingsColorImg {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ResslerParametrA;

    @FXML
    private TextField ResslerParametrB;

    @FXML
    private TextField ResslerParametrE;

    @FXML
    private TextField ResslerParametrR;

    @FXML
    private Button btnSave;

    @FXML
    private TextField generationCount;

    @FXML
    private CheckBox switchControl;

    @FXML
    void onBtnSaveClick(ActionEvent event) {
        Constants.RESSLER_PARAMETR_E = Double.parseDouble(ResslerParametrE.getText());
        Constants.RESSLER_PARAMETR_A = Double.parseDouble(ResslerParametrA.getText());
        Constants.RESSLER_PARAMETR_B = Double.parseDouble(ResslerParametrB.getText());
        Constants.RESSLER_PARAMETR_R = Double.parseDouble(ResslerParametrR.getText());
        Constants.generationCount = Integer.parseInt(generationCount.getText());
        Constants.switchControl = switchControl.isSelected();

        ControllerCryptColorImg.myChaoticEncDecColorImg.setNewRarametrs(Constants.generationCount, Constants.RESSLER_PARAMETR_E,
                Constants.RESSLER_PARAMETR_A, Constants.RESSLER_PARAMETR_B, Constants.RESSLER_PARAMETR_R, Constants.switchControl);

        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        ResslerParametrE.setText(Double.toString(Constants.RESSLER_PARAMETR_E));
        ResslerParametrA.setText(Double.toString(Constants.RESSLER_PARAMETR_A));
        ResslerParametrB.setText(Double.toString(Constants.RESSLER_PARAMETR_B));
        ResslerParametrR.setText(Double.toString(Constants.RESSLER_PARAMETR_R));
        generationCount.setText(Integer.toString(Constants.generationCount));
        switchControl.setSelected(Constants.switchControl);
    }

}
