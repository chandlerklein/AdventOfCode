package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Day14 extends Day {

    public static void main(String[] args) {
        new Day14().printParts();
    }

    @Override
    protected Object part1() {
        String[] lines = dayString().split("\r\n");

        char[][] grid = getGrid();
        parseAndDraw(lines, grid);

        printGrid(grid);

        return null;
    }

    private void parseAndDraw(String[] lines, char[][] grid) {
        Arrays.stream(lines)
              .map(InputLine::new)
              .map(InputLine::rockPaths)
              .forEach(rockPaths -> drawRockPaths(rockPaths, grid));
    }

    private void drawRockPaths(List<RockPath> rockPaths, char[][] grid) {
        for (int i = 0; i < rockPaths.size() - 1; i++) {
            int fromX = rockPaths.get(i).x();
            int fromY = rockPaths.get(i).y();

            int toX = rockPaths.get(i + 1).x();
            int toY = rockPaths.get(i + 1).y();

            while (fromX != toX || fromY != toY) {
                grid[fromX][fromY] = '#';
                if (fromX < toX) {
                    fromX++;
                } else if (fromY < toY) {
                    fromY++;
                } else if (fromX > toX) {
                    fromX--;
                } else {
                    fromY--;
                }
            }
            grid[fromX][fromY] = '#';
        }
    }

    private static void printGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            char[] chars = grid[i];
            System.out.printf("%-3d %s%n", i, Arrays.toString(chars));
        }
    }

    private char[][] getGrid() {
        char[][] grid = new char[500][600];
        Arrays.stream(grid).forEach(chars -> Arrays.fill(chars, '.'));
        grid[0][500] = '+';
        return grid;
    }

    private record InputLine(List<RockPath> rockPaths) {
        public InputLine(String line) {
            this(Arrays.stream(line.split(" -> "))
                       .map(path -> path.split(","))
                       .map(coordinates -> RockPath.createRockPath(parseInt(coordinates[0]), parseInt(coordinates[1])))
                       .toList());
        }
    }

    private record RockPath(int x, int y) {
        private static RockPath createRockPath(int distanceRight, int distanceDown) {
            return new RockPath(distanceDown, distanceRight);
        }

        @Override
        public String toString() {
            return "(%d,%d)".formatted(x, y);
        }
    }

    @Override
    protected Object part2() {
        return null;
    }
}
