import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TicTacToe extends JFrame implements ActionListener {


    private Player p1, p2, currentPlayer;
    private JButton[][] fields;
    private JButton selectedField;
    private int turn;

    private JButton field11;
    private JButton field12;
    private JButton field13;
    private JButton field21;
    private JButton field22;
    private JButton field23;
    private JButton field31;
    private JButton field32;
    private JButton field33;

    private JPanel mainpanel;
    private JLabel playerInfo;


    public TicTacToe(String title) {
        super(title);
        reset();
    }

    public void reset(){
        setVisible(false);
        setContentPane(mainpanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setVisible(true);
        fields = new JButton[][]{
                {field11, field12, field13},
                {field21, field22, field23},
                {field31, field32, field33}
        };
        for (JButton[] row:fields)
            for (JButton field : row) {
                field.setText("");
                field.setEnabled(true);
            }

        p1 = new Player("Player 1", "O");
        p2 = new Player("Player 2", "X");
        currentPlayer = p1;
        playerInfo.setText(p1.getName());
        turn = 1;
        addActionListener();
    }

    private void switchPlayer() {
        if (currentPlayer.equals(p1)) {
            playerInfo.setText(p2.getName());
            currentPlayer = p2;
        } else if (currentPlayer.equals(p2)) {
            playerInfo.setText(p1.getName());
            currentPlayer = p1;
        }
        turn++;
    }

    private void addActionListener() {
        for (JButton[] row : fields) {
            for (JButton field : row) {
                field.addActionListener(this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedField = (JButton) e.getSource();
        selectedField.setText(currentPlayer.getSign());
        selectedField.setEnabled(false);
        checkResult();
        switchPlayer();
    }

    private void checkResult() {
        currentPlayer.selects(getPoint());
        if(currentPlayer.won()) {
            int reply = JOptionPane.showConfirmDialog(null, currentPlayer.getName() + " have won! Want to try again?", "TicTacToe", JOptionPane.YES_NO_CANCEL_OPTION);
            if(reply == JOptionPane.YES_OPTION) reset();
            else stop();
        } else if(turn == 9){
            int reply = JOptionPane.showConfirmDialog(null, "It's a tie! Want to try again?", "TicTacToe", JOptionPane.YES_NO_CANCEL_OPTION);
            if(reply == JOptionPane.YES_OPTION) reset();
            else stop();
        }
    }

    private Point getPoint() {
        for (int row = 0; row < fields.length; row++)
            for (int col = 0; col < fields.length; col++)
                if (fields[row][col] == selectedField) return new Point(row, col);
        return null;
    }

    private void stop() {
        dispose();
        System.exit(0);
    }
}

