package com.chaoticencoder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Controller {
    @FXML
    private Button chooseImg;

    @FXML
    private Label chosenImgLabel;

    @FXML
    private ImageView imgCrypt;

    @FXML
    private ImageView imgDeCrypt;

    @FXML
    private ImageView imgOrig;

    @FXML
    private void onChooseImgClick(ActionEvent event) throws Exception {
        FileChooser myFileChooser = new FileChooser();
        Stage fileChooserStage = new Stage();
        File selectedFile = myFileChooser.showOpenDialog(fileChooserStage);
        if (selectedFile == null) return; //в случае если нажали "Отмена" при выборе файла
        BufferedImage img = ImageIO.read(selectedFile);
        imgOrig.setImage(SwingFXUtils.toFXImage(img, null ));
        chosenImgLabel.setText(selectedFile.getPath());


    }


}