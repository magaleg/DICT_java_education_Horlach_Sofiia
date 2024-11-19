package Hangman;

import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("HANGMAN");
        System.out.print("Guess the word: > ");

        String predefinedWord = "java";
        String guess = scanner.nextLine();

        if (guess.equals(predefinedWord)) {
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }
    }
}

