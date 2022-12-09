package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class Day8 extends Day {

    public static void main(String[] args) {
        new Day8().printParts();
    }

    private static final int[][] DIRECTIONS = new int[][]{ { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

    @Override
    protected Object part1() {
        String[] lines = dayString().split("\r\n");
        int numberVisible = 0;

        int rows = lines.length;
        int columns = lines[0].length();
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++) {
                int treeSize = parseInt(valueOf(lines[i].charAt(j)));
                if (isTreeVisibleFromEdge(lines, i, j, treeSize)) {
                    numberVisible++;
                }
            }
        }
        int numEdgeTrees = (columns * 2) + ((rows - 2) * 2);
        return numberVisible + numEdgeTrees;
    }

    private boolean isTreeVisibleFromEdge(String[] lines, int i, int j, int treeSize) {
        for (int[] direction : DIRECTIONS) {
            int newRow = i + direction[0];
            int newColumn = j + direction[1];
            int adjacentValue = parseInt(valueOf(lines[newRow].charAt(newColumn)));
            while (adjacentValue < treeSize) {
                if (isTreeOnEdge(lines, newRow, newColumn)) {
                    return true;
                }
                newRow += direction[0];
                newColumn += direction[1];
                adjacentValue = parseInt(valueOf(lines[newRow].charAt(newColumn)));
            }
        }
        return false;
    }

    private boolean isTreeOnEdge(String[] lines, int i, int j) {
        return i == 0 || j == 0 || i == lines.length - 1 || j == lines[0].length() - 1;
    }

    @Override
    protected Object part2() {
        String[] lines = dayString().split("\r\n");
        int maxScore = -1;

        int rows = lines.length;
        int columns = lines[0].length();
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++) {
                int treeSize = parseInt(valueOf(lines[i].charAt(j)));
                maxScore = Math.max(maxScore, getScenicScore(lines, i, j, treeSize));
            }
        }
        return maxScore;
    }

    private int getScenicScore(String[] lines, int i, int j, int treeSize) {
        List<Integer> directionScores = new ArrayList<>();
        int currentCount = 0;

        for (int[] direction : DIRECTIONS) {
            int newRow = i + direction[0];
            int newColumn = j + direction[1];
            int adjacentValue = parseInt(valueOf(lines[newRow].charAt(newColumn)));
            while (adjacentValue < treeSize) {
                if (isTreeOnEdge(lines, newRow, newColumn)) {
                    currentCount++;
                    break;
                }
                newRow += direction[0];
                newColumn += direction[1];
                adjacentValue = parseInt(valueOf(lines[newRow].charAt(newColumn)));
                currentCount++;
            }
            if (adjacentValue >= treeSize) {
                currentCount++;
            }
            directionScores.add(currentCount);
            currentCount = 0;
        }
        return directionScores.stream().reduce(1, (a, b) -> a * b);
    }
}
