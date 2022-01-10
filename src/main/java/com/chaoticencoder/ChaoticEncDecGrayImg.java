package com.chaoticencoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChaoticEncDecGrayImg {
    public BufferedImage origImg, cryptImg, deCryptImg;
    public int randseed, sz_imgX, sz_imgY, upr;
    private int gen;
    private ArrayList<Number> key;
    private double resslerParametrE=Constants.RESSLER_PARAMETR_E, resslerParametrA=Constants.RESSLER_PARAMETR_A,
            resslerParametrB=Constants.RESSLER_PARAMETR_B, resslerParametrR=Constants.RESSLER_PARAMETR_R;
    //Если пиксель не надо отслеживать вводим отрицательные(несуществующие координаты).
    int fromX=-11, fromY=-11; // задаем пиксель который надо отследить.

    private ChaoticEncDecGrayImg(){}

    static public class ChaoticEncDecImgBuilder{
        private BufferedImage origImg, cryptImg, deCryptImg;
        private int randseed=111, sz_imgX=512, sz_imgY=512, gen=0, upr=0;
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
        public ChaoticEncDecGrayImg build(){
            ChaoticEncDecGrayImg chaoticEncDecGrayImg = new ChaoticEncDecGrayImg();

            chaoticEncDecGrayImg.randseed = this.randseed;
            chaoticEncDecGrayImg.gen = this.gen;
            chaoticEncDecGrayImg.upr = this.upr;
            chaoticEncDecGrayImg.sz_imgX = sz_imgX;
            chaoticEncDecGrayImg.sz_imgY = sz_imgY;

            chaoticEncDecGrayImg.key = new ArrayList<>(gen*sz_imgX*sz_imgY);
            Random generator = new Random(randseed);
            for (int i = 0; i < gen*sz_imgX*sz_imgY; i++) {
                chaoticEncDecGrayImg.key.add(generator.nextDouble() * 10);
            }

            // меняем размер изображения
            // цветовую палитру оставить TYPE_INT_RGB, так как TYPE_BYTE_GRAY имеет меньшую палитру (оттенков серого)!!!!
            // По итогу при шифровке часть пикселей теряются после добавления шума, и не могут быть восстановлены при дешифровке,
            // так как прежний кодировки пикселя в цветовой палитре не существует, а новая не соответствует оригиналу. Появляются артефакты (контрастные пиксели).
            Image tmp = this.origImg.getScaledInstance(sz_imgX, sz_imgY, Image.SCALE_DEFAULT);
            BufferedImage resized = new BufferedImage(sz_imgX, sz_imgY, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            //в данном кейсе нам нужно черно-бьелое изображение, поэтому меняем его
            for (int x = 0; x < sz_imgX; x++) {
                for (int y = 0; y < sz_imgY; y++) {
                    Color color = new Color(resized.getRGB(x, y));
                    int blue = color.getBlue();
                    int red = color.getRed();
                    int green = color.getGreen();
                    int grey = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
                    int newRed = grey;
                    int newGreen = grey;
                    int newBlue = grey;
                    Color newColor = new Color(newRed, newGreen, newBlue);
                    resized.setRGB(x, y, newColor.getRGB());
                }
            }


            chaoticEncDecGrayImg.origImg = resized;

            return chaoticEncDecGrayImg;
        }
    }

    private ArrayList<Number> nextResler(ArrayList<Number> x0y0z0) {

        double x0=x0y0z0.get(0).doubleValue(), y0=x0y0z0.get(1).doubleValue(),
                z0=x0y0z0.get(2).doubleValue(); //инициализирую нач условия не как ArrayList
        double k[] = {1.0426,  -0.4943, -22.8945};
        //double k[] = {-0.0585,  0.0289, 0.4508};
        ArrayList<Number> xyz = new ArrayList<>(3);
        if (upr==0){

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

        for (int g=0; g<gen; ++g) {
            System.out.println("шифрование итерация: " + g);
            if (g==1)
                g=1;
            for (int i=0; i<sz_imgX; ++i) {
                for (int j=0; j<sz_imgY; ++j) {
                    ArrayList<Number> xyz = nextResler(new ArrayList<>(Arrays.asList(i,j,key.get(g*sz_imgX*sz_imgY+sz_imgX*i+j))));

                    int x = norm((int)Math.round(xyz.get(0).doubleValue()),'x');
                    int y = norm((int)Math.round(xyz.get(1).doubleValue()),'y');
                    int z = norm((int)Math.floor(xyz.get(2).doubleValue()),'z');

                    //swap - меняем местами + шум
                    int newGrayPixel = norm((cryptImg.getRGB(i,j) & 0xff) ^ z,'z');
                    Color pixel = new Color(newGrayPixel, newGrayPixel, newGrayPixel);
                    cryptImg.setRGB(i,j, cryptImg.getRGB(x,y));
                    cryptImg.setRGB(x,y, pixel.getRGB());

                    //внештатный механизм отслеживания пикселя
                    if (i==fromX && j==fromY){
                        fromX=x;
                        fromY=y;
                        System.out.println("ReslerZ: "+xyz.get(2).doubleValue()+"  normZ: "+z+"  пиксель: "+ norm(((cryptImg.getRGB(x,y) & 0xff) ^ z), 'z')+" ушел из "+i+" "+j+" в позицию "+x+" "+y+" изменен на "+newGrayPixel);
                        continue;
                    }
                    if (x==fromX && y==fromY) {
                        fromX=i;
                        fromY=j;
                        System.out.println("ReslerZ: "+xyz.get(2).doubleValue()+"  normZ: "+z+"  пиксель: "+(cryptImg.getRGB(i,j) & 0xff)+" ушел из "+x+" "+y+" в позицию "+i+" "+j);
                    }
                }
            }
        }
        deCryptImg = new BufferedImage(cryptImg.getColorModel(),
                cryptImg.copyData(null), cryptImg.getColorModel().isAlphaPremultiplied(), null);
    }

    public void decrypt(){

        for (int g=gen-1; g>=0; --g) {
            System.out.println("дешифрование итерация: " + g);
            for (int i=sz_imgX-1; i>=0; --i) {
                for (int j=sz_imgY-1; j>=0; --j) {

                    ArrayList<Number> xyz = nextResler(new ArrayList<>(Arrays.asList(i,j,key.get(g*sz_imgX*sz_imgY+sz_imgX*i+j))));

                    int x = norm((int)Math.round(xyz.get(0).doubleValue()),'x');
                    int y = norm((int)Math.round(xyz.get(1).doubleValue()),'y');
                    int z = norm((int)Math.floor(xyz.get(2).doubleValue()),'z');

                    //swap - меняем местами
                    int gray = norm((deCryptImg.getRGB(x,y) & 0xff) ^ z,'z');
                    Color pixel = new Color(gray, gray, gray);
                    deCryptImg.setRGB(x,y, deCryptImg.getRGB(i,j));
                    deCryptImg.setRGB(i,j, pixel.getRGB());

                    //внештатный механизм отслеживания пикселя
                    if (i==fromX && j==fromY){
                        fromX=x;
                        fromY=y;
                        System.out.println("ReslerZ: "+xyz.get(2).doubleValue()+"  normZ: "+z+"  пиксель: "+(deCryptImg.getRGB(x,y) & 0xff)+" ушел из "+i+" "+j+" в позицию "+x+" "+y);
                        continue;
                    }
                    if (x==fromX && y==fromY) {
                        fromX=i;
                        fromY=j;
                        System.out.println("ReslerZ: "+xyz.get(2).doubleValue()+"  normZ: "+z+"  пиксель: "+norm((deCryptImg.getRGB(i,j) & 0xff) ^ z,'z')+" ушел из "+x+" "+y+" в позицию "+i+" "+j+" изменен на "+gray);
                    }
                }
            }
        }
    }
    public void setNewGen(int gen){
        this.gen = gen;
        key = new ArrayList<>(gen*sz_imgX*sz_imgY);
        Random generator = new Random(randseed);
        for (int i = 0; i < gen*sz_imgX*sz_imgY; i++) {
            key.add(generator.nextDouble() * 10);
        }
    }

    public void setNewRarametrs(int gen, int e, int a, int b, int r, boolean upr){
        this.gen = gen;
        key = new ArrayList<>(gen*sz_imgX*sz_imgY);
        Random generator = new Random(randseed);
        for (int i = 0; i < gen*sz_imgX*sz_imgY; i++) {
            key.add(generator.nextDouble() * 10);
        }
    }
}
