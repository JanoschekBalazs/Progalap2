package terminal;

import terminal.MineSweeperGame;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TerminalGame {

    public static void main(String[] args) {
        MineSweeperGame game = null;
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("How many mines should be there?");
            int mineCount = sc.nextInt();
            System.out.println("Width and height of the game:");
            int width = sc.nextInt();
            int height = sc.nextInt();
            game = new MineSweeperGame(mineCount, width, height);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            main(null);
        } catch (InputMismatchException e) {
            System.out.println("Input must be a positive integer!");
            main(null);
        }
        int x, y;
        boolean newGame = true;
        while (!game.isCleared() && newGame) {
            try {
                sc = new Scanner(System.in);
                System.out.println("Next guess:");
                x = sc.nextInt();
                y = sc.nextInt();
                if (game.check(x, y) == -1) {
                    System.out.print("Time to listen to Astronomia from Tony Igy. Wanna play another game?");
                    if (sc.next().matches("[nN]o")) newGame = false;
                } else {
                    game.printState();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch(InputMismatchException e) {
                System.out.println("Input is not a number!");
            }
        }
        if (game.isCleared()) {
            System.out.println("Congrats, and thanks for playing!");
        } else {
            System.out.println("Maybe next time, bb.");
        }
    }
}
