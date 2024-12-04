package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;

public class Day04 extends Day {

    void main() {
        new Day04().run();
    }

    char[][] grid = stream().map(String::toCharArray).toArray(char[][]::new);
    int[][] directions = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };
    char[] xmas = { 'X', 'M', 'A', 'S' };

    @Override public Object part1() {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 'X') continue;
                for (int[] direction : directions) {
                    int newRow = r + direction[0];
                    int newCol = c + direction[1];
                    int i = 1;
                    while (inGrid(newRow, newCol) && i < xmas.length && grid[newRow][newCol] == xmas[i]) {
                        newRow += direction[0];
                        newCol += direction[1];
                        i++;
                    }
                    count += i == 4 ? 1 : 0;
                }
            }
        }
        return count;
    }

    @Override public Object part2() {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 'A' || onEdge(r, c)) continue;
                count += (grid[r - 1][c - 1] == 'M' && grid[r + 1][c + 1] == 'S' || grid[r - 1][c - 1] == 'S' && grid[r + 1][c + 1] == 'M') &&
                         (grid[r - 1][c + 1] == 'M' && grid[r + 1][c - 1] == 'S' || grid[r - 1][c + 1] == 'S' && grid[r + 1][c - 1] == 'M')
                         ? 1 : 0;
            }
        }
        return count;
    }

    private boolean inGrid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private boolean onEdge(int row, int col) {
        return row == 0 || col == 0 || row == grid.length - 1 || col == grid[0].length - 1;
    }

}
