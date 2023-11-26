import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class TicTacToeGame {
    private static final char UNO_PIECE = 'X';
    private static final char DOS_PIECE = 'O';
    private char[][] board;
    private boolean B = true;
    private List<String> uno; // Renamed to uno
    private List<String> dos; // Renamed to dos

    public TicTacToeGame() {
        board = new char[3][3];
        uno = new ArrayList<>();
        dos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        while (!isGameOver()) {
            printBoard();
            int row = -1, col = -1;
            while (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != '-') {
                try {
                    System.out.println("Player " + (B ? "Uno" : "Dos") + ", enter row and column (1-3, separated by a space): ");
                    row = scanner.nextInt() - 1;
                    col = scanner.nextInt() - 1;
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input. Please enter numbers only.");
                    scanner.next();
                    row = -1;
                    col = -1;
                }
            }

            board[row][col] = B ? UNO_PIECE : DOS_PIECE;
            updatePlayerPositions(B, row, col, true);

            if (isGameOver()) {
                break;
            }

            if (playerHasThreePieces(B)) {
                do {
                    System.out.println("Player " + (B ? "Uno" : "Dos") + ", remove a piece. Enter row and column (1-3, separated by a space): ");
                    row = scanner.nextInt() - 1;
                    col = scanner.nextInt() - 1;
                } while (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != (B ? UNO_PIECE : DOS_PIECE));

                board[row][col] = '-';
                updatePlayerPositions(B, row, col, false);
            }

            B = !B;
        }

        printBoard();
        System.out.println("Game Over! Player " + (B ? "Dos" : "Uno") + " wins!");
    }

    private void updatePlayerPositions(boolean isUno, int row, int col, boolean adding) {
        String position = row + "," + col;
        if (isUno) {
            if (adding) {
                uno.add(position);
            } else {
                uno.remove(position);
            }
        } else {
            if (adding) {
                dos.add(position);
            } else {
                dos.remove(position);
            }
        }
    }

    private boolean playerHasThreePieces(boolean isUno) {
        return (isUno ? uno.size() : dos.size()) >= 3;
    }

    private boolean isGameOver() {
        // Check winning conditions for both players
        return checkWinningPositions(uno) || checkWinningPositions(dos);
    }

    private boolean checkWinningPositions(List<String> positions) {
        String[] winningCombinations = {
            "0,1 1,1 2,1",
            "1,0 1,1 1,2",
            "0,2 1,1 2,0"
        };
    
        for (String winningCombination : winningCombinations) {
            boolean win = true;
            for (String pos : winningCombination.split(" ")) {
                if (!positions.contains(pos)) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }
        return false;
    }

    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        game.playGame();
    }
}
