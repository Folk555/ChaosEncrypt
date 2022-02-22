package com.chaoticencoder;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CryptGrayImgAnalysis {

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
                UACI += (Math.abs((myImage.getRGB(i,j) & 0xff) - (compareImg.getRGB(i,j) & 0xff))) / 255.0;
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
            if (pixProbility[i] != 0) H += pixProbility[i]*Math.log(1/pixProbility[i])/Math.log(2);
            //в джаве нет штатного log  по произольному основанию поэтому юзаем формулу log a (b)=log c (b) / log c (a)
        }
        return H;
    }

    static public double[] correlation(BufferedImage myImage) {

        double[] correlation = new double[3];

        double n1 = 0, n2 = 0, n3 = 0, n4 = 0, mean_pix=0;
        for (int i = 0; i < myImage.getWidth(); i++)
            for (int j = 0; j < myImage.getHeight(); j++) {
                mean_pix += myImage.getRGB(i,j) & 0xff;
            }
        mean_pix /= myImage.getHeight()*myImage.getWidth()*255;// среднее арифметическое первого канала пикселя


        //Horizontally
        for (int i = 0; i < myImage.getWidth(); i++) {
            n4 += n2 * n3;
            n2 = 0;
            n3 = 0;
            for (int j = 0; j < myImage.getHeight(); j++) {
                if (j + 1 > myImage.getWidth() - 1) continue;
                n1 += ((myImage.getRGB(i,j) & 0xff) - mean_pix) * ((myImage.getRGB(i,j+1) & 0xff) - mean_pix);
                n2 += Math.pow(((myImage.getRGB(i,j) & 0xff) - mean_pix),2);
                n3 += Math.pow(((myImage.getRGB(i,j+1) & 0xff) - mean_pix), 2);
            }
        }
        correlation[0] = n1 / n4;


        //Vertically
        n1 = 0;
        n2 = 0;
        n3 = 0;
        n4 = 0;
        for (int i = 0; i < myImage.getWidth(); i++) {
            n4 += n2 * n3;
            n2 = 0;
            n3 = 0;
            for (int j = 0; j < myImage.getHeight(); j++) {
                if (j + 1 > myImage.getHeight() - 1) continue;
                n1 += ((myImage.getRGB(j, i) & 0xff) - mean_pix) * ((myImage.getRGB(j + 1, i) & 0xff) - mean_pix);
                n2 += Math.pow(((myImage.getRGB(j, i) & 0xff) - mean_pix), 2);
                n3 += Math.pow(((myImage.getRGB(j + 1, i) & 0xff) - mean_pix), 2);
            }
        }
        correlation[1] = n1 / n4;


        //Diagonally
        n1 = 0;
        n2 = 0;
        n3 = 0;
        n4 = 0;
        // кореляция высчитывается в два этапа:
        // этап 1: вычисляем разницу пикселей между левым верхним и правым нижним пикселем на диагонале
        //   правый верхний пиксель картинки первая диагональ, диагональ картинки - последняя диагональ в 1ом этапе
        for (int j = myImage.getWidth(); j >= 0; j--) {  // Идем с последнего столбика к первому
            //счетчики для "бегущей строки" по диагонали
            int c = 0;  //столбик
            int i = 0;  //Строка
            n4 += n2 * n3;
            n2 = 0;
            n3 = 0;
            while ((j + c < myImage.getWidth() - 1) && (i + 1 < myImage.getHeight() - 1)) {  //С первой строки к последней
                n1 += ((myImage.getRGB(i, j + c) & 0xff) - mean_pix) *((myImage.getRGB(i + 1, j + c + 1) & 0xff) - mean_pix);
                n2 += Math.pow(((myImage.getRGB(i, j + c) & 0xff) - mean_pix), 2);
                n3 += Math.pow(((myImage.getRGB(i + 1, j + c + 1) & 0xff) - mean_pix), 2);
                c += 1;
                i += 1;
            }
        }
        // Этап 2: диагональ картинки - первая диагональ, нижний левый пиксель последняя
        for (int i = 1; i < myImage.getHeight(); i++) { // Идем с первой строки к последней. Главную диагональ пропускаем, так как был этап 1
            int c = 0;
            int ii = 0;
            n4 += n2 * n3;
            n2 = 0;
            n3 = 0;
            while ((c < myImage.getWidth() - 1) && (i + ii < myImage.getHeight() - 1)) {  //С первого столбика к последнему
                n1 += ((myImage.getRGB(i + ii, c) & 0xff) - mean_pix) * ((myImage.getRGB(i + ii + 1, c + 1) & 0xff) - mean_pix);
                n2 += Math.pow(((myImage.getRGB(i + ii, c) & 0xff) - mean_pix),2) * Math.pow(((myImage.getRGB(i + ii + 1, c + 1) & 0xff) - mean_pix), 2);
                n3 += Math.pow(((myImage.getRGB(i + ii + 1, c + 1) & 0xff) - mean_pix), 2);
                c += 1;
                ii += 1;
            }
        }
        correlation[2] = n1 / n4;

        return correlation;
    }

    static public int[] invariant(BufferedImage myImage) {
        int[] pixelCount = new int[256];
        for (int i = 0; i < 256; i++) pixelCount[i] = 0;
        for (int i = 0; i < myImage.getWidth(); i++)
            for (int j = 0; j < myImage.getHeight(); j++)
                pixelCount[myImage.getRGB(i, j) & 0xff] += 1;
        return pixelCount;
    }


}
