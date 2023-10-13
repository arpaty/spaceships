/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceships;

import java.util.ArrayList;

/**
 *
 * @author Patrik Bogdan
 */
public class Player {
    private String playerName;
    private ArrayList<int[]> shipPositions;
    private int points = 0;
    
    public Player() {
        this.playerName = "Nobody";
    }
    
    public Player(String playerName) {
        this.playerName = playerName;
        this.shipPositions = new ArrayList<>();
    }

    public String getName() {
        return playerName;
    }

    public ArrayList<int[]> getShipPositions() {
        return shipPositions;
    }
    
    public int getPoints() {
        return points;
    }

    public void givePoint() {
        points += 1;
    }

    @Override
    public String toString() {
        return getName();
    }
}
