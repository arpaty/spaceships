/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceships;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Patrik Bogdan
 */
public class Window extends MainWindow {

    private int size;
    private Model model;
    private JLabel currentPlayer;
    private JLabel currentScore;
    private MainWindow mainWindow;
    private JButton[][] fields;
    private int[][] stepsFromTo = new int[2][2];

    public Window(int size, MainWindow mainWindow) {
        this.size = size;
        this.mainWindow = mainWindow;
        model = new Model(size);
        fields = new JButton[size][size];

        JPanel top = new JPanel();

        currentPlayer = new JLabel();
        updateLabelText();

        currentScore = new JLabel();
        updateCurrentScore();

        JButton newGameButton = new JButton();
        newGameButton.setText("New game");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        top.add(currentPlayer);
        top.add(newGameButton);
        top.add(currentScore);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                addButton(mainPanel, fields, i, j);
            }
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        addBlackHole();
        addSpaceShips();
        setResizable(false);

        disableFields(fields);
    }

    /**
     *
     * @param panel the main panel
     * @param fields the fields
     * @param i row index
     * @param j column index the game's "heart"
     */
    private void addButton(JPanel panel, JButton[][] fields,
            final int i, final int j) {
        final JButton button = new JButton();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actualPlayer = model.getPlayerName();
                HashMap<Integer, int[]> steps;
                switch (actualPlayer) {
                    case "Player 1" -> {
                        if (fields[i][j].getBackground() == Color.BLUE) {
                            steps = model.step(i, j);
                            if(steps.isEmpty()) {
                                fields[i][j].setEnabled(false);
                            }
                            else {
                               stepsFromTo[0][0] = i;
                               stepsFromTo[0][1] = j;
                               disableShips();
                               highLightSteps(steps); 
                            }
                        } else if (fields[i][j].getBackground() == Color.LIGHT_GRAY) {
                            stepsFromTo[1][0] = i;
                            stepsFromTo[1][1] = j;
                            model.actualStep(stepsFromTo);
                            changePositions(stepsFromTo);
                            model.setActualPlayer(model.getPlayerTwo());
                            clearSteps();
                            disableFields(fields);
                            updateCurrentScore();
                            updateLabelText();
                        } else if (fields[i][j].getBackground() == Color.BLACK) {
                            stepsFromTo[1][0] = i;
                            stepsFromTo[1][1] = j;
                            model.actualStep(stepsFromTo);
                            inBlackHole();
                            clearSteps();
                            checkIfGameEnded();
                            model.setActualPlayer(model.getPlayerTwo());
                            disableFields(fields);
                            updateCurrentScore();
                            updateLabelText();
                        }
                    }

                    case "Player 2" -> {
                        if (fields[i][j].getBackground() == Color.RED) {
                            steps = model.step(i, j);
                            if(steps.isEmpty()) {
                                fields[i][j].setEnabled(false);
                            }
                            else {
                               stepsFromTo[0][0] = i;
                               stepsFromTo[0][1] = j;
                               disableShips();
                               highLightSteps(steps);
                            }
                        } else if (fields[i][j].getBackground() == Color.LIGHT_GRAY) {
                            stepsFromTo[1][0] = i;
                            stepsFromTo[1][1] = j;
                            model.actualStep(stepsFromTo);
                            changePositions(stepsFromTo);
                            clearSteps();
                            model.setActualPlayer(model.getPlayerOne());
                            disableFields(fields);
                            updateCurrentScore();
                            updateLabelText();
                        } else if (fields[i][j].getBackground() == Color.BLACK) {
                            stepsFromTo[1][0] = i;
                            stepsFromTo[1][1] = j;
                            model.actualStep(stepsFromTo);
                            inBlackHole();
                            clearSteps();
                            checkIfGameEnded();
                            model.setActualPlayer(model.getPlayerOne());
                            disableFields(fields);
                            updateCurrentScore();
                            updateLabelText();
                        }
                    }
                }
            }
        });
        
        fields[i][j] = button;
        panel.add(button);
    }

    /**
     * visually adds each player's ships onto the playing field
     */
    private void addSpaceShips() {
        ArrayList<int[]> playerOneShips = model.getPlayerOneShips();
        ArrayList<int[]> playerTwoShips = model.getPlayerTwoShips();

        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                for (int k = 0; k < size - 1; k++) {
                    if (i == playerOneShips.get(k)[0] && j == playerOneShips.get(k)[1]) {
                        fields[i][j].setBackground(Color.BLUE);
                    }

                    if (i == playerTwoShips.get(k)[0] && j == playerTwoShips.get(k)[1]) {
                        fields[i][j].setBackground(Color.RED);
                    }
                }
            }
        }
    }

    /**
     * visually adds the black hole onto the playing field
     */
    private void addBlackHole() {
        BlackHole b;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (size) {
                    case 5 -> {
                        b = new BlackHole(2, 2);
                        if (i == b.getRow() && j == b.getColumn()) {
                            fields[i][j].setBackground(Color.BLACK);
                        }
                    }
                    case 7 -> {
                        b = new BlackHole(3, 3);
                        if (i == b.getRow() && j == b.getColumn()) {
                            fields[i][j].setBackground(Color.BLACK);
                        }
                    }
                    case 9 -> {
                        b = new BlackHole(4, 4);
                        if (i == b.getRow() && j == b.getColumn()) {
                            fields[i][j].setBackground(Color.BLACK);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param positions the locations from where the player stepped from and to
     * lets the player visually step onto another field
     */
    private void changePositions(int[][] positions) {
        int rowFrom = positions[0][0];
        int colFrom = positions[0][1];
        fields[rowFrom][colFrom].setBackground(Color.WHITE);

        int rowTo = positions[1][0];
        int colTo = positions[1][1];
        if (model.getActualPlayer().equals(model.getPlayerOne())) {
            fields[rowTo][colTo].setBackground(Color.BLUE);
        } else if (model.getActualPlayer().equals(model.getPlayerTwo())) {
            fields[rowTo][colTo].setBackground(Color.RED);
        }
    }

    /**
     * disables the player's ships
     */
    private void disableShips() {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                if (fields[i][j].getBackground() == Color.BLUE || fields[i][j].getBackground() == Color.RED) {
                    fields[i][j].setEnabled(false);
                }
            }
        }
    }

    /**
     * @param field the fields
     */
    private void disableFields(JButton[][] field) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                if (model.getActualPlayer().equals(model.getPlayerOne()) && fields[i][j].getBackground() == Color.BLUE) {
                    fields[i][j].setEnabled(true);
                } else if (model.getActualPlayer().equals(model.getPlayerTwo()) && fields[i][j].getBackground() == Color.RED) {
                    fields[i][j].setEnabled(true);
                } else {
                    fields[i][j].setEnabled(false);
                }
            }
        }
    }

    /**
     *
     * @param the possible steps from which the player can choose from
     * highlights the possible steps from which the player can choose from also,
     * reapplies the black hole's color if the player can step onto it
     */
    private void highLightSteps(HashMap<Integer, int[]> steps) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                for (int currDir : steps.keySet()) {
                    if (i == steps.get(currDir)[0] && j == steps.get(currDir)[1]) {
                        fields[i][j].setEnabled(true);
                        fields[i][j].setBackground(Color.LIGHT_GRAY);
                    }
                }

                if (i == model.getBlackHole().getRow() && j == model.getBlackHole().getColumn()) {
                    fields[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }

    /**
     * clears all highlighted steps
     */
    private void clearSteps() {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                if (fields[i][j].getBackground() == Color.LIGHT_GRAY) {
                    fields[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    /**
     * visually removes the player's ship with which they stepped into the black
     * hole
     */
    private void inBlackHole() {
        int rowFrom = stepsFromTo[0][0];
        int colFrom = stepsFromTo[0][1];

        fields[rowFrom][colFrom].setBackground(Color.WHITE);
    }

    /**
     * starts a new game
     */
    private void newGame() {
        MainWindow newWindow = new MainWindow();
        newWindow.setVisible(true);

        this.dispose();
    }

    /**
     * updates the actual player's text
     */
    private void updateLabelText() {
        currentPlayer.setText("Actual player: " + model.getActualPlayer());
    }

    /**
     * updates the actual player's score
     */
    private void updateCurrentScore() {
        currentScore.setText("Score: " + model.getActualPlayer().getPoints());
    }

    /**
     * checks whether the game has ended and shows a message indicating the end
     * of the game
     */
    private void checkIfGameEnded() {
        if (model.winCondition()) {
            Player winner = model.findWinner();
            showGameOverMessage(winner);
        }
    }

    /**
     *
     * @param winner the winner of the game displays the winner of the game and
     * starts a new one automatically
     */
    private void showGameOverMessage(Player winner) {
        JOptionPane.showMessageDialog(this,
                "Game over! The winner is: " + winner.getName());
        newGame();
    }
}
