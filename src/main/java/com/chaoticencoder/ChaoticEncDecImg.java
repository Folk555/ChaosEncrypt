package com.chaoticencoder;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChaoticEncDecImg {
    public BufferedImage origImg, cryptImg, deCryptImg;
    public int randseed, sz_imgX, sz_imgY, gen, upr;
    ArrayList<Number> key;

    private ChaoticEncDecImg(){}

    static public class ChaoticEncDecImgBuilder{
        private BufferedImage origImg, cryptImg, deCryptImg;
        private int randseed=111, sz_imgX=1024, sz_imgY=1024, gen=1, upr=1;
        ArrayList<Number> key;

        public ChaoticEncDecImgBuilder withImage(BufferedImage origImg){
            this.origImg = origImg;
            return this;
        }
        public ChaoticEncDecImgBuilder withRandseed(int randseed){
            this.randseed = randseed;
            return this;
        }
        public ChaoticEncDecImgBuilder withGen(int gen){
            this.gen = gen;
            return this;
        }
        public ChaoticEncDecImgBuilder withUpr(int upr){
            this.upr = upr;
            return this;
        }
        public ChaoticEncDecImgBuilder withSize(int sz_imgX, int sz_imgY){
            this.sz_imgX = sz_imgX;
            this.sz_imgY = sz_imgY;
            return this;
        }
        public ChaoticEncDecImg build(){
            ChaoticEncDecImg chaoticEncDecImg = new ChaoticEncDecImg();

            chaoticEncDecImg.randseed = this.randseed;
            chaoticEncDecImg.gen = this.gen;
            chaoticEncDecImg.upr = this.upr;
            chaoticEncDecImg.sz_imgX = sz_imgX;
            chaoticEncDecImg.sz_imgY = sz_imgY;

            chaoticEncDecImg.key = new ArrayList<>(sz_imgX*sz_imgY);
            Random generator = new Random(randseed);
            for (int i = 0; i < sz_imgX*sz_imgY; i++) {
                chaoticEncDecImg.key.add(generator.nextDouble() * 1);
            }

            // меняем размер изображения
            Image tmp = this.origImg.getScaledInstance(sz_imgX, sz_imgY, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(sz_imgX, sz_imgY, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            chaoticEncDecImg.origImg = resized;

            chaoticEncDecImg.cryptImg = new BufferedImage(chaoticEncDecImg.origImg.getColorModel(),
                    chaoticEncDecImg.origImg.copyData(null), chaoticEncDecImg.origImg.getColorModel().isAlphaPremultiplied(), null);

            return chaoticEncDecImg;
        }
    }

    private ArrayList<Number> nextResler(ArrayList<Number> x0y0z0) {
        double e=0.05, a=0.2, b=0.2, r=5.7;
        double x0=x0y0z0.get(0).doubleValue(), y0=x0y0z0.get(1).doubleValue(),
                z0=x0y0z0.get(2).doubleValue(); //инициализирую нач условия не как ArrayList
        double k[] = {1.0426,  -0.4943, -22.8945};
        ArrayList<Number> xyz = new ArrayList<>(3);
        if (upr==0){
            xyz.add(-(y0 + z0));
            xyz.add((x0 + a * y0));
            xyz.add(b + z0*(x0 - r));
            return xyz;
        }
        else{
            xyz.add(-(y0 + z0));
            xyz.add((x0 + a * y0)+k[0]*x0+k[1]*y0+k[2]*z0);
            xyz.add(b + z0 * (x0 - r)+k[0]*x0+k[1]*y0+k[2]*z0);
            return xyz;
        }
    }
    private int norm(int nn, char letter) {
        if (nn >= 0)
            switch (letter) {
                case 'x':
                    if ((nn >= sz_imgX)) nn = nn % sz_imgX;
                    break;
                case 'y':
                    if ((nn >= sz_imgY)) nn = nn % sz_imgY;
                    break;
            }
        else
            switch (letter) {
                case 'x':
                    nn = (sz_imgX - (nn % -sz_imgX)) % sz_imgX;
                    break;
                case 'y':
                    nn = (sz_imgY - (nn % -sz_imgY)) % sz_imgX;
                    break;
            }
        return nn;
    }

    public void encrypt(){

        for (int g=0; g<gen; ++g) {
            System.out.println("шифрование итерация: " + g);
            for (int i=0; i<sz_imgX; ++i) {
                for (int j=0; j<sz_imgY; ++j) {
                    ArrayList<Number> xyz = nextResler(new ArrayList<>(Arrays.asList(i,j,key.get(sz_imgX*i+j))));

                    int x = norm((int)Math.round(xyz.get(0).doubleValue()),'x');
                    int y = norm((int)Math.round(xyz.get(1).doubleValue()),'y');
                    System.out.println("x: "+x+"  y: "+y);

                    //swap - меняем местами
                    int gray = cryptImg.getRGB(i,j) & 0xff;
                    Color pixel = new Color(gray, gray, gray);
                    cryptImg.setRGB(i,j, cryptImg.getRGB(x,y));
                    cryptImg.setRGB(x,y, pixel.getRGB());
                }
            }
        }
        deCryptImg = new BufferedImage(cryptImg.getColorModel(),
                cryptImg.copyData(null), cryptImg.getColorModel().isAlphaPremultiplied(), null);
    }

}
