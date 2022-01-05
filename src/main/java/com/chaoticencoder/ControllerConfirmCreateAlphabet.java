package com.chaoticencoder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerConfirmCreateAlphabet {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmBtnClick(ActionEvent event) {

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
            System.out.println("Ошибка. Файл алфавита не был создан");
            e.printStackTrace();
        }

    }


}
