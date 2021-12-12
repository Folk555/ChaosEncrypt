package com.chaoticencoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControllerCryptText {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea cryptTextArea;

    @FXML
    private TextArea deCryptTextArea;

    @FXML
    private TextArea cryptAnalysis;

    @FXML
    private Button encryptBtn;

    @FXML
    private TextField generationCount;

    @FXML
    private TextArea origTextArea;

    @FXML
    private CheckBox switchControl;

    @FXML
    private Button toMenuBtn;

    @FXML
    private Button alphabetCreateBtn;

    @FXML
    void onEncryptBtnClick(ActionEvent event) {

        ChaoticEncDecText text = new ChaoticEncDecText(origTextArea.getText(),111, switchControl.isSelected(), Integer.parseInt(generationCount.getText()));
        cryptTextArea.setText(text.getCryptText());
        deCryptTextArea.setText(text.getDecryptText());
        cryptAnalysis.setText("Совпадения оригинал и криптограммы: " + "\n" + String.format("%.5f",CryptTextAnalysis.match(text.getOrigText(), text.getCryptText())) + "%");
        cryptAnalysis.appendText("\n");
        cryptAnalysis.appendText("Совпадения оригинал и дешифровки: " + "\n" + String.format("%.1f",CryptTextAnalysis.match(text.getOrigText(), text.getDecryptText())) + "%");

        /*myChaoticEncDecImg.setNewGen(Integer.parseInt(generationCount.getText()));
        myChaoticEncDecImg.upr = switchControl.isSelected() ? 1 : 0;
        myChaoticEncDecImg.cryptImg = new BufferedImage(myChaoticEncDecImg.origImg.getColorModel(),
                myChaoticEncDecImg.origImg.copyData(null), myChaoticEncDecImg.origImg.getColorModel().isAlphaPremultiplied(), null);

        //System.out.println("  cryptImg: "+(myChaoticEncDecImg.origImg.getRGB(19,13)& 0xff)+"  origImg: "+(myChaoticEncDecImg.origImg.getRGB(19,13)& 0xff));

        myChaoticEncDecImg.encrypt();
        imgCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.cryptImg, null ));
        imgDeCryptView.setImage(SwingFXUtils.toFXImage(myChaoticEncDecImg.deCryptImg, null ));
        onDecryptionBtnClick(new ActionEvent());
        analysisBtn.setDisable(false);

         */
    }

    @FXML
    void createAlphabet(ActionEvent event) {
        File alphabet = new File("src/main/resources/files/alphabet.txt");

        try {
            if (alphabet.exists()) alphabet.delete();
            PrintWriter pw = new PrintWriter(alphabet);
            for (int i = 32; i <= 126; i++) {
                char c = (char) i;
                pw.print(c);
            }
            for (int i = 1040; i <= 1103; i++) {
                char c = (char) i;
                pw.print(c);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Файл алфавита не был создан");
            e.printStackTrace();
        }


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
    void realTimeSwitchCryptBtn(KeyEvent event) {
        if (origTextArea.getText().length() > 0) encryptBtn.setDisable(false);
        else encryptBtn.setDisable(true);
    }

    @FXML
    void initialize() {
        assert cryptTextArea != null : "fx:id=\"cryptTextArea\" was not injected: check your FXML file 'cryptText.fxml'.";
        assert deCryptTextArea != null : "fx:id=\"deCryptTextArea\" was not injected: check your FXML file 'cryptText.fxml'.";
        assert encryptBtn != null : "fx:id=\"encryptBtn\" was not injected: check your FXML file 'cryptText.fxml'.";
        assert generationCount != null : "fx:id=\"generationCount\" was not injected: check your FXML file 'cryptText.fxml'.";
        assert origTextArea != null : "fx:id=\"origTextArea\" was not injected: check your FXML file 'cryptText.fxml'.";
        assert switchControl != null : "fx:id=\"switchControl\" was not injected: check your FXML file 'cryptText.fxml'.";
        assert toMenuBtn != null : "fx:id=\"toMenuBtn\" was not injected: check your FXML file 'cryptText.fxml'.";

    }

}
