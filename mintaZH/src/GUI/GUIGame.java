package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIGame extends JFrame implements ActionListener {

    private JPanel headpanel;
    private JPanel contentpanel;
    private JPanel mainpanel;
    private JButton reset;

    private Dimension dim;
    private int mineCount;
    private Minefield map;

    public GUIGame(String title) {
        super(title);
        mainpanel = new JPanel();
        setContentPane(mainpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        reset();
    }

    public void reset() {
        setVisible(false);
        mainpanel.removeAll();

        userInput();

        mainpanel.setLayout(new BorderLayout());

        headpanel = new JPanel(new BorderLayout(100, 50));
        reset = new JButton("Reset");
        reset.setMaximumSize(new Dimension(20, 20));
        reset.addActionListener(this);
        headpanel.add(reset, BorderLayout.CENTER);
        mainpanel.add(headpanel, BorderLayout.NORTH);

        contentpanel = new JPanel(new GridLayout(dim.height, dim.width));
        mainpanel.add(contentpanel, BorderLayout.CENTER);
        map = new Minefield(dim, mineCount, contentpanel);
        map.addActionListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void userInput() {
        int width = numberInputDialog("How wide should the map be?");
        int height = numberInputDialog("How heigh should the map be?");
        int mineCount = numberInputDialog("How many mines should be there?");
        if (mineCount > width * height) {
            JOptionPane.showMessageDialog(null, "Number of mines can't be more mines than number of fields!", "Error", JOptionPane.ERROR_MESSAGE);
            userInput();
        }
        dim = new Dimension(width, height);
        this.mineCount = mineCount;
    }

    public int numberInputDialog(String msg) {
        int number;
        String input = JOptionPane.showInputDialog(null, msg, "Minesweeper", JOptionPane.QUESTION_MESSAGE);
        try {
            if (input == null) System.exit(0);
            number = Integer.parseInt(input);
            if (number < 1) throw new NumberFormatException();
            return number;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "\"" + input + "\"" + " is not a positive integer!", "Error", JOptionPane.ERROR_MESSAGE);
            number = numberInputDialog(msg);
            return number;
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();

        if (button == reset) reset();
        else {
            Coordinates coordinates = map.findCoordinates(button);
            if (map.steppedOnMine(coordinates)) loseMessage();
            else map.sweep(coordinates);
            if (map.isCleared()) winMessage();
        }
    }

    public void winMessage() {
        if (JOptionPane.showConfirmDialog(null, "Congratulations, you have won! Would you like to play again?") ==
                JOptionPane.YES_OPTION) {
            reset();
        } else System.exit(0);
    }

    public void loseMessage() {
        if (JOptionPane.showConfirmDialog(null, "You have lost! Would you like to play again?") ==
                JOptionPane.YES_OPTION) {
            reset();
        } else System.exit(0);
    }

    public static void main(String[] args) {
        new GUIGame("Minesweeper");
    }
}
