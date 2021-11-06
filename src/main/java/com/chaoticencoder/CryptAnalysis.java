package com.chaoticencoder;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CryptAnalysis {

    static public double NPCR(BufferedImage myImage, BufferedImage compareImg) {

        double NPCR = 0;
        for (int i = 0; i < myImage.getWidth(); i++)
            for (int j = 0; j < myImage.getHeight(); j++)
                if (myImage.getRGB(i, j) != compareImg.getRGB(i, j)) NPCR += 1;
        NPCR /= myImage.getWidth() * myImage.getHeight();
        return NPCR;
    }

    static public double UACI(BufferedImage myImage, BufferedImage compareImg) {

        double UACI = 0;
        for (int i = 0; i < myImage.getWidth(); i++)
            for (int j = 0; j < myImage.getHeight(); j++) {
                UACI += Math.abs((myImage.getRGB(i,j) & 0xff) - (compareImg.getRGB(i,j) & 0xff));
            }
        UACI /= myImage.getWidth() * myImage.getHeight();
        return UACI;
    }

    static public double entropy(BufferedImage myImage) {

        double[] pixProbility = new double[256];
        for (int i = 0; i < 256; i++) pixProbility[i]=0;
        for (int i = 0; i < myImage.getWidth(); i++)
            for (int j = 0; j < myImage.getHeight(); j++)
                pixProbility[myImage.getRGB(i,j) & 0xff] += 1;
        double H=0;
        for (int i = 0; i < 256; i++) {
            pixProbility[i] /= myImage.getWidth() * myImage.getHeight();
            if (pixProbility[i] != 0) H += pixProbility[i]*Math.log2(1/pixProbility[i]);
        }
        return H;
    }

}
