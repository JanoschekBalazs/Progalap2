import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player {

    private final static int GOAL = 3;

    private String name;
    private String sign;
    private ArrayList<Point> points;

    public Player(String name, String sign) {
        this.name = name;
        this.sign = sign;
        points = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    public void selects(Point point) {
        points.add(point);
    }

    public boolean won() {
        return horizontalHit() || verticalHit() || diagonalHit();
    }

    private boolean diagonalHit() {
        for (int i = 0; i < points.size(); i++) {
            int score = 0;
            Point p0 = points.get(i);
            for (Point point : points) {
                if (
                        point.y == p0.y && point.x == p0.x ||
                                point.y == p0.y + 1 && point.x == p0.x + 1 ||
                                point.y == p0.y + 2 && point.x == p0.x + 2  ||
                                point.y == p0.y - 1 && point.x == p0.x + 1 ||
                                point.y == p0.y - 2 && point.x == p0.x + 2)
                    score++;
            }
            if(score == GOAL) return true;
        }
        return false;
    }

    private boolean horizontalHit(){
        for (int i = 0; i < points.size(); i++) {
            int score = 0;
            Point p0 = points.get(i);
            for (Point point : points) {
                if (point.x == p0.x) score++;
            }
            if(score == GOAL) return true;
        }
        return false;
    }

    private boolean verticalHit(){
        for (int i = 0; i < points.size(); i++) {
            int score = 0;
            Point p0 = points.get(i);
            for (Point point : points) {
                if (point.y == p0.y) score++;
            }
            if(score == GOAL) return true;
        }
        return false;
    }
}
