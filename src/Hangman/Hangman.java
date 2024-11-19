package Hangman;

import java.util.*;

public class Hangman {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = {"python", "java", "javascript", "kotlin"};
        String wordToGuess = words[new Random().nextInt(words.length)];

        HashSet<Character> guessedLetters = new HashSet<>();
        char[] displayWord = new char[wordToGuess.length()];
        Arrays.fill(displayWord, '-');

        int remainingAttempts = 8;
        System.out.println("HANGMAN");

        while (remainingAttempts > 0) {
            System.out.println(String.valueOf(displayWord));
            System.out.print("Input a letter: > ");
            char guess = scanner.next().charAt(0);

            if (guessedLetters.contains(guess)) {
                System.out.println("You've already guessed this letter");
                continue;
            }

            guessedLetters.add(guess);

            if (wordToGuess.contains(String.valueOf(guess))) {
                for (int i = 0; i < wordToGuess.length(); i++) {
                    if (wordToGuess.charAt(i) == guess) {
                        displayWord[i] = guess;
                    }
                }
            } else {
                System.out.println("That letter doesn't appear in the word");
                remainingAttempts--;
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
