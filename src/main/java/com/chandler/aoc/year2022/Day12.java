package com.chandler.aoc.year2022;

import com.chandler.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.String.format;

public class Day12 extends Day {

    public static void main(String[] args) {
        new Day12().run();
    }

    private final List<char[]> map = Arrays.stream(string().split("\r\n")).map(String::toCharArray).toList();
    private final Set<String> visited = new HashSet<>();

    @Override
    public Object part1() {
        return getResult(new int[]{20, 0});
    }

    @Override
    public Object part2() {
        return getEntrances(map).stream()
                                .map(this::getResult)
                                .min(Integer::compareTo)
                                .orElseThrow();
    }

    private int getResult(int[] entrance) {
        int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int rows = map.size();
        int cols = map.get(0).length;

        Position startingPosition = new Position(entrance[0], entrance[1], 0);

        Queue<Position> queue = new LinkedList<>();
        queue.add(startingPosition);
        visited.add(format("%d,%d", entrance[0], entrance[1]));

        while (!queue.isEmpty()) {
            Position position = queue.remove();
            int currentDistance = position.distance();

            for (int[] direction : directions) {
                int newX = position.x() + direction[0];
                int newY = position.y() + direction[1];
                Position newPosition = new Position(newX, newY, currentDistance + 1);
                String newPositionStr = format("%d,%d", newX, newY);

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                    char newHeight = map.get(newX)[newY];
                    char oldHeight = map.get(position.x())[position.y()];
                    if (!isVisited(newPositionStr) && isTraversable(oldHeight, newHeight)) {
                        if (newHeight == 'E') {
                            map.get(newX)[newY] = '{';
                            continue;
                        }
                        if (newHeight == '{') {
                            map.get(newX)[newY] = 'E';
                            visited.clear();
                            return currentDistance + 1;
                        }
                        queue.add(newPosition);
                        visited.add(newPositionStr);
                    }
                }
            }
        }
        visited.clear();
        return MAX_VALUE;
    }

    private boolean isVisited(String newPositionStr) {
        return visited.contains(newPositionStr);
    }

    private boolean isTraversable(char oldHeight, char newHeight) {
        return newHeight - oldHeight <= 1 || oldHeight == 'S';
    }

    private List<int[]> getEntrances(List<char[]> map) {
        List<int[]> entrances = new ArrayList<>();
        Set<Character> validEntrances = Set.of('S', 'a');
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length; j++) {
                if (validEntrances.contains(map.get(i)[j])) {
                    entrances.add(new int[]{i, j});
                }
            }
        }
        return entrances;
    }

    private record Position(int x, int y, int distance) {}

}
