package com.chandler.aoc.util;

import java.util.Map;
import java.util.Objects;

public class Guard {
    private int row;
    private int col;
    private int orientation;
    private int turns;

    public Guard(int row, int col, int orientation, int turns) {
        this.row = row;
        this.col = col;
        this.orientation = orientation;
        this.turns = turns;
    }

    public static Guard at(Map.Entry<Point, Character> start) {
        return Guard.of(start.getKey().x(), start.getKey().y(), start.getValue());
    }

    public static Guard from(Guard guard) {
        return new Guard(guard.getRow(), guard.getCol(), guard.getOrientation(), guard.getTurns());
    }

    public static Guard of(int row, int col, char c) {
        int orientation = switch (c) {
            case '^' -> 0;
            case '>' -> 1;
            case 'v' -> 2;
            case '<' -> 3;
            default -> throw new IllegalArgumentException("Provided character is not a guard");
        };
        return new Guard(row, col, orientation, 1);
    }

    public void turnRight() {
        this.setOrientation((this.getOrientation() + 1) % 4);
    }

    public void move(char[][] grid, int[][] directions) {
        move(grid, directions, this.turns);
    }

    private void move(char[][] grid, int[][] directions, int turns) {
        for (int i = 0; i < turns; i++) {
            int rowAhead = this.getRow() + directions[this.getOrientation()][0];
            int colAhead = this.getCol() + directions[this.getOrientation()][1];

            if (isInGrid(grid, rowAhead, colAhead) && grid[rowAhead][colAhead] == '#') {
                turnRight();
            } else {
                this.setRow(rowAhead);
                this.setCol(colAhead);
            }
        }
    }

    public boolean isInGrid(char[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public boolean isInGrid(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public Guard withTurns(int turns) {
        setTurns(turns);
        return this;
    }

    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Guard guard = (Guard) o;
        return row == guard.row && col == guard.col && orientation == guard.orientation;
    }

    @Override public int hashCode() {
        return Objects.hash(row, col, orientation);
    }
}
