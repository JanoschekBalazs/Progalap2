package GUI;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class MineSweeperGame {

    private Dimension dim;
    private HashSet<Field> sweptFields;
    private HashSet<Coordinates> mines;

    public MineSweeperGame(Dimension dim, int mineCount) {

        this.dim = dim;
        sweptFields = new HashSet<>();
        setMines(mineCount);
    }

    private void setMines(int mineCount) {
        mines = new HashSet<>();
        Random rand = new Random();
        while (mines.size() < mineCount) {
            mines.add(new Coordinates(rand.nextInt(dim.height), rand.nextInt(dim.width)));
        }
        System.err.println(this);
    }

    public int check(Coordinates coordinates) {
        try {
            int fieldState = neighbourMineCount(coordinates);
            sweptFields.add(new Field(coordinates, fieldState));
            sweep(coordinates);
            return fieldState;
        } catch (BummException e) {
            return Field.DETONATED;
        }
    }

    private int neighbourMineCount(Coordinates coordinates) throws BummException    {

        if (mines.contains(coordinates)) throw new BummException();

        int mineCount = 0;

        for (int x = coordinates.getX() - 1; x <= coordinates.getX() + 1; x++)
            for (int y = coordinates.getY() - 1; y <= coordinates.getY() + 1; y++)
                if (mines.contains(new Coordinates(x, y))) mineCount++;

        return mineCount;
    }

    private void sweep(Coordinates coordinates) {
        int fieldState = Field.HIDDEN;
        try {
            fieldState = neighbourMineCount(coordinates);
            sweptFields.add(new Field(coordinates, fieldState));
        } catch(BummException ignored){}

        if (fieldState == 0) {

            int startPosX = Math.max(coordinates.getX() - 1, 0);
            int startPosY = Math.max(coordinates.getY() - 1, 0);
            int endPosX = Math.min(coordinates.getX() + 1, dim.height - 1);
            int endPosY = Math.min(coordinates.getY() + 1, dim.width - 1);

            for (int x = startPosX; x <= endPosX; x++)
                for (int y = startPosY; y <= endPosY; y++) {
                    Coordinates field = new Coordinates(x, y);
                    if (!isSwept(field))
                        sweep(field);
                }
        }
    }

    private boolean isSwept(Coordinates coordinates) {
        for (Field sweptField : sweptFields) {
            if (sweptField.getCoordinates().equals(coordinates)) return true;
        }
        return false;
    }

    public boolean isCleared() {
        return sweptFields.size() == dim.height * dim.width - mines.size();
    }

    public HashSet<Field> getSweptFields() {
        return sweptFields;
    }

    public Dimension getDim(){
        return dim;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Coordinates of mines:").append("\n");
        for (Coordinates mine : mines) {
            s.append(mine).append("\n");
        }
        return s.toString();
    }

}
