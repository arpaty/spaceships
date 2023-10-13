/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceships;

/**
 *
 * @author Patrik Bogdan
 */
public class BlackHole {

    private final int row;
    private final int column;

    public BlackHole(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
