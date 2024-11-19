package Hangman;

import java.util.*;

public class Hangman {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("HANGMAN");

        while (true) {
            System.out.print("Type \"play\" to play the game, \"exit\" to quit: > ");
            String command = scanner.nextLine().trim();

            if (command.equals("play")) {
                playGame(scanner);
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Invalid input. Please type \"play\" or \"exit\".");
            }
        }
    }

    public static void playGame(Scanner scanner) {
        String[] words = {"python", "java", "javascript", "kotlin"};
        String wordToGuess = words[new Random().nextInt(words.length)];

        HashSet<Character> guessedLetters = new HashSet<>();
        char[] displayWord = new char[wordToGuess.length()];
        Arrays.fill(displayWord, '-');

        int remainingAttempts = 8;

        while (remainingAttempts > 0) {
            System.out.println(String.valueOf(displayWord));
            System.out.print("Input a letter: > ");
            String input = scanner.next().trim();

            // Перевірка введення
            if (input.length() != 1) {
                System.out.println("You should input a single letter");
                continue;
            }

            char guess = input.charAt(0);
            if (!Character.isLowerCase(guess)) {
                System.out.println("Please enter a lowercase English letter");
                continue;
            }

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
                scanner.nextLine();
                return;
            }
        }

        System.out.println("You lost!");
        scanner.nextLine();
    }
}
