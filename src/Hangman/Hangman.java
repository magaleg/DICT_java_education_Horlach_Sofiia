package Hangman;

import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = {"python", "java", "javascript", "kotlin"};

        Random random = new Random();
        String randomWord = words[random.nextInt(words.length)];

        String hint = randomWord.substring(0, 2) + "-".repeat(randomWord.length() - 2);

        System.out.println("HANGMAN");
        System.out.println("Guess the word " + hint + ": > ");

        String guess = scanner.nextLine();

        if (guess.equals(randomWord)) {
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }
    }
}
