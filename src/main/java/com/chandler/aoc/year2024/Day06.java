package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;
import com.chandler.aoc.util.Guard;
import com.chandler.aoc.util.Point;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Map.entry;

public class Day06 extends Day {

    void main() {
        new Day06().run();
    }

    char[][] grid = stream().map(String::toCharArray).toArray(char[][]::new);
    Set<Character> arrows = Set.of('^', '>', 'v', '<');
    int[][] directions = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    Set<Point> positions = new HashSet<>();

    @Override public Object part1() {
        var start = findStart().orElseThrow();
        Guard guard = Guard.at(start);
        while (guard.isInGrid(grid)) {
            positions.add(new Point(guard.getRow(), guard.getCol()));
            guard.move(grid, directions);
        }
        return positions.size();
    }

    @Override public Object part2() {
        long startTime = System.currentTimeMillis();

        part1(); // load guard path in positions map
        var start = findStart().orElseThrow();

        var obstaclePositions = new HashSet<>(this.positions);
        obstaclePositions.remove(new Point(start.getKey().x(), start.getKey().y())); // don't put obstacle at start

        int count = 0;
        for (Point obstacle : obstaclePositions) {
            var slowGuard = Guard.at(start);
            var fastGuard = Guard.from(slowGuard).withTurns(2);

            grid[obstacle.x()][obstacle.y()] = '#'; // place temporary obstacle
            while (slowGuard.isInGrid(grid) && fastGuard.isInGrid(grid)) {
                slowGuard.move(grid, directions);
                fastGuard.move(grid, directions);
                if (slowGuard.equals(fastGuard)) {
                    count += 1;
                    break;
                }
            }
            grid[obstacle.x()][obstacle.y()] = '.'; // reset obstacle
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + "ms");

        return count;
    }

    private Optional<Map.Entry<Point, Character>> findStart() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                char item = grid[row][col];
                if (arrows.contains(item)) {
                    return Optional.of(entry(new Point(row, col), item));
                }
            }
        }
        return Optional.empty();
    }

}
