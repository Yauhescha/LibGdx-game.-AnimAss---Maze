package com.hescha.game.maz.generator;

import java.io.IOException;
import java.util.Scanner;

class MazeGeneratorJUnit {

    public static void main(String[] args) throws IOException {

        // Ask for the size of the maze
        Scanner scan = new Scanner(System.in);
        int size = 0;
        do {
            System.out.print("Input the size for the maze: ");
            while (!scan.hasNextInt()) {
                // Repeat message when bad input
                System.out.println("Needs a valid integer for maze size (3 or Higher)");
                System.out.print("Input the size for the maze: ");
                scan.next();
            }
            size = scan.nextInt();
        } while (size <= 2);
        scan.close();

        MazeGenerator.generate(size);

        System.out.println();
        System.out.println("======================");
        System.out.println("Program Completed!");
        System.out.println("======================");

    }

}
