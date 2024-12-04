package com.chandler.aoc.year2022;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Day14 extends Day {

    public static void main(String[] args) {
        new Day14().run();
    }

    private static int maxRow = -1;

    @Override
    public Object part1() {
        String[] lines = string().split("\r\n");
        char[][] grid = getGrid();
        parseAndDraw(lines, grid);
        return dropSand(grid, 0);
    }

    private int dropSand(char[][] grid, int numSandPieces) {
        int[] sandPosition = new int[]{0, 500};
        int numMoves = 0;

        while (sandCanFall(sandPosition, grid)) {
            if (numMoves > 500) {
                return numSandPieces;
            }
            if (isSpaceOpen(grid[sandPosition[0] + 1][sandPosition[1]])) {
                sandPosition[0]++;
            } else if (isSpaceOpen(grid[sandPosition[0] + 1][sandPosition[1] - 1])) {
                sandPosition[0]++;
                sandPosition[1]--;
            } else {
                sandPosition[0]++;
                sandPosition[1]++;
            }
            numMoves++;
        }
        grid[sandPosition[0]][sandPosition[1]] = 'o';
        numSandPieces++;
        return dropSand(grid, numSandPieces);
    }

    private boolean sandCanFall(int[] sandPosition, char[][] grid) {
        int row = sandPosition[0];
        int col = sandPosition[1];
        return isSpaceOpen(grid[row + 1][col - 1]) || isSpaceOpen(grid[row + 1][col]) || isSpaceOpen(grid[row + 1][col + 1]);
    }

    private boolean isSpaceOpen(char space) {
        return space != '#' && space != 'o';
    }

    private void parseAndDraw(String[] lines, char[][] grid) {
        Arrays.stream(lines)
              .map(InputLine::new)
              .map(InputLine::rockPaths)
              .forEach(rockPaths -> drawRockPaths(rockPaths, grid));
    }

    private void drawRockPaths(List<RockPath> rockPaths, char[][] grid) {
        for (int i = 0; i < rockPaths.size() - 1; i++) {
            int fromRow = rockPaths.get(i).row();
            int fromCol = rockPaths.get(i).col();

            int toRow = rockPaths.get(i + 1).row();
            int toCol = rockPaths.get(i + 1).col();

            while (fromRow != toRow || fromCol != toCol) {
                grid[fromRow][fromCol] = '#';
                if (fromRow < toRow) {
                    fromRow++;
                } else if (fromCol < toCol) {
                    fromCol++;
                } else if (fromRow > toRow) {
                    fromRow--;
                } else {
                    fromCol--;
                }
            }
            grid[fromRow][fromCol] = '#';
        }
    }

    private char[][] getGrid() {
        char[][] grid = new char[1_000][1_100];
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

    private record RockPath(int row, int col) {
        private static RockPath createRockPath(int distanceRight, int distanceDown) {
            maxRow = Math.max(maxRow, distanceDown);
            return new RockPath(distanceDown, distanceRight);
        }

        @Override
        public String toString() {
            return "(%d,%d)".formatted(row, col);
        }
    }

    @Override
    public Object part2() {
        String[] lines = string().split("\r\n");
        char[][] grid = getGrid();
        parseAndDraw(lines, grid);
        drawFloor(grid, maxRow + 2);
        return dropSandPart2(grid);
    }

    private void drawFloor(char[][] grid, int rowNum) {
        for (int i = 0; i < grid[0].length; i++) {
            grid[rowNum][i] = '#';
        }
    }

    private int dropSandPart2(char[][] grid) {
        int numSandPieces = 0;
        int[] sandPosition = new int[]{0, 500};

        while (true) {
            while (sandCanFall(sandPosition, grid)) {
                if (isSpaceOpen(grid[sandPosition[0] + 1][sandPosition[1]])) {
                    sandPosition[0]++;
                } else if (isSpaceOpen(grid[sandPosition[0] + 1][sandPosition[1] - 1])) {
                    sandPosition[0]++;
                    sandPosition[1]--;
                } else {
                    sandPosition[0]++;
                    sandPosition[1]++;
                }
            }
            grid[sandPosition[0]][sandPosition[1]] = 'o';
            numSandPieces++;
            if (sandPosition[0] == 0 && sandPosition[1] == 500) {
                return numSandPieces;
            }
            sandPosition = new int[]{0, 500};
        }
    }
}
