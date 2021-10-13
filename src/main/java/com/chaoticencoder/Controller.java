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
import java.awt.*;

public class Controller {

    ChaoticEncDecImg myChaoticEncDecImg;

    @FXML
    private Button chooseImg;

    @FXML
    private Label chosenImgLabel;

    @FXML
    private ImageView imgCryptView;

    @FXML
    private ImageView imgDeCryptView;

    @FXML
    private ImageView imgOrigView;

    @FXML
    private Button analysisBtn;

    @FXML
    private Button decryptionBtn;

    @FXML
    private Button encryptBtn;

    @FXML
    private void onChooseImgClick(ActionEvent event) throws Exception {
        FileChooser myFileChooser = new FileChooser();
        Stage fileChooserStage = new Stage();
        File selectedFile = myFileChooser.showOpenDialog(fileChooserStage);
        if (selectedFile == null) return; //в случае если нажали "Отмена" при выборе файла
        BufferedImage img = ImageIO.read(selectedFile);

        myChaoticEncDecImg =
                new ChaoticEncDecImg.ChaoticEncDecImgBuilder()
                        .withImage( img )
                        .build();
        imgOrigView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.origImg, null ));
        chosenImgLabel.setText(selectedFile.getPath());

        decryptionBtn.setDisable(false);
        encryptBtn.setDisable(false);
    }

    @FXML
    void onAnalysisBtnClick(ActionEvent event) {
        //if ((imgCrypt.getImage() == null) || (imgDeCrypt.getImage() == null)) return; Будет отдельная сцена для анализа. Там будет выбор анализ по одному изображению или сравнение по 2ум
    }

    @FXML
    void onEncryptBtnClick(ActionEvent event) {
        myChaoticEncDecImg.encrypt();
        imgCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.cryptImg, null ));
        /*int color = myChaoticEncDecImg.origImg.getRGB(0,0);
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        System.out.println(blue+" "+green+" "+red);
         color = myChaoticEncDecImg.origImg.getRGB(0,511);
         blue = color & 0xff;
         green = (color & 0xff00) >> 8;
         red = (color & 0xff0000) >> 16;
        System.out.println(blue+" "+green+" "+red);
        color = myChaoticEncDecImg.origImg.getRGB(511,0);
        blue = color & 0xff;
        green = (color & 0xff00) >> 8;
        red = (color & 0xff0000) >> 16;
        System.out.println(blue+" "+green+" "+red);
        color = myChaoticEncDecImg.origImg.getRGB(511,511);
        blue = color & 0xff;
        green = (color & 0xff00) >> 8;
        red = (color & 0xff0000) >> 16;
        System.out.println(blue+" "+green+" "+red);*/



    }

    @FXML
    void onDecryptionBtnClick(ActionEvent event) {

    }

}