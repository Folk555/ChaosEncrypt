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

    static ChaoticEncDecImg myChaoticEncDecImg;

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
        myChaoticEncDecImg.cryptImg = new BufferedImage(myChaoticEncDecImg.origImg.getColorModel(),
                myChaoticEncDecImg.origImg.copyData(null), myChaoticEncDecImg.origImg.getColorModel().isAlphaPremultiplied(), null);

        //System.out.println("  cryptImg: "+(myChaoticEncDecImg.origImg.getRGB(19,13)& 0xff)+"  origImg: "+(myChaoticEncDecImg.origImg.getRGB(19,13)& 0xff));

        myChaoticEncDecImg.encrypt();
        imgCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.cryptImg, null ));
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.deCryptImg, null ));

    }

    @FXML
    void onDecryptionBtnClick(ActionEvent event) {

        myChaoticEncDecImg.decrypt();
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.deCryptImg, null ));

        for (int i=myChaoticEncDecImg.sz_imgX-1; i>=0; --i) {
            for (int j=myChaoticEncDecImg.sz_imgY-1; j>=0; --j) {

                if (Math.abs((myChaoticEncDecImg.deCryptImg.getRGB(i,j) & 0xff) - ((myChaoticEncDecImg.origImg.getRGB(i,j) & 0xff)))>0)
                    System.out.println("i: "+i+"  j: "+j+"  дешифр: "+ (myChaoticEncDecImg.deCryptImg.getRGB(i,j) & 0xff)+"  оригин: "+(myChaoticEncDecImg.origImg.getRGB(i,j) & 0xff));
            }
        }
    }

    public void selectDefaultImg(){
        File selectedImgDefault = new File("M:\\статья\\прога\\1024.jpg");
         //в случае если файла не существует
        BufferedImage img = null;
        try {
            img = ImageIO.read(selectedImgDefault); //в случае если файла не существует
        } catch (IOException e) {
            System.out.println("Изображение \"по умолчанию\" не существует");
            return;
        }

        myChaoticEncDecImg =
                new ChaoticEncDecImg.ChaoticEncDecImgBuilder()
                        .withImage( img )
                        .build();
        imgOrigView.setImage(SwingFXUtils.toFXImage(Controller.myChaoticEncDecImg.origImg, null ));
        chosenImgLabel.setText(selectedImgDefault.getPath());

        decryptionBtn.setDisable(false);
        encryptBtn.setDisable(false);
    }

    @FXML
    void initialize() {
        assert analysisBtn != null : "fx:id=\"analysisBtn\" was not injected: check your FXML file 'imgEncrypt.fxml'.";
        assert chooseImg != null : "fx:id=\"chooseImg\" was not injected: check your FXML file 'imgEncrypt.fxml'.";
        assert chosenImgLabel != null : "fx:id=\"chosenImgLabel\" was not injected: check your FXML file 'imgEncrypt.fxml'.";
        assert decryptionBtn != null : "fx:id=\"decryptionBtn\" was not injected: check your FXML file 'imgEncrypt.fxml'.";
        assert encryptBtn != null : "fx:id=\"encryptBtn\" was not injected: check your FXML file 'imgEncrypt.fxml'.";
        assert imgCryptView != null : "fx:id=\"imgCryptView\" was not injected: check your FXML file 'imgEncrypt.fxml'.";
        assert imgDeCryptView != null : "fx:id=\"imgDeCryptView\" was not injected: check your FXML file 'imgEncrypt.fxml'.";
        assert imgOrigView != null : "fx:id=\"imgOrigView\" was not injected: check your FXML file 'imgEncrypt.fxml'.";
        selectDefaultImg(); //чтоб не выбирать изображение каждый раз при запуске проги
    }

}