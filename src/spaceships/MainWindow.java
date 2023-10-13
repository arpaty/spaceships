/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceships;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Patrik Bogdan
 */
public class MainWindow extends JFrame {

    public MainWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JButton small = new JButton();
        small.setText("5 x 5");

        small.addActionListener(getActionListener(5));

        JButton medium = new JButton();
        medium.setText("7 x 7");

        medium.addActionListener(getActionListener(7));

        JButton large = new JButton();
        large.setText("9 x 9");

        large.addActionListener(getActionListener(9));

        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(small);
        getContentPane().add(medium);
        getContentPane().add(large);
    }

    /**
     *
     * @param size the size of the board (size * size)
     * @return opens a window with the given board size to play on
     */
    private ActionListener getActionListener(final int size) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = new Window(size, MainWindow.this);
                window.setVisible(true);
                MainWindow.this.setVisible(false);
            }
        };
    }
}
