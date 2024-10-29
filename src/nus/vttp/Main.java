package nus.vttp;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<ArrayList> board = generateBoard();
        boolean player = true;
        int counter = 0;
        while (true) {
            String line = scan.nextLine();
            if (player) {
                String[] parts = line.split("");
                board.get(Integer.valueOf(parts[0])).set(Integer.valueOf(parts[1]), "X");
                player = false;
            } else {
                String[] parts = line.split("");
                board.get(Integer.valueOf(parts[0])).set(Integer.valueOf(parts[1]), "O");
                player = true;
            }
            board.forEach((n) -> {
                System.out.println(n);
            });
            int whoWin = checkWin(board);
            counter ++;
            if (whoWin == 1){
                System.out.println("Player1 wins");
                break;
            } else if (whoWin == -1){
                System.out.println("Player2 wins");
                break;
            } else if (whoWin == 0 && counter == 9){
                System.out.println("Tie game");
            }
        }
        scan.close();
    }

    //Generate the board
    @SuppressWarnings("rawtypes")
    public static ArrayList<ArrayList> generateBoard() {
        ArrayList<ArrayList> board = new ArrayList<>();
        ArrayList<String> row1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            row1.add("0" + Integer.toString(i));
        }
        ArrayList<String> row2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            row2.add("1" + Integer.toString(i));
        }
        ArrayList<String> row3 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            row3.add("2" + Integer.toString(i));
        }
        board.add(row1);
        board.add(row2);
        board.add(row3);
        board.forEach((n) -> {
            System.out.println(n);
        });
        return board;
    }
    //Check combination for win
    public static int checkWin(ArrayList<ArrayList> board) {
        String r1c1 = (String) board.get(0).get(0);
        String r1c2 = (String) board.get(0).get(1);
        String r1c3 = (String) board.get(0).get(2);
        String r2c1 = (String) board.get(1).get(0);
        String r2c2 = (String) board.get(1).get(1);
        String r2c3 = (String) board.get(1).get(2);
        String r3c1 = (String) board.get(2).get(0);
        String r3c2 = (String) board.get(2).get(1);
        String r3c3 = (String) board.get(2).get(2);
        if ((r1c1.equalsIgnoreCase(r1c2) && r1c1.equalsIgnoreCase(r1c3))
                || (r1c1.equalsIgnoreCase(r2c1) && r1c1.equalsIgnoreCase(r3c1))) {
                    if (r1c1.equalsIgnoreCase("X")){
                        return 1;
                    } else{
                        return -1;
                    }
        } else if ((r2c2.equalsIgnoreCase(r2c1) && r2c2.equalsIgnoreCase(r2c3))
                || (r2c2.equalsIgnoreCase(r1c2) && r2c2.equalsIgnoreCase(r3c2))
                || (r2c2.equalsIgnoreCase(r1c1) && r2c2.equalsIgnoreCase(r3c3))
                || (r2c2.equalsIgnoreCase(r1c3) && r2c2.equalsIgnoreCase(r3c1))) {
                    if (r2c2.equalsIgnoreCase("X")){
                        return 1;
                    } else{
                        return -1;
                    }
        } else if ((r3c3.equalsIgnoreCase(r2c3) && r3c3.equalsIgnoreCase(r1c3))
                || (r3c3.equalsIgnoreCase(r3c2) && r3c3.equalsIgnoreCase(r3c1))) {
                    if (r3c3.equalsIgnoreCase("X")){
                        return 1;
                    } else{
                        return -1;
                    }
        } else{
            return 0;
        }
    }
}