import java.util.Scanner;

public class TicTacToe {
    private char[][] board;
    private static final char EMPTY = ' ';
    private char currentPlayer;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private boolean isCellOccupied(int row, int col) {
        return board[row][col] != EMPTY;
    }

    private boolean isValidCoordinate(int coord) {
        return coord >= 1 && coord <= 3;
    }

    private void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true;
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    private boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe game = new TicTacToe();

        System.out.println("Welcome to Tic Tac Toe! Good luck!");
        game.printBoard();

        while (true) {
            System.out.println("Enter the coordinates for " + game.currentPlayer + ":");
            String[] input = scanner.nextLine().split(" ");

            if (input.length != 2) {
                System.out.println("You should enter numbers!");
                continue;
            }

            try {
                int row = Integer.parseInt(input[0]);
                int col = Integer.parseInt(input[1]);

                if (!game.isValidCoordinate(row) || !game.isValidCoordinate(col)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }

                row--;
                col--;

                if (game.isCellOccupied(row, col)) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                game.makeMove(row, col);
                game.printBoard();

                if (game.checkWin()) {
                    System.out.println(game.currentPlayer + " wins");
                    break;
                }

                if (game.isDraw()) {
                    System.out.println("Draw");
                    break;
                }

                game.switchPlayer();
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        }

        scanner.close();
    }
}
