package nus.vttp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;

public class MiniMaxMain {
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> board = generateBoard();
        printBoard(board);
        
        boolean player = true;
        Scanner scan = new Scanner(System.in);
        while (true) {
            String line = scan.nextLine();
            if (!possibleMoves(board).isEmpty()) {
                setMove(board, line, player);
                printBoard(board);
                if (checkWin(board, player) == 1) {
                    System.out.println("Player win");
                    break;
                }
                
                player = false;
                String bestMove = findBestMove(board);
                setMove(board, bestMove, player);
                printBoard(board);
                if (checkWin(board, player) == -1) {
                    System.out.println("CPU win");
                    break;
                }
                player = true;
            } else {
                if (checkWin(board, player) == 0) {
                    printBoard(board);
                    System.out.println("Tie game");
                    break;
                }
            }
        }
        scan.close();
    }

    public static void setMove(ArrayList<ArrayList<String>> board, String line, boolean player) {
        String[] parts = line.split("");
        int var1 = Integer.valueOf(parts[0]);
        int var2 = Integer.valueOf(parts[1]);
        if (player == true) {
            board.get(var1).set(var2, "X");
        } else if (player == false) {
            board.get(var1).set(var2, "O");
        }

    }

    public static int miniMax(ArrayList<ArrayList<String>> board, int depth, boolean isMaximizing) {
        // Check terminal states
        int score = checkWin(board, !isMaximizing); // true for maximizer, false for minimizer
        if (score == 1)
            return 10 - depth; // Maximizing player wins
        if (score == -1)
            return depth - 10; // Minimizing player wins
        if (possibleMoves(board).isEmpty())
            return 0; // Tie

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (String move : possibleMoves(board)) {
                ArrayList<ArrayList<String>> newBoard = tryMove(board, move, true);
                int eval = miniMax(newBoard, depth + 1, false); // Recursive call for minimizing
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (String move : possibleMoves(board)) {
                ArrayList<ArrayList<String>> newBoard = tryMove(board, move, false);
                int eval = miniMax(newBoard, depth + 1, true); // Recursive call for maximizing
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    // Helper to find the best move
    public static String findBestMove(ArrayList<ArrayList<String>> board) {
        int bestScore = Integer.MIN_VALUE;
        String bestMove = null;
        for (String move : possibleMoves(board)) {
            ArrayList<ArrayList<String>> newBoard = tryMove(board, move, false);
            int moveScore = miniMax(newBoard, 0, false); // Start minimax on opponent's move
            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestMove = move;
            }
        }
        return bestMove;
    }

    public static ArrayList<ArrayList<String>> tryMove(ArrayList<ArrayList<String>> board, String item,
            boolean player) {
        ArrayList<ArrayList<String>> newboard = board;
        String[] parts = item.split("");
        int var1 = Integer.valueOf(parts[0]);
        int var2 = Integer.valueOf(parts[1]);
        if (player) {
            newboard.get(var1).set(var2, "X");
        } else {
            newboard.get(var1).set(var2, "O");
        }
        return newboard;
    }

    public static ArrayList<String> possibleMoves(ArrayList<ArrayList<String>> board) {
        ArrayList<String> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(i).get(j).equalsIgnoreCase(" ")) {
                    moves.add(Integer.toString(i) + Integer.toString(j));
                }
            }
        }
        return moves;
    }

    public static int findDepth(ArrayList<ArrayList<String>> board) {
        int depth = 0;
        for (ArrayList<String> list : board) {
            for (String item : list) {
                if (!item.equalsIgnoreCase(" ")) {
                    depth++;
                }
            }
        }
        return depth;
    }

    public static void printBoard(ArrayList<ArrayList<String>> board) {
        for (ArrayList<String> item : board) {
            System.out.println(item);
        }
    }

    public static ArrayList<ArrayList<String>> generateBoard() {
        ArrayList<ArrayList<String>> board = new ArrayList<>();

        ArrayList<String> row1 = new ArrayList<>(Arrays.asList(" ", " ", " "));
        ArrayList<String> row2 = new ArrayList<>(Arrays.asList(" ", " ", " "));
        ArrayList<String> row3 = new ArrayList<>(Arrays.asList(" ", " ", " "));

        board.add(row1);
        board.add(row2);
        board.add(row3);

        return board;
    }

    public static int checkWin(ArrayList<ArrayList<String>> board, boolean player) {
        ArrayList<String> rowWinX = new ArrayList<>(Arrays.asList("X", "X", "X"));
        ArrayList<String> rowWinO = new ArrayList<>(Arrays.asList("O", "O", "O"));
        String pos00 = board.get(0).get(0);
        String pos01 = board.get(0).get(1);
        String pos02 = board.get(0).get(2);
        String pos10 = board.get(1).get(0);
        String pos11 = board.get(1).get(1);
        String pos12 = board.get(1).get(2);
        String pos20 = board.get(2).get(0);
        String pos21 = board.get(2).get(1);
        String pos22 = board.get(2).get(2);
        // row win
        if (board.get(0).equals(rowWinX)
                || board.get(1).equals(rowWinX)
                || board.get(2).equals(rowWinX)) {
            return 1;
        } else if ((board.get(0).equals(rowWinO)
                || board.get(1).equals(rowWinO)
                || board.get(2).equals(rowWinO))) {
            return -1;
        } // column win
        else if (!pos00.equalsIgnoreCase(" ")
                && ((pos00.equalsIgnoreCase(pos10) && pos00.equalsIgnoreCase(pos20))
                        || (pos01.equalsIgnoreCase(pos11) && pos00.equalsIgnoreCase(pos21))
                        || (pos02.equalsIgnoreCase(pos12) && pos00.equalsIgnoreCase(pos22)))) {
            if (player) {
                return 1;
            } else {
                return -1;
            }
        } // diagnoal win
        else if (!pos11.equalsIgnoreCase(" ")
                && ((pos11.equalsIgnoreCase(pos00) && pos11.equalsIgnoreCase(pos22))
                        || (pos11.equalsIgnoreCase(pos02) && pos11.equalsIgnoreCase(pos20)))) {
            if (player) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }
}
