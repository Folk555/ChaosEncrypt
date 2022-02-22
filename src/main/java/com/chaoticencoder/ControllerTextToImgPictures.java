package com.chaoticencoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class ControllerTextToImgPictures {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label cryptLblComparison;

    @FXML
    private TextArea cryptTA;

    @FXML
    private Label deCryptLblComparison;

    @FXML
    private TextArea deCryptTA;

    @FXML
    private ImageView imgCryptView;

    @FXML
    private ImageView imgDeCryptView;

    @FXML
    private ImageView imgOrigView;

    @FXML
    private Label origLblComparison;

    @FXML
    private TextArea origTA;


    private String textFromImg(BufferedImage img){

        int squareImg = img.getWidth();
        ArrayList<Character> alphabet = ControllerCryptText.text.getAlphabet();
        String text = "";

        for (int x = 0; x < squareImg; x++) {
            for (int y = 0; y < squareImg; y++) {
                int pixel = (img.getRGB(x,y) & 0xff);
                char symbol = alphabet.get(pixel);
                text += symbol;
            }
        }
        return text;
    }


    @FXML
    void initialize() {
        assert cryptLblComparison != null : "fx:id=\"cryptLblComparison\" was not injected: check your FXML file 'textToImgPictures.fxml'.";
        assert cryptTA != null : "fx:id=\"cryptTA\" was not injected: check your FXML file 'textToImgPictures.fxml'.";
        assert deCryptLblComparison != null : "fx:id=\"deCryptLblComparison\" was not injected: check your FXML file 'textToImgPictures.fxml'.";
        assert deCryptTA != null : "fx:id=\"deCryptTA\" was not injected: check your FXML file 'textToImgPictures.fxml'.";
        assert imgCryptView != null : "fx:id=\"imgCryptView\" was not injected: check your FXML file 'textToImgPictures.fxml'.";
        assert imgDeCryptView != null : "fx:id=\"imgDeCryptView\" was not injected: check your FXML file 'textToImgPictures.fxml'.";
        assert imgOrigView != null : "fx:id=\"imgOrigView\" was not injected: check your FXML file 'textToImgPictures.fxml'.";
        assert origLblComparison != null : "fx:id=\"origLblComparison\" was not injected: check your FXML file 'textToImgPictures.fxml'.";
        assert origTA != null : "fx:id=\"origTA\" was not injected: check your FXML file 'textToImgPictures.fxml'.";

        BufferedImage[] imgs = ControllerTextToImgHistogram.getImgsFromTexts();
        imgOrigView.setImage(SwingFXUtils.toFXImage(imgs[0], null ));
        imgCryptView.setImage(SwingFXUtils.toFXImage(imgs[1], null ));
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(imgs[2], null ));

        String origTextFromImg = textFromImg(imgs[0]);
        String cryptTextFromImg = textFromImg(imgs[1]);
        String deCryptTextFromImg = textFromImg(imgs[2]);

        origTA.appendText(origTextFromImg);
        cryptTA.appendText(cryptTextFromImg);
        deCryptTA.appendText(deCryptTextFromImg);

        origLblComparison.setText(Double.toString(CryptTextAnalysis.match(origTextFromImg, ControllerCryptText.text.getOrigText()))+"%");
        cryptLblComparison.setText(Double.toString(CryptTextAnalysis.match(cryptTextFromImg, ControllerCryptText.text.getCryptText()))+"%");
        deCryptLblComparison.setText(Double.toString(CryptTextAnalysis.match(deCryptTextFromImg, ControllerCryptText.text.getDecryptText()))+"%");


    }

}
