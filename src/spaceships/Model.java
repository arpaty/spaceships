/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Patrik Bogdan
 */
public final class Model {

    private int size;
    private FieldElements[][] table;

    private Player p1;
    
    private Player p2;

    private Player actualPlayer;
    private BlackHole b;
    
    public Model(int size) {
        this.size = size;
        table = new FieldElements[size][size];

        int[][] playerOneShips;
        int[][] playerTwoShips;

        switch (size) {
            case 5 -> {
                b = new BlackHole(2, 2);

                p1 = new Player("Player 1");
                playerOneShips = new int[][]{{0, 0}, {1, 1}, {4, 0}, {3, 1}};
                addShipPositions("Player 1", playerOneShips);

                p2 = new Player("Player 2");
                playerTwoShips = new int[][]{{0, 4}, {1, 3}, {3, 3}, {4, 4}};
                addShipPositions("Player 2", playerTwoShips);

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        table[i][j] = FieldElements.EMPTY;

                        if (checkIfBlackHole(i, j)) {
                            table[i][j] = FieldElements.BLACK_HOLE;
                        }

                        fillShipPositions(table, playerOneShips, i, j, FieldElements.PLAYER_ONE);
                        fillShipPositions(table, playerTwoShips, i, j, FieldElements.PLAYER_TWO);
                    }
                }
            }
            case 7 -> {
                b = new BlackHole(3, 3);

                p1 = new Player("Player 1");
                playerOneShips = new int[][]{{0, 0}, {1, 1}, {2, 2}, {4, 2}, {5, 1}, {6, 0}};
                addShipPositions("Player 1", playerOneShips);

                p2 = new Player("Player 2");
                playerTwoShips = new int[][]{{0, 6}, {1, 5}, {2, 4}, {4, 4}, {5, 5}, {6, 6}};
                addShipPositions("Player 2", playerTwoShips);

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        table[i][j] = FieldElements.EMPTY;

                        if (checkIfBlackHole(i, j)) {
                            table[i][j] = FieldElements.BLACK_HOLE;
                        }

                        fillShipPositions(table, playerOneShips, i, j, FieldElements.PLAYER_ONE);
                        fillShipPositions(table, playerTwoShips, i, j, FieldElements.PLAYER_TWO);
                    }
                }
            }
            case 9 -> {
                b = new BlackHole(4, 4);

                p1 = new Player("Player 1");
                playerOneShips = new int[][]{{0, 0}, {1, 1}, {2, 2}, {3, 3}, {5, 3}, {6, 2}, {7, 1}, {8, 0}};
                addShipPositions("Player 1", playerOneShips);

                p2 = new Player("Player 2");
                playerTwoShips = new int[][]{{0, 8}, {1, 7}, {2, 6}, {3, 5}, {5, 5}, {6, 6}, {7, 7}, {8, 8}};
                addShipPositions("Player 2", playerTwoShips);

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        table[i][j] = FieldElements.EMPTY;

                        if (checkIfBlackHole(i, j)) {
                            table[i][j] = FieldElements.BLACK_HOLE;
                        }

                        fillShipPositions(table, playerOneShips, i, j, FieldElements.PLAYER_ONE);
                        fillShipPositions(table, playerTwoShips, i, j, FieldElements.PLAYER_TWO);
                    }
                }
            }
        }
        actualPlayer = p1;
    }

    /**
     *
     * @param shipPositions the player's ships
     * @param p the player in question stores the player's positions of their
     * ships for further interaction
     */
    private void addShipPositions(String playerName, int[][] shipPositions) {
        if(playerName.equals("Player 1")) {
            for (int i = 0; i < size - 1; i++) {
                p1.getShipPositions().add(shipPositions[i]);
            }
        }
        
        else if(playerName.equals("Player 2")) {
            for (int i = 0; i < size - 1; i++) {
                p2.getShipPositions().add(shipPositions[i]);
            }
        }
    }

    /**
     *
     * @param table the main table which represents the field of the game
     * @param playerShips the player's ships
     * @param i row index
     * @param j column index
     * @param playerName the player's name fills the game field in accordance to
     * where the player's ships are located
     */
    private void fillShipPositions(FieldElements[][] table, int[][] playerShips,
            int i, int j, FieldElements p) {
        for (int k = 0; k < playerShips.length; k++) {
            if (i == playerShips[k][0] && j == playerShips[k][1]) {
                if(p.equals(FieldElements.PLAYER_ONE))
                    table[i][j] = FieldElements.PLAYER_ONE;
                else if(p.equals(FieldElements.PLAYER_TWO)) 
                    table[i][j] = FieldElements.PLAYER_TWO;
            }
        }
    }

    /**
     *
     * @param fromTo the locations from where the player stepped from and to
     * changes the player's ship's location in accordance to the player's step
     * if the player steps into the black hole, their ship with which they
     * stepped into it gets removed from the game and they receive a point
     */
    public void actualStep(int[][] fromTo) {
        int rowFrom = fromTo[0][0];
        int colFrom = fromTo[0][1];

        int rowTo = fromTo[1][0];
        int colTo = fromTo[1][1];
        
        for (int i = 0; i < getActualPlayer().getShipPositions().size(); i++) {
            if (Arrays.equals(getActualPlayer().getShipPositions().get(i), (fromTo[0]))) {
                getActualPlayer().getShipPositions().set(i, fromTo[1]);
            }
        }

        if (checkIfBlackHole(rowTo, colTo)) {
            table[rowFrom][colFrom] = FieldElements.EMPTY;
            table[rowTo][colTo] = FieldElements.BLACK_HOLE;
            actualPlayer.getShipPositions().remove(fromTo[0]);
            actualPlayer.givePoint();
        } else {
            table[rowFrom][colFrom] = FieldElements.EMPTY;
            if (getActualPlayer().equals(p1)) {
                table[rowTo][colTo] = FieldElements.PLAYER_ONE;
            }
            
            else if (getActualPlayer().equals(p2)) {
                table[rowTo][colTo] = FieldElements.PLAYER_TWO;
            }
        }
    }

    /**
     *
     * @param row row index of the player's ship with which they want to step
     * with
     * @param column column index of the player's ship with which they want to
     * step with
     * @return the possible options where the player can step to
     */
    public HashMap<Integer, int[]> step(int row, int column) {
        HashMap<Integer, int[]> directions = new HashMap<>();

        int[][] stepOptions = {{row - 1, column}, {row + 1, column},
        {row, column + 1}, {row, column - 1}};

        for (int i = 0; i < stepOptions.length; i++) {
            if (checkIfInsideBoard(i, stepOptions)) {
                for (int j = 0; j < stepOptions[i].length; j++) {
                    if (checkIfBlackHole(stepOptions[i][0], stepOptions[i][1])) {
                        directions.put(i, stepOptions[i]);
                    }

                    if (!(checkIfOccupied(stepOptions[i][0], stepOptions[i][1]))) {
                        directions.put(i, stepOptions[i]);
                    }
                }
            }
        }

        for (int currentDirection : directions.keySet()) {
            int[] newField;
            int newValue;
            switch (currentDirection) {
                case 0 -> {
                    newValue = getClosestEmptyField(row, column, "up");
                    newField = new int[]{newValue, column};

                    directions.replace(currentDirection, newField);
                }
                case 1 -> {
                    newValue = getClosestEmptyField(row, column, "down");
                    newField = new int[]{newValue, column};

                    directions.replace(currentDirection, newField);
                }
                case 2 -> {
                    newValue = getClosestEmptyField(row, column, "right");
                    newField = new int[]{row, newValue};

                    directions.replace(currentDirection, newField);
                }
                case 3 -> {
                    newValue = getClosestEmptyField(row, column, "left");
                    newField = new int[]{row, newValue};

                    directions.replace(currentDirection, newField);
                }
            }
        }
        
        return directions;
    }

    /**
     *
     * @param row row index of the step's direction
     * @param col column index of the step's direction
     * @param direction the actual direction
     * @return the closest row or column index where the player can actually
     * step to
     */
    private int getClosestEmptyField(int row, int col, String direction) {
        int newPos;

        switch (direction) {
            case "up" -> {
                newPos = row - 1;
                while ((newPos > -1 && newPos < size)
                        && !(checkIfOccupied(newPos, col))) {
                    if (newPos == b.getRow() && col == b.getColumn()) {
                        return newPos;
                    }
                    newPos--;
                }
                return newPos + 1;
            }
            case "down" -> {
                newPos = row + 1;
                while ((newPos > -1 && newPos < size) && !(checkIfOccupied(newPos, col))) {
                    if (newPos == b.getRow() && col == b.getColumn()) {
                        return newPos;
                    }
                    newPos++;
                }
                return newPos - 1;
            }
            case "right" -> {
                newPos = col + 1;
                while ((newPos > -1 && newPos < size) && !(checkIfOccupied(row, newPos))) {
                    if (row == b.getRow() && newPos == b.getColumn()) {
                        return newPos;
                    }
                    newPos++;
                }
                return newPos - 1;
            }
            case "left" -> {
                newPos = col - 1;
                while ((newPos > -1 && newPos < size) && !(checkIfOccupied(row, newPos))) {
                    if (row == b.getRow() && newPos == b.getColumn()) {
                        return newPos;
                    }
                    newPos--;
                }
                return newPos + 1;
            }
            default -> {
                return 0;
            }

        }
    }

    /**
     *
     * @param row row index
     * @param stepOptions the potential steps
     * @return whether the given step would be inside the playing field or not
     */
    private boolean checkIfInsideBoard(int row, int[][] stepOptions) {
        return stepOptions[row][0] > -1 && stepOptions[row][1] > - 1 && stepOptions[row][0] < size && stepOptions[row][1] < size;
    }

    /**
     *
     * @param value row index
     * @param field column index
     * @return whether the given field is empty or occupied it also treats the
     * black hole as unoccupied
     */
    private boolean checkIfOccupied(int row, int column) {
        switch (table[row][column]) {
            case EMPTY:
                return false;
            case BLACK_HOLE:
                return false;
            default:
                return true;
        }
    }

    /**
     *
     * @param row row index
     * @param column column index
     * @return whether the given field is the black hole or not
     */
    private boolean checkIfBlackHole(int row, int column) {
        return row == b.getRow() && column == b.getColumn();
    }

    /**
     *
     * @return whether the game condition has been met
     */
    public boolean winCondition() {
        return actualPlayer.getPoints() == ((size - 1) / 2);
    }

    /**
     *
     * @return the winner of the game whether the game condition has been met
     */
    public Player findWinner() {
        if (winCondition()) {
            return actualPlayer;
        }

        return new Player();
    }

    public ArrayList<int[]> getPlayerOneShips() {
        return p1.getShipPositions();
    }

    public ArrayList<int[]> getPlayerTwoShips() {
        return p2.getShipPositions();
    }

    public String getPlayerName() {
        return actualPlayer.getName();
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }
    
    public void setActualPlayer(Player nextPlayer) {
        this.actualPlayer = nextPlayer;
    }

    public Player getPlayerOne() {
        return p1;
    }

    public Player getPlayerTwo() {
        return p2;
    }

    public BlackHole getBlackHole() {
        return b;
    }
}
