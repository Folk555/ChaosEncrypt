package com.chaoticencoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class ChaoticEncDecText {
    private ArrayList<Character> origText = new ArrayList<>(), cryptText = new ArrayList<>(), deCryptText = new ArrayList<>();
    private int[] cryptTextCode, deCryptTextCode;
    private int square, gen;
    boolean upr;
    private ArrayList<Number> key;
    public ArrayList<Character> alphabet = new ArrayList<>();
    int fromX = -10, fromY = 1; // задаем пиксель который надо отследить.


    //Константы для нормирования
    public static final int NORM_OF_SQUARE = 0;
    public static final int NORM_OF_CHAR = 1;

    ChaoticEncDecText(String origText, int randseed, boolean upr, int gen) {
        this.upr = upr;
        this.gen = gen;
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/files/alphabet.txt"));
            String sAlphabet = br.readLine();
            for (char c : sAlphabet.toCharArray()) {
                alphabet.add(c);
            }
            Collections.sort(alphabet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        square = (int) Math.ceil(Math.sqrt(origText.length()));
        for (char i : origText.toCharArray()) {
            this.origText.add(i);
        }
        for (int i = origText.length(); i < square * square; i++) {
            this.origText.add(' ');
        }
        this.cryptText = (ArrayList<Character>) this.origText.clone();

        this.key = new ArrayList<>(gen * square * square);
        Random generator = new Random(randseed);
        for (int i = 0; i < gen * square * square; i++) {
            key.add(generator.nextDouble() * 10);
        }

        encrypt();
        decrypt();
    }

    private ArrayList<Number> nextResler(ArrayList<Number> x0y0z0) {
        double e = 0.05, a = 0.2, b = 0.2, r = 5.7;
        double x0 = x0y0z0.get(0).doubleValue(), y0 = x0y0z0.get(1).doubleValue(),
                z0 = x0y0z0.get(2).doubleValue(); //инициализирую нач условия не как ArrayList
        double k[] = {1.0426, -0.4943, -22.8945};
        ArrayList<Number> xyz = new ArrayList<>(3);
        if (upr) {
            xyz.add(-(y0 + z0));
            xyz.add((x0 + a * y0));
            xyz.add(b + z0 * (x0 - r));
            return xyz;
        } else {
            xyz.add(-(y0 + z0));
            xyz.add((x0 + a * y0) + k[0] * x0 + k[1] * y0 + k[2] * z0);
            xyz.add(b + z0 * (x0 - r) + k[0] * x0 + k[1] * y0 + k[2] * z0);
            return xyz;
        }
    }

    private int norm(int nn, int letter) {
        if (nn >= 0)
            switch (letter) {
                case NORM_OF_SQUARE:
                    if ((nn >= square)) nn = nn % square;
                    break;
                case NORM_OF_CHAR:
                    if (nn > alphabet.size() - 1) {
                        nn = (nn % alphabet.size());
                    }
                    break;
            }
        else
            switch (letter) {
                case NORM_OF_SQUARE:
                    nn = (square + (nn % -square));
                    if (nn == square) nn = 0;
                    break;
                case NORM_OF_CHAR:
                    //if (nn < 0) nn = (Math.abs(nn) % alphabet.size());
                    //nn = alphabet.size() - nn;
                    nn = (alphabet.size() + (nn % -(alphabet.size())));
                    if (nn == alphabet.size()) nn = 0;
                    break;
            }
        return nn;
    }

    public void encrypt() {

        /*
        for (int i=0; i < cryptText.size(); ++i){
            System.out.print(Collections.binarySearch(alphabet, cryptText.get(i))+"  ");//ищем i-ую букву в алфавите и пишем ее код
        }
         */

        for (int g = 0; g < gen; ++g) {
            System.out.println("шифрование итерация: " + (g+1));
            if (g == 1)
                g = 1;
            for (int i = 0; i < square; ++i) {
                for (int j = 0; j < square; ++j) {
                    ArrayList<Number> xyz = nextResler(new ArrayList<>(Arrays.asList(i, j, key.get(g * square * square + square * i + j))));

                    int x = norm((int) Math.round(xyz.get(0).doubleValue()), NORM_OF_SQUARE);
                    int y = norm((int) Math.round(xyz.get(1).doubleValue()), NORM_OF_SQUARE);
                    int z = norm((int) Math.floor(xyz.get(2).doubleValue()), NORM_OF_CHAR);

                    //swap - меняем местами + шум
                    char swap = alphabet.get(norm(Collections.binarySearch(alphabet, cryptText.get(i * square + j)) + z, NORM_OF_CHAR));
                    //Здесь нельзя использовать XOR так как norm зависет от размера алфавита. Алфавит может быть не полнобайтовым например 166 (не 255),
                    //в этом случае мы можем выйти за границу нормализации после XOR и потерять крайние левые биты.
                    cryptText.set(i * square + j, cryptText.get(x * square + y));
                    cryptText.set(x * square + y, swap);

                    //внештатный механизм отслеживания символа
                    if (i == fromX && j == fromY) {
                        fromX = x;
                        fromY = y;
                        System.out.println("ReslerZ: " + xyz.get(2).doubleValue() + "  normZ: " + z + "  символ: "
                                + (Collections.binarySearch(alphabet, cryptText.get(x * square + y)) ^ z)
                                + " ушел из " + i + " " + j + " в позицию " + x + " " + y + " изменен на " +
                                norm(Collections.binarySearch(alphabet, cryptText.get(x * square + y)), NORM_OF_CHAR));
                        continue;
                    }
                    if (x == fromX && y == fromY) {
                        fromX = i;
                        fromY = j;
                        System.out.println("ReslerZ: " + xyz.get(2).doubleValue() + "  normZ: " + z + "  символ: "
                                + norm(Collections.binarySearch(alphabet, cryptText.get(i * square + j)), NORM_OF_CHAR)
                                + " ушел из " + x + " " + y + " в позицию " + i + " " + j);
                    }
                }
            }
        }

    }

    public void decrypt() {

        deCryptText = (ArrayList<Character>) cryptText.clone();
        for (int g = gen - 1; g >= 0; --g) {
            System.out.println("дешифрование итерация: " + (g+1));
            for (int i = square - 1; i >= 0; --i) {
                for (int j = square - 1; j >= 0; --j) {

                    ArrayList<Number> xyz = nextResler(new ArrayList<>(Arrays.asList(i, j, key.get(g * square * square + square * i + j))));

                    int x = norm((int) Math.round(xyz.get(0).doubleValue()), NORM_OF_SQUARE);
                    int y = norm((int) Math.round(xyz.get(1).doubleValue()), NORM_OF_SQUARE);
                    int z = norm((int) Math.floor(xyz.get(2).doubleValue()), NORM_OF_CHAR);

                    //swap - меняем местами
                    //int p = norm(Collections.binarySearch(alphabet, deCryptText.get(x * square + y)) - z, NORM_OF_CHAR);
                    char swap = alphabet.get(norm(Collections.binarySearch(alphabet, deCryptText.get(x * square + y)) - z, NORM_OF_CHAR));
                    deCryptText.set(x * square + y, deCryptText.get(i * square + j));
                    deCryptText.set(i * square + j, swap);
                }
            }
        }

    }

    public String getCryptText() {
        if (cryptText.size() == 0) return null;
        String text = "";
        for (char i : cryptText) {
            text += i;
        }
        return text;
    }

    public int getSquare() {
        return square;
    }

    public String getDecryptText() {
        if (deCryptText.size() == 0) return null;
        String text = "";
        for (char i : deCryptText) {
            text += i;
        }
        return text;
    }

    public String getOrigText() {
        if (origText.size() == 0) return null;
        String text = "";
        for (char i : origText) {
            text += i;
        }
        return text;
    }

    public int[] getOrigTextCode() {
        int[] textCode = new int[square*square];
        for (int i = 0; i < square; ++i) {
            for (int j = 0; j < square; ++j) {
                textCode[i*square+j] = Collections.binarySearch(alphabet, origText.get(i * square + j));
            }
        }
        return textCode;
    }

    public int[] getCryptTextCode() {
        int[] textCode = new int[square*square];
        for (int i = 0; i < square; ++i) {
            for (int j = 0; j < square; ++j) {
                textCode[i*square+j] = Collections.binarySearch(alphabet, cryptText.get(i * square + j));
            }
        }
        return textCode;
    }

    public int[] getDeCryptTextCode() {
        int[] textCode = new int[square*square];
        for (int i = 0; i < square; ++i) {
            for (int j = 0; j < square; ++j) {
                textCode[i*square+j] = Collections.binarySearch(alphabet, deCryptText.get(i * square + j));
            }
        }
        return textCode;
    }
}