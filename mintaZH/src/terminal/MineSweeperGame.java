package terminal;

import GUI.Coordinates;

import java.util.HashSet;

public class MineSweeperGame {

    private char[][] map;
    private Minefield minefield;
    private int mineCount;
    private int checkedFieldCount;
    private int width;
    private int height;

    public MineSweeperGame(int mineCount, int width, int height) {
        if (mineCount < 1 || width < 1 || height < 1 )
            throw new IllegalArgumentException("Input numbers can't be less than 1!");
        else if(mineCount > width*height)
            throw new IllegalArgumentException("Number of mines can't be more than the number of fields!");

        this.mineCount = mineCount;
        this.width = width;
        this.height = height;
        reset(mineCount, width, height);
    }

    public void reset(int mineCount, int width, int height) {
        checkedFieldCount = 0;
        minefield = new Minefield(mineCount, width, height);
        map = new char[height][width];
        for (int row = 0; row < height; row++)
            for (int column = 0; column < width; column++)
                map[row][column] = ' ';
    }

    public int check(int x, int y) {

        if (x < 1 || x > height || y < 1 || y > width) throw new IllegalArgumentException("Wrong coordinates!");

        int neighbourMines;
        neighbourMines = minefield.neighbourMineCount(x, y);
        if(neighbourMines > 0) {
            map[x - 1][y - 1] = (char) ('0' + (char) neighbourMines);
            checkedFieldCount++;
        }
        else if (neighbourMines == 0) {
            map[x - 1][y - 1] = (char) ('0' + (char) neighbourMines);
            checkedFieldCount++;

            int startPosX = (x - 1 < 1) ? x : x - 1;
            int startPosY = (y - 1 < 1) ? y : y - 1;
            int endPosX = (x + 1 > height) ? x : x + 1;
            int endPosY = (y + 1 > width) ? y : y + 1;

            for (int i = startPosX; i <= endPosX; i++)
                for (int j = startPosY; j <= endPosY; j++) {
                    int mines = minefield.neighbourMineCount(i, j);
                    map[i - 1][j - 1] = (char) ('0' + (char) mines);
                    checkedFieldCount++;
                }
        }
        return neighbourMines;
    }

    public boolean isCleared() {
        return checkedFieldCount == width * height - mineCount;
    }

    public void printState() {
        System.out.print("   |");
        for (int i = 1; i <= width; i++) {
            System.out.printf("%3d|", i);
        }
        System.out.println();
        System.out.print("---+");
        for (int i = 1; i <= width; i++) {
            System.out.print("---+");
        }
        System.out.println();
        for (int row = 0; row < map.length; row++) {
            System.out.printf("%3d|", row + 1);
            for (int field = 0; field < map[row].length; field++) {
                System.out.printf("%3c|", map[row][field]);
            }
            System.out.print("\n---+");
            for (int i = 1; i <= width; i++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }
}
