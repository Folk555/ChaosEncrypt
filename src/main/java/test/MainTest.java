package test;

import java.util.ArrayList;
import java.util.Random;

public class MainTest {
    public static void main(String[] args) {
        int randseed = 111;
        System.out.println(randseed/2);
        Random generator = new Random(randseed);
        for (int i = 0; i < 10; i++) {
            System.out.println(generator.nextDouble() * 10);
        }
    }
}
