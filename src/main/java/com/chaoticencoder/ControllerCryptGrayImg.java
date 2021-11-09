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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ControllerCryptGrayImg {

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
    private TextField generationCount;

    @FXML
    private CheckBox switchControl;

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

        myChaoticEncDecImg.decrypt();
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.deCryptImg, null ));

        for (int i=myChaoticEncDecImg.sz_imgX-1; i>=0; --i) {
            for (int j=myChaoticEncDecImg.sz_imgY-1; j>=0; --j) {

                if (Math.abs((myChaoticEncDecImg.deCryptImg.getRGB(i,j) & 0xff) - ((myChaoticEncDecImg.origImg.getRGB(i,j) & 0xff)))>0)
                    System.out.println("i: "+i+"  j: "+j+"  дешифр: "+ (myChaoticEncDecImg.deCryptImg.getRGB(i,j) & 0xff)+"  оригин: "+(myChaoticEncDecImg.origImg.getRGB(i,j) & 0xff));
            }
        }
    }

    @FXML
    void onEncryptBtnClick(ActionEvent event) {
        myChaoticEncDecImg.setNewGen(Integer.parseInt(generationCount.getText()));
        myChaoticEncDecImg.upr = switchControl.isSelected() ? 1 : 0;
        myChaoticEncDecImg.cryptImg = new BufferedImage(myChaoticEncDecImg.origImg.getColorModel(),
                myChaoticEncDecImg.origImg.copyData(null), myChaoticEncDecImg.origImg.getColorModel().isAlphaPremultiplied(), null);

        //System.out.println("  cryptImg: "+(myChaoticEncDecImg.origImg.getRGB(19,13)& 0xff)+"  origImg: "+(myChaoticEncDecImg.origImg.getRGB(19,13)& 0xff));

        myChaoticEncDecImg.encrypt();
        imgCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.cryptImg, null ));
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.deCryptImg, null ));
        onDecryptionBtnClick(new ActionEvent());
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

        myChaoticEncDecImg =
                new ChaoticEncDecImg.ChaoticEncDecImgBuilder()
                        .withImage( img )
                        .build();
        imgOrigView.setImage(SwingFXUtils.toFXImage(ControllerCryptGrayImg.myChaoticEncDecImg.origImg, null ));
        chosenImgLabel.setText(selectedImgDefault.getPath());

        decryptionBtn.setDisable(false);
        encryptBtn.setDisable(false);
    }

    @FXML
    void initialize() {
        assert analysisBtn != null : "fx:id=\"analysisBtn\" was not injected: check your FXML file 'cryptGrayImg.fxml'.";
        assert chooseImg != null : "fx:id=\"chooseImg\" was not injected: check your FXML file 'cryptGrayImg.fxml'.";
        assert chosenImgLabel != null : "fx:id=\"chosenImgLabel\" was not injected: check your FXML file 'cryptGrayImg.fxml'.";
        assert decryptionBtn != null : "fx:id=\"decryptionBtn\" was not injected: check your FXML file 'cryptGrayImg.fxml'.";
        assert encryptBtn != null : "fx:id=\"encryptBtn\" was not injected: check your FXML file 'cryptGrayImg.fxml'.";
        assert imgCryptView != null : "fx:id=\"imgCryptView\" was not injected: check your FXML file 'cryptGrayImg.fxml'.";
        assert imgDeCryptView != null : "fx:id=\"imgDeCryptView\" was not injected: check your FXML file 'cryptGrayImg.fxml'.";
        assert imgOrigView != null : "fx:id=\"imgOrigView\" was not injected: check your FXML file 'cryptGrayImg.fxml'.";
        selectDefaultImg(); //чтоб не выбирать изображение каждый раз при запуске проги
    }

}