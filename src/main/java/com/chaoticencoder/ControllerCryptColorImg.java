package com.chaoticencoder;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ControllerCryptColorImg {

    static public ChaoticEncDecColorImg myChaoticEncDecColorImg;

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
    private void onChooseImgClick(ActionEvent event) throws Exception {
        FileChooser myFileChooser = new FileChooser();
        Stage fileChooserStage = new Stage();
        File selectedFile = myFileChooser.showOpenDialog(fileChooserStage);
        if (selectedFile == null) return; //в случае если нажали "Отмена" при выборе файла
        BufferedImage img = ImageIO.read(selectedFile);

        myChaoticEncDecColorImg =
                new ChaoticEncDecColorImg.ChaoticEncDecImgBuilder()
                        .withImage( img )
                        .build();
        imgOrigView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecColorImg.origImg, null ));
        chosenImgLabel.setText(selectedFile.getPath());

        decryptionBtn.setDisable(false);
        encryptBtn.setDisable(false);
    }

    @FXML
    void onAnalysisBtnClick(ActionEvent event) throws MalformedURLException {
        var fxmlsLoader = new ArrayList<FXMLLoader>();
        fxmlsLoader.add(new FXMLLoader(getClass().getResource("colorImgAnalysis/blueChannelAnalysis.fxml")));
        fxmlsLoader.add(new FXMLLoader(getClass().getResource("colorImgAnalysis/greenChannelAnalysis.fxml")));
        fxmlsLoader.add(new FXMLLoader(getClass().getResource("colorImgAnalysis/redChannelAnalysis.fxml")));
        try {
            for (var fxml:fxmlsLoader) {
                fxml.load();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (var fxml:fxmlsLoader) {
            Parent root = fxml.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    @FXML
    void onDecryptionBtnClick(ActionEvent event) {

        myChaoticEncDecColorImg.decrypt();
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecColorImg.deCryptImg, null ));

    }

    @FXML
    void onEncryptBtnClick(ActionEvent event) {
        myChaoticEncDecColorImg.encrypt();
        imgCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecColorImg.cryptImg, null ));
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecColorImg.deCryptImg, null ));

        analysisBtn.setDisable(false);
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

        myChaoticEncDecColorImg =
                new ChaoticEncDecColorImg.ChaoticEncDecImgBuilder()
                        .withImage( img )
                        .build();
        imgOrigView.setImage(SwingFXUtils.toFXImage(ControllerCryptColorImg.myChaoticEncDecColorImg.origImg, null ));
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settingsColorImg.fxml"));
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