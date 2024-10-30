import java.util.*;

public class GridGame {

    // Represents an empty cell on the board
    private static final char EMPTY = '\u0000';

    // Constant values representing player
    private static final int MAX = 1;  // Maximizing player
    private static final int MIN = -1; // Minimizing player

    // The game board
    private char[][] board = new char[3][3];

    // Current player, initially MAX
    private int currentPlayer = MAX;

    // Memorization cache for storing board states
    private Map<String, Integer> memo = new HashMap<>(); // Memorization cache

    // Main method to start the game
    public static void main(String[] args) {
        GridGame game = new GridGame();
        game.runGame();
    }

    // Runs the game loop
    private void runGame() {
        Scanner scanner = new Scanner(System.in);
        initializeBoard();
        printBoard();

        while (true) {
            // Maximizing player's turn
            if (currentPlayer == MAX) {
                bestMove();
            } else {
                // Minimizing player's turn
                System.out.println("Player MIN's turn.");
                System.out.println("Enter row and column to place, and the letter (e.g., 1 1 C):");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                char letter = scanner.next().charAt(0);

                // Check if the move is valid
                if (canPlace(row, col, letter)) {
                    board[row][col] = letter;
                } else {
                    System.out.println("Invalid move, try again.");
                    continue;
                }
            }

            printBoard();

            // Check for win or tie
            if (checkWin()) {
                System.out.println("Player " + (currentPlayer == MAX ? MAX : MIN) + " wins!");
                break;
            }
            if (isBoardFull()) {
                System.out.println("It's a tie!");
                break;
            }

            // Switch players
            currentPlayer = -currentPlayer;
        }
        scanner.close();
    }

    // Initializes the game board
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Determines if a move is valid
    private boolean canPlace(int row, int col, char letter) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY;
    }

    // Computes the best move for the maximizing player
    private void bestMove() {
        // Placeholder for the best move calculation
        // Implementation of AI logic can be added here
    }

    // Checks if the current player has won
    private boolean checkWin() {
        String[] patterns = {"CSE", "ESC"};
        return checkLines(patterns);
    }

    // Checks rows, columns, and diagonals for win patterns
    private boolean checkLines(String[] patterns) {
        String rowString, colString, diag1 = "", diag2 = "";
        for (int i = 0; i < 3; i++) {
            rowString = "";
            colString = "";
            for (int j = 0; j < 3; j++) {
                rowString += board[i][j];
                colString += board[j][i];
            }

            // Check rows and columns for win patterns
            for (String pattern : patterns) {
                if (rowString.contains(pattern) || colString.contains(pattern))
                    return true;
            }

            // Build diagonals
            diag1 += board[i][i];
            diag2 += board[i][2 - i];
        }

        // Check diagonals for win patterns
        for (String pattern : patterns) {
            if (diag1.contains(pattern) || diag2.contains(pattern))
                return true;
        }
        return false;
    }

    // Checks if the board is full
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY)
                    return false;
            }
        }
        return true;
    }

    // Prints the current state of the board
    private void printBoard() {
        System.out.println("~board~");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                System.out.print((board[i][j] == EMPTY ? '_' : board[i][j]) + " ");
            System.out.println();
        }
    }
}