package com.chaoticencoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChaoticEncDecColorImg {
    public BufferedImage origImg, cryptImg, deCryptImg;
    public int randseed, sz_imgX, sz_imgY;
    boolean upr;
    private int gen;
    private ArrayList<Number> key1;
    private ArrayList<Number> key2;
    private double resslerParametrE = Constants.RESSLER_PARAMETR_E, resslerParametrA = Constants.RESSLER_PARAMETR_A,
            resslerParametrB = Constants.RESSLER_PARAMETR_B, resslerParametrR = Constants.RESSLER_PARAMETR_R;
    int fromX=-11, fromY=-2; // задаем пиксель который надо отследить.

    private ChaoticEncDecColorImg(){}

    static public class ChaoticEncDecImgBuilder{
        private BufferedImage origImg, cryptImg, deCryptImg;
        private int randseed = 111, sz_imgX = 512, sz_imgY = 512, gen = Constants.generationCount;
        boolean upr = Constants.switchControl;

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
        public ChaoticEncDecImgBuilder withUpr(boolean upr){
            this.upr = upr;
            return this;
        }
        public ChaoticEncDecImgBuilder withSize(int sz_imgX, int sz_imgY){
            this.sz_imgX = sz_imgX;
            this.sz_imgY = sz_imgY;
            return this;
        }
        public ChaoticEncDecColorImg build(){
            ChaoticEncDecColorImg chaoticEncDecColorImg = new ChaoticEncDecColorImg();

            chaoticEncDecColorImg.randseed = this.randseed;
            chaoticEncDecColorImg.gen = this.gen;
            chaoticEncDecColorImg.upr = this.upr;
            chaoticEncDecColorImg.sz_imgX = sz_imgX;
            chaoticEncDecColorImg.sz_imgY = sz_imgY;

            chaoticEncDecColorImg.key1 = new ArrayList<>(gen*sz_imgX*sz_imgY);
            Random generator = new Random(randseed);
            for (int i = 0; i < gen*sz_imgX*sz_imgY; i++) {
                chaoticEncDecColorImg.key1.add(generator.nextDouble() * 10);
            }

            chaoticEncDecColorImg.key2 = new ArrayList<>(gen*sz_imgX*sz_imgY);
            generator = new Random(randseed/2);
            for (int i = 0; i < gen*sz_imgX*sz_imgY; i++) {
                chaoticEncDecColorImg.key2.add(generator.nextDouble() * 10);
            }

            // меняем размер изображения
            Image tmp = this.origImg.getScaledInstance(sz_imgX, sz_imgY, Image.SCALE_DEFAULT);
            BufferedImage resized = new BufferedImage(sz_imgX, sz_imgY, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            chaoticEncDecColorImg.origImg = resized;

            return chaoticEncDecColorImg;
        }
    }

    private ArrayList<Number> nextResler(ArrayList<Number> x0y0z0) {

        double x0=x0y0z0.get(0).doubleValue(), y0=x0y0z0.get(1).doubleValue(),
                z0=x0y0z0.get(2).doubleValue(); //инициализирую нач условия не как ArrayList
        double k[] = {1.0426,  -0.4943, -22.8945};
        //double k[] = {-0.0585,  0.0289, 0.4508};
        ArrayList<Number> xyz = new ArrayList<>(3);
        if (!upr){

            xyz.add(-(y0 + z0));
            xyz.add((x0 + resslerParametrA * y0));
            xyz.add(resslerParametrB + z0*(x0 - resslerParametrR));

            /*
            xyz.add(x0-e*(y0 + z0));
            xyz.add(y0+e*(x0 + resslerParametrA * y0));
            xyz.add(z0+e*resslerParametrB + z0*e*(x0 - resslerParametrR));
            */


            return xyz;
        }
        else{

            xyz.add(-(y0 + z0));
            xyz.add((x0 + resslerParametrA * y0)+k[0]*x0+k[1]*y0+k[2]*z0);
            xyz.add(resslerParametrB + z0 * (x0 - resslerParametrR)+k[0]*x0+k[1]*y0+k[2]*z0);

            /*
            xyz.add(x0-e*(y0 + z0));
            xyz.add(y0+e*(x0 + resslerParametrA * y0)+k[0]*x0+k[1]*y0+k[2]*z0);
            xyz.add(z0+e*resslerParametrB + z0*e*(x0 - resslerParametrR)+k[0]*x0+k[1]*y0+k[2]*z0);
            */

            return xyz;
        }
    }
    private int norm(int nn, char letter)  {
        if (nn >= 0)
            switch (letter) {
                case 'x':
                    if ((nn >= sz_imgX)) nn = nn % sz_imgX;
                    break;
                case 'X':
                    if ((nn >= sz_imgX)) nn = nn % sz_imgX;
                    break;
                case 'y':
                    if ((nn >= sz_imgY)) nn = nn % sz_imgY;
                    break;
                case 'Y':
                    if ((nn >= sz_imgY)) nn = nn % sz_imgY;
                    break;
                case 'z':
                    if ((nn > 255)) nn = (nn % 255);
                    break;
                case 'Z':
                    if ((nn > 255)) nn = (nn % 255);
                    break;

            }
        else
            switch (letter) {
                case 'x':
                    nn = (sz_imgX + (nn % -sz_imgX));
                    if (nn==sz_imgX) nn=0;
                    break;
                case 'X':
                    nn = (sz_imgX + (nn % -sz_imgX));
                    if (nn==sz_imgX) nn=0;
                    break;
                case 'y':
                    nn = (sz_imgY + (nn % -sz_imgY));
                    if (nn==sz_imgY) nn=0;
                    break;
                case 'Y':
                    nn = (sz_imgY + (nn % -sz_imgY));
                    if (nn==sz_imgY) nn=0;
                    break;
                case 'z':
                    nn = (255 + (nn % -255));
                    break;
                case 'Z':
                    nn = (255 + (nn % -255)) ;
                    break;
            }
        return nn;
    }

    public void encrypt(){
        cryptImg = new BufferedImage(origImg.getColorModel(),
                origImg.copyData(null), origImg.getColorModel().isAlphaPremultiplied(), null);

        for (int g=0; g<gen; ++g) {
            System.out.println("шифрование итерация: " + g);
            for (int i=0; i<sz_imgX; ++i) {
                for (int j=0; j<sz_imgY; ++j) {

                    ArrayList<Number> xyz = nextResler(new ArrayList<>(Arrays.asList(i,j,key1.get(g*sz_imgX*sz_imgY+sz_imgX*i+j))));
                    int x = norm((int)Math.round(xyz.get(0).doubleValue()),'x');
                    int y = norm((int)Math.round(xyz.get(1).doubleValue()),'y');
                    int z = norm((int)Math.floor(xyz.get(2).doubleValue()),'z');
                    ArrayList<Number> xyzSecondary = nextResler(new ArrayList<>(Arrays.asList(i,j,key2.get(g*sz_imgX*sz_imgY+sz_imgX*i+j))));
                    int zSecondary = norm((int)Math.floor(xyzSecondary.get(2).doubleValue()),'z');

                    //swap - меняем местами + шум
                    int newBlueOfPixel = norm((cryptImg.getRGB(i,j) & 0xff) ^ z,'z');
                    int newGreenOfPixel = norm(((cryptImg.getRGB(i,j)>>8) & 0xff) ^ zSecondary,'z');
                    int newRedOfPixel = norm(((cryptImg.getRGB(i,j)>>16) & 0xff) ^ (z ^ zSecondary),'z');
                    Color pixel = new Color(newRedOfPixel, newGreenOfPixel, newBlueOfPixel);

                    cryptImg.setRGB(i,j, cryptImg.getRGB(x,y));
                    cryptImg.setRGB(x,y, pixel.getRGB());

                    //внештатный механизм отслеживания пикселя
                    if (i==fromX && j==fromY){
                        fromX=x;
                        fromY=y;
                        System.out.println("ReslerZ: "+xyz.get(2).doubleValue()+"  normZ: "+z+"  пиксель: "+ cryptImg.getRGB(x,y)+" ушел из "+i+" "+j+" в позицию "+x+" "+y+" изменен на " + cryptImg.getRGB(i,j));
                        continue;
                    }
                    if (x==fromX && y==fromY) {
                        fromX=i;
                        fromY=j;
                        System.out.println("ReslerZ: "+xyz.get(2).doubleValue()+"  normZ: "+z+"  пиксель: "+cryptImg.getRGB(i,j)+" ушел из "+x+" "+y+" в позицию "+i+" "+j);
                    }

                }
            }
        }
        //Делаем декрипту не пустой (для избежания ошибок 0ого указателя)
        deCryptImg = new BufferedImage(cryptImg.getColorModel(),
                cryptImg.copyData(null), cryptImg.getColorModel().isAlphaPremultiplied(), null);
    }

    public void decrypt(){

        deCryptImg = new BufferedImage(cryptImg.getColorModel(),
                cryptImg.copyData(null), cryptImg.getColorModel().isAlphaPremultiplied(), null);

        for (int g=gen-1; g>=0; --g) {
            System.out.println("дешифрование итерация: " + g);
            for (int i=sz_imgX-1; i>=0; --i) {
                for (int j=sz_imgY-1; j>=0; --j) {

                    ArrayList<Number> xyz = nextResler(new ArrayList<>(Arrays.asList(i,j,key1.get(g*sz_imgX*sz_imgY+sz_imgX*i+j))));
                    int x = norm((int)Math.round(xyz.get(0).doubleValue()),'x');
                    int y = norm((int)Math.round(xyz.get(1).doubleValue()),'y');
                    int z = norm((int)Math.floor(xyz.get(2).doubleValue()),'z');
                    ArrayList<Number> xyzSecondary = nextResler(new ArrayList<>(Arrays.asList(i,j,key2.get(g*sz_imgX*sz_imgY+sz_imgX*i+j))));
                    int zSecondary = norm((int)Math.floor(xyzSecondary.get(2).doubleValue()),'z');

                    //swap - меняем местами
                    int newBlueOfPixel = norm((deCryptImg.getRGB(x,y) & 0xff) ^ z,'z');
                    int newGreenOfPixel = norm((deCryptImg.getRGB(x,y)>>8 & 0xff) ^ zSecondary,'z');
                    int newRedOfPixel = norm((deCryptImg.getRGB(x,y)>>16 & 0xff) ^ (z ^ zSecondary),'z');
                    Color pixel = new Color(newRedOfPixel, newGreenOfPixel, newBlueOfPixel);

                    deCryptImg.setRGB(x,y, deCryptImg.getRGB(i,j));
                    deCryptImg.setRGB(i,j, pixel.getRGB());

                    if (i==fromX && j==fromY){
                        fromX=x;
                        fromY=y;
                        System.out.println("ReslerZ: "+xyz.get(2).doubleValue()+"  normZ: "+z+"  пиксель: "+deCryptImg.getRGB(x,y)+" ушел из "+i+" "+j+" в позицию "+x+" "+y);
                        continue;
                    }
                    if (x==fromX && y==fromY) {
                        fromX=i;
                        fromY=j;
                        System.out.println("ReslerZ: "+xyz.get(2).doubleValue()+"  normZ: "+z+"  пиксель: "+deCryptImg.getRGB(i,j)+" ушел из "+x+" "+y+" в позицию "+i+" "+j+" изменен на "+deCryptImg.getRGB(x,y));
                    }
                }
            }
        }
    }
    public void setNewGen(int gen){
        this.gen = gen;
        key1 = new ArrayList<>(gen*sz_imgX*sz_imgY);
        key2 = new ArrayList<>(gen*sz_imgX*sz_imgY);
        Random generator = new Random(randseed);
        for (int i = 0; i < gen*sz_imgX*sz_imgY; i++) {
            key1.add(generator.nextDouble() * 10);
        }
        generator = new Random(randseed/2);
        for (int i = 0; i < gen*sz_imgX*sz_imgY; i++) {
            key2.add(generator.nextDouble() * 10);
        }
    }

    public void setNewRarametrs(int gen, double e, double a, double b, double r, boolean upr){
        setNewGen(gen);
        this.upr = upr;
        this.resslerParametrE = e;
        this.resslerParametrA = a;
        this.resslerParametrB = b;
        this.resslerParametrR = r;

    }
}
