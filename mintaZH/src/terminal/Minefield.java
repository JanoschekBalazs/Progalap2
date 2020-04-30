package terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Minefield {

    private HashSet<Mine> minefield;

    public Minefield(int mineCount, int width, int height) {

        minefield = new HashSet<>();
        Random n = new Random();

        while (minefield.size() < mineCount)
            minefield.add(new Mine(1 + n.nextInt(height), 1 + n.nextInt(width)));

        System.err.println(this);
    }

    public int neighbourMineCount(int x, int y) {

        if (isOnMine(x, y)) return -1;

        int mineCount = 0;

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (isOnMine(x + i, y + j)) mineCount++;

        return mineCount;
    }

    public boolean isOnMine(int x, int y) {
        for (Mine mine : minefield)
            if (mine.getX() == x && mine.getY() == y) return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Coordinates of minefield:").append("\n");
        for (Mine mine : minefield) {
            s.append(mine).append("\n");
        }
        return s.toString();
    }

}

class BummException extends RuntimeException {

    public BummException(String msg) {
        super(msg);
    }
}
