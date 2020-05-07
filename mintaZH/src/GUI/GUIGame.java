package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class GUIGame extends JFrame implements ActionListener {

    private JPanel headpanel;
    private JPanel contentpanel;
    private JPanel mainpanel;
    private JButton[][] fields;
    private JButton reset;

    private MineSweeperGame game;

    public GUIGame(String title) {
        super(title);
        mainpanel = new JPanel();
        setContentPane(mainpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        reset();
    }

    public void reset() {
        setVisible(false);

        initGame();
        initGUI();

        setVisible(true);
    }

    private void initGame() {
        int width = numberInputDialog("How wide should the map be?");
        int height = numberInputDialog("How high should the map be?");
        int mineCount = numberInputDialog("How many mines should be there?");
        if (mineCount > width * height) {
            JOptionPane.showMessageDialog(null, "Number of mines can't be more mines than number of fields!", "Error", JOptionPane.ERROR_MESSAGE);
            initGame();
        }
        else game = new MineSweeperGame(new Dimension(width, height), mineCount);
    }

    private int numberInputDialog(String msg) {
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

    private void initGUI(){

        mainpanel.removeAll();

        reset = new JButton("Reset");
        reset.addActionListener(this);

        headpanel = new JPanel(new BorderLayout(50, 50));
        headpanel.add(reset, BorderLayout.CENTER);

        contentpanel = new JPanel(new GridLayout(game.getDim().height, game.getDim().width));
        buildContent();

        mainpanel.setLayout(new BorderLayout());
        mainpanel.add(headpanel, BorderLayout.NORTH);
        mainpanel.add(contentpanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void buildContent(){
        int height = game.getDim().height;
        int width = game.getDim().width;
        fields = new JButton[height][width];
        for (int x = 0; x < height; x++)
            for (int y = 0; y < width; y++) {
                fields[x][y] = new JButton("?");
                fields[x][y].setSize(100, 100);
                contentpanel.add(fields[x][y]);
                fields[x][y].addActionListener(this);
            }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();

        if (button == reset) reset();
        else {
            if (game.check(getButtonCoordinates(button)) == Field.DETONATED) gameOverMessage();
            else {
                updateState();
                if (game.isCleared()) winMessage();
            }
        }
    }

    private void updateState() {
        HashSet<Field> sweptFields = game.getSweptFields();
        int x, y;
        for (Field sweptField : sweptFields) {
            x = sweptField.getCoordinates().getX();
            y = sweptField.getCoordinates().getY();
            fields[x][y].setText(Integer.toString(sweptField.getState()));
            fields[x][y].setEnabled(false);
        }
    }

    public Coordinates getButtonCoordinates(JButton field) {
        for (int x = 0; x < fields.length; x++) {
            for (int y = 0; y < fields[x].length; y++) {
                if (fields[x][y] == field) return new Coordinates(x, y);
            }
        }
        return null;
    }

    public void winMessage() {
        if (JOptionPane.showConfirmDialog(null, "Congratulations, you have won! Would you like to play again?") ==
                JOptionPane.YES_OPTION) {
            reset();
        } else System.exit(0);
    }

    public void gameOverMessage() {
        if (JOptionPane.showConfirmDialog(null, "You have lost! Would you like to play again?") ==
                JOptionPane.YES_OPTION) {
            reset();
        } else System.exit(0);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new GUIGame("Mine Sweeper"));
    }
}
