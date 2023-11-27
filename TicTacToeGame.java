import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.InputMismatchException;


public class TicTacToeGame {
    private static final char UNO_PIECE = 'X';
    private static final char DOS_PIECE = 'O';
    private char[][] P;
    private boolean B = true;
    private List<String> uno; 
    private List<String> dos; 
    private Set<Set<String>> W;
    private Set<Set<String>> S;
    private Set<Set<String>> L;
    

    public TicTacToeGame() {
        Set<Integer> A = new HashSet<>(Arrays.asList(1, 2, 3));
        int size = A.size(); 

        P = new char[size][size]; 
        uno = new ArrayList<>();
        dos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                P[i][j] = '-';
            }
        }
        initializeWinningConditions();
    }

    private void initializeWinningConditions() {
        W = new HashSet<>();
        S = new HashSet<>();
        L = new HashSet<>();


        W.add(createSet("0,1", "1,1", "2,1")); 
        W.add(createSet("1,0", "1,1", "1,2")); 
        W.add(createSet("0,0", "1,1", "2,2")); 
        W.add(createSet("0,2", "1,1", "2,0")); 


        S.add(createSet("0,0", "1,1", "2,2"));


        L.addAll(W);
            for (Set<String> set : S) {
                L.remove(set);
        }
    }

    private Set<String> createSet(String... positions) {
        return new HashSet<>(Arrays.asList(positions));
    }   
    

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        while (!isGameOver()) {
            printP();
            int row = -1, col = -1;
            while (row < 0 || row >= 3 || col < 0 || col >= 3 || P[row][col] != '-') {
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

            P[row][col] = B ? UNO_PIECE : DOS_PIECE;
            updatePlayerPositions(B, row, col, true);

            if (isGameOver()) {
                break;
            }

            if (playerHasThreePieces(B)) {
                do {
                    System.out.println("Player " + (B ? "Uno" : "Dos") + ", remove a piece. Enter row and column (1-3, separated by a space): ");
                    row = scanner.nextInt() - 1;
                    col = scanner.nextInt() - 1;
                } while (row < 0 || row >= 3 || col < 0 || col >= 3 || P[row][col] != (B ? UNO_PIECE : DOS_PIECE));

                P[row][col] = '-';
                updatePlayerPositions(B, row, col, false);
            }

            B = !B;
        }

        printP();
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
        return checkWinningPositions(uno, L) || checkWinningPositions(dos, L);
    }

    private boolean checkWinningPositions(List<String> positions, Set<Set<String>> winningConditions) {
        Set<String> playerPositions = new HashSet<>(positions);
        for (Set<String> winCondition : winningConditions) {
            if (playerPositions.containsAll(winCondition)) {
                return true;
            }
        }
        return false;
    }

    private void printP() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(P[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        game.playGame();
    }
}
