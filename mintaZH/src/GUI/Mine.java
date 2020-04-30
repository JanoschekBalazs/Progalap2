package GUI;

import java.util.Objects;

public class Mine {

    private int x;
    private int y;

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + (x + 1) + ", " + (y + 1) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mine)) return false;
        Mine mine = (Mine) o;
        return x == mine.x &&
                y == mine.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
