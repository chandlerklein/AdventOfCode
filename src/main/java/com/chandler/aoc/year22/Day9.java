package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Character.getNumericValue;
import static java.lang.Math.abs;

public class Day9 extends Day {

    public static void main(String[] args) {
        new Day9().printParts();
    }

    private static final Map<Character, int[]> directions = Map.of(
            'U', new int[]{-1, 0},
            'R', new int[]{0, 1},
            'D', new int[]{1, 0},
            'L', new int[]{0, -1}
    );

    @Override
    protected Object part1() {
        String[] input = dayString().split("\r\n");

        int[] head = new int[]{0, 0};
        int[] tail = new int[]{0, 0};
        Set<Point> points = new HashSet<>(Set.of(new Point(0, 0)));

        for (String line : input) {
            int[] direction = directions.get(line.charAt(0));
            int numMoves = getNumericValue(line.charAt(2));

            for (int i = 0; i < numMoves; i++) {
                int tempHeadRow = head[0];
                int tempHeadCol = head[1];

                head[0] += direction[0];
                head[1] += direction[1];

                if (!isHeadAdjacent(head, tail)) {
                    if (isHeadInSameRowOrColumn(head, tail)) {
                        tail[0] += direction[0];
                        tail[1] += direction[1];
                    } else {
                        tail[0] = tempHeadRow;
                        tail[1] = tempHeadCol;
                    }
                    points.add(new Point(tail[0], tail[1]));
                }
            }
        }
        return points.size();
    }

    private record Point(int row, int col) {}

    private boolean isHeadAdjacent(int[] head, int[] tail) {
        return abs(tail[0] - head[0]) <= 1 && abs(tail[1] - head[1]) <= 1;
    }

    private boolean isHeadInSameRowOrColumn(int[] head, int[] tail) {
        return head[0] == tail[0] || head[1] == tail[1];
    }

    @Override
    protected Object part2() {
        return null;
    }
}
