package game;

import java.util.Random;

class Minefield {

    private final Cell[][] cells;
    private boolean gameLost;
    private final int noOfRows, noOfColumns, noOfMines;
    private int noOfFoundMines, noOfMarkedCells, noOfOpenCells;


    Minefield(int noOfRows, int noOfColumns, int noOfMines) {
        this.cells = new Cell[noOfRows][noOfColumns];
        this.noOfFoundMines = noOfMarkedCells = noOfOpenCells = 0;
        this.gameLost = false;
        this.noOfRows = noOfRows;
        this.noOfColumns = noOfColumns;
        this.noOfMines = noOfMines;
        initializeField();
    }

    void initializeField() {

        for (int row = 0; row < noOfRows; row++) {
            for (int column = 0; column < noOfColumns; column++) {
                cells[row][column] = new Cell();
            }
        }
        placeMines();
        printField();
    }

    private void placeMines() {

        int noOfPlacedMines = 0;
        while (noOfPlacedMines < noOfMines) {
            Random random = new Random();
            int rowToPlace = random.nextInt(noOfRows);
            int columnToPlace = random.nextInt(noOfColumns);
            if (!cells[rowToPlace][columnToPlace].isMine()) {
                cells[rowToPlace][columnToPlace].setAsMine(true);
                noOfPlacedMines++;
            }
        }
    }

    private void printField() {

        System.out.println();

        System.out.print(" |");
        for (int column = 1; column <= noOfColumns; column++) {
            System.out.print(column);
        }
        System.out.println("|");

        System.out.println("-|" + "-".repeat(noOfColumns) + "|");

        for (int row = 0; row < noOfRows; row++) {
            System.out.print(row + 1 + "|");
            for (int column = 0; column < noOfColumns; column++) {
                if (cells[row][column].isOpen()) {
                    if (cells[row][column].isMine()) {
                        System.out.print('X');
                    } else {
                        int noOfMinesAround = countNeighboringMines(row, column);
                        if (noOfMinesAround == 0) {
                            System.out.print('/');
                        } else {
                            System.out.print(noOfMinesAround);
                        }
                    }
                } else if (cells[row][column].isMarked()) {
                    System.out.print('*');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println("|");
        }

        System.out.println("-|" + "-".repeat(noOfColumns) + "|");
    }

    void updateField(String[] input) {

        int column = Integer.parseInt(input[0]) - 1;
        int row = Integer.parseInt(input[1]) - 1;
        String command = input[2];

        if (command.equalsIgnoreCase("mine")) {

            if (!cells[row][column].isMarked()) {
                cells[row][column].setAsMarked(true);
                noOfMarkedCells++;
                if(cells[row][column].isMine()){
                    noOfFoundMines++;
                }
            } else {
                cells[row][column].setAsMarked(false);
                noOfMarkedCells--;
                if(cells[row][column].isMine()){
                    noOfFoundMines--;
                }
            }
        }

        if (command.equalsIgnoreCase("free")) {

            if (!cells[row][column].isMine() && !cells[row][column].isOpen()) {
                cells[row][column].setAsOpen(true);
                noOfOpenCells++;
                if (countNeighboringMines(row, column) == 0) {
                    exploreSurroundingCells(row, column);
                }

            } else if (cells[row][column].isMine()) {
                for (Cell[] rowOfCells : cells) {
                    for (Cell indidualCell : rowOfCells) {
                        if (indidualCell.isMine()) {
                            indidualCell.setAsOpen(true);
                        }
                    }
                }
                this.gameLost = true;
            }
        }
        printField();
    }

    void exploreSurroundingCells(int currentCellRow, int currentCellColumn) {

        if (countNeighboringMines(currentCellRow, currentCellColumn) == 0) {
            cells[currentCellRow][currentCellColumn].setAsOpen(true);
            noOfOpenCells++;
            for (int row = Math.max(0, currentCellRow - 1); row <= Math.min(noOfRows - 1, currentCellRow + 1); row++) {
                for (int column = Math.max(0, currentCellColumn - 1); column <= Math.min(noOfColumns - 1, currentCellColumn + 1); column++) {
                    if (!cells[row][column].isOpen()) {
                        exploreSurroundingCells(row, column);
                    }
                }
            }
        } else {
            cells[currentCellRow][currentCellColumn].setAsOpen(true);
            noOfOpenCells++;
        }
    }

    private int countNeighboringMines(int currentRow, int currentColumn) {

        int mineCount = 0;

        for (int row = Math.max(0, currentRow - 1); row < Math.min(noOfRows, currentRow + 2); row++) {
            for (int column = Math.max(0, currentColumn - 1); column < Math.min(noOfColumns, currentColumn + 2); column++) {
                if (row == currentRow && column == currentColumn) {
                    continue;
                }
                if (cells[row][column].isMine()) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    boolean gameWon() {
        boolean allMinesMarked = (noOfFoundMines == noOfMines) && (noOfMarkedCells == 0);
        boolean allSafeCellsOpened = (noOfOpenCells == cells.length - noOfMines);
        return allMinesMarked || allSafeCellsOpened;
    }

    boolean gameLost() {
        return this.gameLost;
    }

}