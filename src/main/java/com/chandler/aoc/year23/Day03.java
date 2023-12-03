package com.chandler.aoc.year23;

import com.chandler.aoc.common.Day;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetterOrDigit;
import static java.lang.Integer.parseInt;
import static java.lang.String.copyValueOf;
import static java.util.Map.entry;

public class Day03 extends Day {

    public static void main(String[] args) {
        new Day03().run();
    }

    @Override
    protected Object part1() {
        char[][] arr = stream().map(String::toCharArray).toArray(char[][]::new);

        Map<Map.Entry<Integer, Integer>, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (!isLetterOrDigit(arr[i][j]) && arr[i][j] != '.') {
                    addPartNumbers(arr, i, j, map);
                }
            }
        }
        return map.values().stream().mapToInt(Integer::intValue).sum();
    }

    private final int[][] directions = {
        { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

    private void addPartNumbers(char[][] arr, int i, int j, Map<Map.Entry<Integer, Integer>, Integer> map) {
        for (int[] direction : directions) {
            int row = i + direction[0];
            int col = j + direction[1];

            try {
                if (!isDigit(arr[row][col])) continue;
                while (isDigit(arr[row][col - 1])) col--;
            } catch (Exception e) { }
            int start = col;

            try {
                while (isDigit(arr[row][col + 1])) col++;
            } catch (Exception e) { }
            int end = col;

            String strNum = copyValueOf(arr[row], start, end - start + 1);
            map.merge(entry(row, start), parseInt(strNum), Math::max);
        }
    }

    @Override
    protected Object part2() {
        return super.part2();
    }

}
