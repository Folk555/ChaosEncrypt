package com.chaoticencoder;

//import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

public class ControllerAnalysisWindow {

    static ChaoticEncDecImg myChaoticEncDecImg = ControllerCryptGrayImg.myChaoticEncDecImg;

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
        int[] invariantOrig = CryptAnalysis.invariant(myChaoticEncDecImg.origImg);
        int[] invariantCrypt = CryptAnalysis.invariant(myChaoticEncDecImg.cryptImg);
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
        comparisonTA.appendText("NPCR: "+CryptAnalysis.NPCR(myChaoticEncDecImg.origImg, myChaoticEncDecImg.cryptImg));
        comparisonTA.appendText("\nUACI: "+CryptAnalysis.UACI(myChaoticEncDecImg.origImg, myChaoticEncDecImg.cryptImg));
        System.out.println("Сравнительные хар-ки построены");

        //характеристики оригинала
        origTA.appendText("Энтропия: "+CryptAnalysis.entropy(myChaoticEncDecImg.origImg));
        origTA.appendText("\nКорреляция");
        origTA.appendText("\n   по горизонтале: "+CryptAnalysis.correlation(myChaoticEncDecImg.origImg)[0]);
        origTA.appendText("\n   по вертикале: "+CryptAnalysis.correlation(myChaoticEncDecImg.origImg)[1]);
        origTA.appendText("\n   по диагонале: "+CryptAnalysis.correlation(myChaoticEncDecImg.origImg)[2]);
        System.out.println("Хар-ки оригинала построены");

        //характеристики криптограммы
        cryptTA.appendText("Энтропия: "+CryptAnalysis.entropy(myChaoticEncDecImg.cryptImg));
        cryptTA.appendText("\nКорреляция");
        cryptTA.appendText("\n   по горизонтале: "+CryptAnalysis.correlation(myChaoticEncDecImg.cryptImg)[0]);
        cryptTA.appendText("\n   по вертикале: "+CryptAnalysis.correlation(myChaoticEncDecImg.cryptImg)[1]);
        cryptTA.appendText("\n   по диагонале: "+CryptAnalysis.correlation(myChaoticEncDecImg.cryptImg)[2]);
        System.out.println("Хар-ки криптограммы построены");
    }

}
