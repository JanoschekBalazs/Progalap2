package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;

public class Minefield {

    private JButton[][] map;
    private HashSet<Coordinates> sweptFields;
    private HashSet<Mine> mines;
    private Dimension dim;

    public Minefield(Dimension dim, int mineCount, JPanel panel){

        this.dim = dim;
        buildMap(panel);
        armMines(mineCount);
        sweptFields = new HashSet<>();
    }

    public void buildMap(JPanel panel){
        map = new JButton[dim.height][dim.width];
        for (int x = 0; x < dim.height; x++)
            for (int y = 0; y < dim.width; y++) {
                map[x][y] = new JButton("?");
                map[x][y].setVisible(true);
                map[x][y].setMinimumSize(new Dimension(20, 20));
                panel.add(map[x][y]);
            }
    }

    public void armMines(int mineCount){
        mines = new HashSet<>();
        Random rand = new Random();
        while (mines.size() < mineCount){
            mines.add(new Mine(rand.nextInt(dim.height), rand.nextInt(dim.width)));
        }

        System.err.println(this);
    }

    public void sweep(Coordinates coordinates) {

        int mineCount = neighbourMineCount(coordinates);

        if (mineCount > 0) {
            setField(coordinates, String.valueOf(mineCount));
        } else if (mineCount == 0) {
            setField(coordinates, String.valueOf(mineCount));

            int startPosX = (coordinates.x - 1 < 0) ? coordinates.x : coordinates.x - 1;
            int startPosY = (coordinates.y - 1 < 0) ? coordinates.y : coordinates.y - 1;
            int endPosX = (coordinates.x + 1 > dim.height - 1) ? coordinates.x : coordinates.x + 1;
            int endPosY = (coordinates.y + 1 > dim.width - 1) ? coordinates.y : coordinates.y + 1;

            for (int x = startPosX; x <= endPosX; x++)
                for (int y = startPosY; y <= endPosY; y++) {
                    Coordinates field = new Coordinates(x, y);
                    if (!sweptFields.contains(field))
                        sweep(field);
                }
        }
    }

    public Coordinates findCoordinates(JButton field) {
        for (int x = 0; x < dim.height; x++) {
            for (int y = 0; y < dim.width; y++) {
                if (getField(x, y) == field) return new Coordinates(x, y);
            }
        }
        return null;
    }

    public JButton getField(int x, int y){
        return map[x][y];
    }

    public void setField(Coordinates coordinates, String value){
        map[coordinates.x][coordinates.y].setText(value);
        map[coordinates.x][coordinates.y].setEnabled(false);
        sweptFields.add(coordinates);
    }

    public boolean isCleared() {
        return sweptFields.size() == dim.height * dim.width - mines.size();
    }

    public int neighbourMineCount(Coordinates coordinates) {

        if (steppedOnMine(coordinates)) return -1;

        int mineCount = 0;
        for (int x = coordinates.x - 1; x <= coordinates.x + 1; x++)
            for (int y = coordinates.y - 1; y <= coordinates.y + 1; y++)
                if (steppedOnMine(new Coordinates(x, y))) mineCount++;
        return mineCount;
    }

    public boolean steppedOnMine(Coordinates coordinates) {
        for (Mine mine : mines)
            if (mine.getX() == coordinates.x && mine.getY() == coordinates.y) return true;
        return false;
    }

    public void addActionListener(ActionListener a){
        for (JButton[] row : map)
            for (JButton field : row)
                field.addActionListener(a);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Coordinates of mines:").append("\n");
        for (Mine mine : mines) {
            s.append(mine).append("\n");
        }
        return s.toString();
    }

}
