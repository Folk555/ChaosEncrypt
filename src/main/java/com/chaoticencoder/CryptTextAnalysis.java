package com.chaoticencoder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CryptTextAnalysis {

    static public double match(String mainText, String compareText) {

        double match = 0;
        int matrixSize = (int)Math.ceil(Math.sqrt(mainText.length()));
        for (int i = 0; i < matrixSize; i++)
            for (int j = 0; j < matrixSize; j++)
                if (mainText.charAt(i*matrixSize + j) == compareText.charAt(i*matrixSize + j)) match += 1;
        match = match / (matrixSize * matrixSize) * 100;
        return match;
    }

}
