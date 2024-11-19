package Hangman;

import java.util.HashSet;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = {"python", "java", "javascript", "kotlin"};
        String wordToGuess = words[(int) (Math.random() * words.length)];

        HashSet<Character> guessedLetters = new HashSet<>();
        char[] displayWord = new char[wordToGuess.length()];
        for (int i = 0; i < displayWord.length; i++) {
            displayWord[i] = '-';
        }

        int attemptsLeft = 8;
        System.out.println("HANGMAN");

        while (attemptsLeft > 0) {
            System.out.println(String.valueOf(displayWord));
            System.out.print("Input a letter: > ");
            char guess = scanner.next().charAt(0);

            if (guessedLetters.contains(guess)) {
                System.out.println("You've already guessed this letter");
                continue;
            }

            guessedLetters.add(guess);
            boolean correctGuess = false;

            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == guess) {
                    displayWord[i] = guess;
                    correctGuess = true;
                }
            }

            if (!correctGuess) {
                System.out.println("That letter doesn't appear in the word");
                attemptsLeft--;
            }

            if (String.valueOf(displayWord).equals(wordToGuess)) {
                System.out.println("You guessed the word " + wordToGuess + "!");
                System.out.println("You survived!");
                return;
            }
        }

        System.out.println("You lost!");
    }
}
