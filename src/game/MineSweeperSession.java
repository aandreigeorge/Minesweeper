package game;

import java.util.Scanner;

public class MineSweeperSession {

    Minefield mineSweeper;
    private static final Scanner scanner = new Scanner(System.in);


    public void playGame() {

        System.out.print("How many mines do you want on the field? ");
        this.mineSweeper = new Minefield(9, 9, Integer.parseInt(scanner.nextLine()));

        do {
            System.out.print("Set/unset mine marks or claim a cell as free: ");
            mineSweeper.updateField(scanner.nextLine().split(" "));
        } while (!mineSweeper.gameWon() && !mineSweeper.gameLost());

        if (mineSweeper.gameWon()) {
            System.out.println("Congratulations! You found all the mines!");
        } else if (mineSweeper.gameLost()) {
            System.out.println("You stepped on a mine and failed!");
        }
        scanner.close();
    }

}
