package com.chandler.aoc.year2023;

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

    private final char[][] arr = stream().map(String::toCharArray).toArray(char[][]::new);

    private final int[][] directions = {
        { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

    @Override
    protected Object part1() {
        Map<Map.Entry<Integer, Integer>, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (!isLetterOrDigit(arr[i][j]) && arr[i][j] != '.') getParts(arr, i, j, map);
            }
        }
        return map.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    protected Object part2() {
        int total = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == '*') {
                    Map<Map.Entry<Integer, Integer>, Integer> map = new HashMap<>();
                    total += getParts(arr, i, j, map);
                }
            }
        }
        return total;
    }

    private int getParts(char[][] arr, int i, int j, Map<Map.Entry<Integer, Integer>, Integer> map) {
        for (int[] direction : directions) {
            Endpoints endpoints = getEndpoints(arr, i, j, direction);
            if (endpoints == null) continue;

            String strNum = copyValueOf(arr[endpoints.row()], endpoints.start(), endpoints.end() - endpoints.start() + 1);
            map.merge(entry(endpoints.row(), endpoints.start()), parseInt(strNum), Math::max);
        }
        return map.size() == 2 ? map.values().stream().reduce(1, Math::multiplyExact) : 0;
    }

    private static Endpoints getEndpoints(char[][] arr, int i, int j, int[] direction) {
        int row = i + direction[0];
        int col = j + direction[1];

        if (row < 0 || col < 0 || row >= arr.length || col >= arr[0].length || !isDigit(arr[row][col])) return null;

        try { while (isDigit(arr[row][col - 1])) col--; } catch (Exception e) { /* do nothing */ }
        int start = col;

        try { while (isDigit(arr[row][col + 1])) col++; } catch (Exception e) { /* do nothing */ }
        int end = col;

        return new Endpoints(row, start, end);
    }

    private record Endpoints(int row, int start, int end) { }

}
