package com.chaoticencoder;

//import java.awt.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTextToImgHistogram {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<?, ?> BarCharLeft;

    @FXML
    private BarChart<?, ?> BarCharRight;

    @FXML
    private TextArea comparisonTA;

    @FXML
    private ImageView imgCryptView;

    @FXML
    private ImageView imgDeCryptView;

    @FXML
    private ImageView imgOrigView;

    @FXML
    private TextArea cryptTA;

    @FXML
    private TextArea origTA;

    private static BufferedImage imgFromOrigText, imgFromCryptText, imgFromdeCryptText;

    @FXML
    void initialize() {
        assert BarCharLeft != null : "fx:id=\"BarCharLeft\" was not injected: check your FXML file 'analysisWindow.fxml'.";
        assert BarCharRight != null : "fx:id=\"BarCharRight\" was not injected: check your FXML file 'analysisWindow.fxml'.";

        int squareImg = ControllerCryptText.text.getSquare();
        imgFromOrigText = new BufferedImage(squareImg, squareImg, BufferedImage.TYPE_INT_RGB);

        //в данном кейсе нам нужно черно-бьелое изображение, где интенсивность это "код" символа в алфавите
        //получаем код (по алфавиту) символов и создаем картинки
        int[] origTextCode = ControllerCryptText.text.getOrigTextCode();
        for (int x = 0; x < squareImg; x++) {
            for (int y = 0; y < squareImg; y++) {
                int grey = origTextCode[x*squareImg+y];
                int newRed = grey;
                int newGreen = grey;
                int newBlue = grey;
                Color newColor = new Color(newRed, newGreen, newBlue);
                imgFromOrigText.setRGB(x, y, newColor.getRGB());
            }
        }

        imgFromCryptText = new BufferedImage(squareImg, squareImg, BufferedImage.TYPE_INT_RGB);
        int[] cryptTextCode = ControllerCryptText.text.getCryptTextCode();
        for (int x = 0; x < squareImg; x++) {
            for (int y = 0; y < squareImg; y++) {
                int grey = cryptTextCode[x*squareImg+y];
                int newRed = grey;
                int newGreen = grey;
                int newBlue = grey;
                Color newColor = new Color(newRed, newGreen, newBlue);
                imgFromCryptText.setRGB(x, y, newColor.getRGB());
            }
        }

        imgFromdeCryptText = new BufferedImage(squareImg, squareImg, BufferedImage.TYPE_INT_RGB);
        int[] deCryptTextCode = ControllerCryptText.text.getDeCryptTextCode();
        for (int x = 0; x < squareImg; x++) {
            for (int y = 0; y < squareImg; y++) {
                int grey = deCryptTextCode[x*squareImg+y];
                int newRed = grey;
                int newGreen = grey;
                int newBlue = grey;
                Color newColor = new Color(newRed, newGreen, newBlue);
                imgFromdeCryptText.setRGB(x, y, newColor.getRGB());
            }
        }

        //Гистограмма
        int[] invariantOrig = CryptImgAnalysis.getInvariantGreenPixels(imgFromOrigText);
        int[] invariantCrypt = CryptImgAnalysis.getInvariantGreenPixels(imgFromCryptText);
        XYChart.Series dsOrig = new XYChart.Series();
        XYChart.Series dsCrypt = new XYChart.Series();
        for (int i = 0; i < invariantOrig.length; i++) {
            dsOrig.getData().add(new XYChart.Data(Integer.toString(i), invariantOrig[i]));
            dsCrypt.getData().add(new XYChart.Data(Integer.toString(i), invariantCrypt[i]));
        }
        BarCharLeft.getData().add(dsOrig);
        BarCharRight.getData().add(dsCrypt);
        System.out.println("Гистограммы построены");

        //сравнительные характеристики
        comparisonTA.appendText("NPCR: "+ CryptImgAnalysis.NPCR(imgFromOrigText, imgFromCryptText));
        comparisonTA.appendText("\nUACI: "+ CryptImgAnalysis.getUACIgrayPixels(imgFromOrigText, imgFromCryptText));
        System.out.println("Сравнительные хар-ки построены");

        //характеристики оригинала
        origTA.appendText("Энтропия: "+ CryptImgAnalysis.getEntropyGrayPixels(imgFromOrigText));
        origTA.appendText("\nКорреляция");
        double[] corr = CryptImgAnalysis.getCorrelationGrayPixels(imgFromOrigText);
        origTA.appendText("\n   по горизонтале: "+ corr[0]);
        origTA.appendText("\n   по вертикале: "+ corr[1]);
        origTA.appendText("\n   по диагонале: "+ corr[2]);
        System.out.println("Хар-ки оригинала построены");

        //характеристики криптограммы
        cryptTA.appendText("Энтропия: "+ CryptImgAnalysis.getEntropyGrayPixels(imgFromCryptText));
        cryptTA.appendText("\nКорреляция");
        corr = CryptImgAnalysis.getCorrelationGrayPixels(imgFromCryptText);
        cryptTA.appendText("\n   по горизонтале: "+ corr[0]);
        cryptTA.appendText("\n   по вертикале: "+ corr[1]);
        cryptTA.appendText("\n   по диагонале: "+ corr[2]);
        System.out.println("Хар-ки криптограммы построены");


        Stage stageForPictures = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("textToImgPictures.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stageForPictures.setScene(scene);
        stageForPictures.show();
    }

    public static BufferedImage[] getImgsFromTexts() {
        BufferedImage[] imgs = {imgFromOrigText, imgFromCryptText, imgFromdeCryptText};
        return imgs;
    }
}
