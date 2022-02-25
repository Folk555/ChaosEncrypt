package com.chaoticencoder.controllersColorImgAnalysis;

import com.chaoticencoder.ChaoticEncDecColorImg;
import com.chaoticencoder.ControllerCryptColorImg;
import com.chaoticencoder.CryptImgAnalysis;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRedChannelAnalysis {

    static ChaoticEncDecColorImg myChaoticEncDecColorImg = ControllerCryptColorImg.myChaoticEncDecColorImg;

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
    private TextArea cryptTA;

    @FXML
    private TextArea origTA;

    @FXML
    void initialize() {
        assert BarCharLeft != null : "fx:id=\"BarCharLeft\" was not injected: check your FXML file 'analysisWindow.fxml'.";
        assert BarCharRight != null : "fx:id=\"BarCharRight\" was not injected: check your FXML file 'analysisWindow.fxml'.";

        //Гистограмма
        int[] invariantOrig = CryptImgAnalysis.getInvariantRedPixels(myChaoticEncDecColorImg.origImg);
        int[] invariantCrypt = CryptImgAnalysis.getInvariantRedPixels(myChaoticEncDecColorImg.cryptImg);
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
        comparisonTA.appendText("NPCR: "+ CryptImgAnalysis.NPCR(myChaoticEncDecColorImg.origImg, myChaoticEncDecColorImg.cryptImg));
        comparisonTA.appendText("\nUACI: "+ CryptImgAnalysis.getUACIredPixels(myChaoticEncDecColorImg.origImg, myChaoticEncDecColorImg.cryptImg));
        System.out.println("Сравнительные хар-ки построены");

        //характеристики оригинала
        origTA.appendText("Энтропия: "+ CryptImgAnalysis.getEntropyRedPixels(myChaoticEncDecColorImg.origImg));
        origTA.appendText("\nКорреляция");
        origTA.appendText("\n   по горизонтале: "+ CryptImgAnalysis.getCorrelationRedPixels(myChaoticEncDecColorImg.origImg)[0]);
        origTA.appendText("\n   по вертикале: "+ CryptImgAnalysis.getCorrelationRedPixels(myChaoticEncDecColorImg.origImg)[1]);
        origTA.appendText("\n   по диагонале: "+ CryptImgAnalysis.getCorrelationRedPixels(myChaoticEncDecColorImg.origImg)[2]);
        System.out.println("Хар-ки оригинала построены");

        //характеристики криптограммы
        cryptTA.appendText("Энтропия: "+ CryptImgAnalysis.getEntropyRedPixels(myChaoticEncDecColorImg.cryptImg));
        cryptTA.appendText("\nКорреляция");
        cryptTA.appendText("\n   по горизонтале: "+ CryptImgAnalysis.getCorrelationRedPixels(myChaoticEncDecColorImg.cryptImg)[0]);
        cryptTA.appendText("\n   по вертикале: "+ CryptImgAnalysis.getCorrelationRedPixels(myChaoticEncDecColorImg.cryptImg)[1]);
        cryptTA.appendText("\n   по диагонале: "+ CryptImgAnalysis.getCorrelationRedPixels(myChaoticEncDecColorImg.cryptImg)[2]);
        System.out.println("Хар-ки криптограммы построены");
    }

}
