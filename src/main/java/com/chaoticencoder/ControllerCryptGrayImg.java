package com.chaoticencoder;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ControllerCryptGrayImg {

    static ChaoticEncDecGrayImg myChaoticEncDecGrayImg;

    @FXML
    private Button analysisBtn;

    @FXML
    private Button chooseImg;

    @FXML
    private Label chosenImgLabel;

    @FXML
    private Button decryptionBtn;

    @FXML
    private Button encryptBtn;

    @FXML
    private ImageView imgCryptView;

    @FXML
    private ImageView imgDeCryptView;

    @FXML
    private ImageView imgOrigView;

    @FXML
    private Button toMenuBtn;

    @FXML
    private Button toSettings;

    @FXML
    private Label labelDecryptTime;

    @FXML
    private Label labelEncryptTime;

    @FXML
    private void onChooseImgClick(ActionEvent event) throws Exception {
        FileChooser myFileChooser = new FileChooser();
        Stage fileChooserStage = new Stage();
        File selectedFile = myFileChooser.showOpenDialog(fileChooserStage);
        if (selectedFile == null) return; //в случае если нажали "Отмена" при выборе файла
        BufferedImage img = ImageIO.read(selectedFile);

        myChaoticEncDecGrayImg =
                new ChaoticEncDecGrayImg.ChaoticEncDecImgBuilder()
                        .withImage( img )
                        .build();
        imgOrigView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecGrayImg.origImg, null ));
        chosenImgLabel.setText(selectedFile.getPath());

        decryptionBtn.setDisable(false);
        encryptBtn.setDisable(false);
    }

    @FXML
    void onAnalysisBtnClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("analysisWindow.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onDecryptionBtnClick(ActionEvent event) {
        long startTimeMiliseconds = Calendar.getInstance().getTime().getTime();

        myChaoticEncDecGrayImg.decrypt();
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecGrayImg.deCryptImg, null ));
        analysisBtn.setDisable(false);

        long endTimeMiliseconds = Calendar.getInstance().getTime().getTime();
        labelDecryptTime.setText("Время шифрования: " + (endTimeMiliseconds-startTimeMiliseconds) / 1000 + "сек.");
    }

    @FXML
    void onEncryptBtnClick(ActionEvent event) {
        long startTimeMiliseconds = Calendar.getInstance().getTime().getTime();

        myChaoticEncDecGrayImg.cryptImg = new BufferedImage(myChaoticEncDecGrayImg.origImg.getColorModel(),
                myChaoticEncDecGrayImg.origImg.copyData(null), myChaoticEncDecGrayImg.origImg.getColorModel().isAlphaPremultiplied(), null);

        myChaoticEncDecGrayImg.encrypt();
        imgCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecGrayImg.cryptImg, null ));
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecGrayImg.deCryptImg, null ));
        analysisBtn.setDisable(true);

        long endTimeMiliseconds = Calendar.getInstance().getTime().getTime();
        labelEncryptTime.setText("Время шифрования: " + (endTimeMiliseconds-startTimeMiliseconds) / 1000 + " сек.");

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

        myChaoticEncDecGrayImg =
                new ChaoticEncDecGrayImg.ChaoticEncDecImgBuilder()
                        .withImage( img )
                        .build();
        imgOrigView.setImage(SwingFXUtils.toFXImage(ControllerCryptGrayImg.myChaoticEncDecGrayImg.origImg, null ));
        chosenImgLabel.setText(selectedImgDefault.getPath());

        decryptionBtn.setDisable(false);
        encryptBtn.setDisable(false);
    }

    @FXML
    void onToMenuBtnClick(ActionEvent event) {
        Stage stage = (Stage) Stage.getWindows().get(0);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
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
    void onToSettingsBtnClick(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settingsGrayImg.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void initialize() {

        selectDefaultImg(); //чтоб не выбирать изображение каждый раз при запуске проги
    }

}