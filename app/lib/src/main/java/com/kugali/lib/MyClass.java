package com.kugali.lib;

import java.util.Scanner;

public class MyClass {
    private static final String TAG = MyClass.class.getSimpleName();
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            for (int i = 0; i < 8; i++) {
                int mountainH = in.nextInt(); // represents the height of one mountain.
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println("4"); // The index of the mountain to fire on.
        }    }
}