package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;
import com.chandler.aoc.util.Point;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Day08 extends Day {

    void main() {
        new Day08().run();
    }

    char[][] grid = stream().map(String::toCharArray).toArray(char[][]::new);

    @Override public Object part1() {
        return getAntennae()
            .values().stream()
            .map(points -> Sets.combinations(points, 2))
            .flatMap(Collection::stream)
            .flatMap(pair -> {
                Point[] p = pair.toArray(new Point[0]);
                int rowDiff = p[0].x() - p[1].x();
                int colDiff = p[0].y() - p[1].y();
                return Stream.of(
                    new Point(p[0].x() + rowDiff, p[0].y() + colDiff),
                    new Point(p[1].x() - rowDiff, p[1].y() - colDiff));
            })
            .distinct()
            .filter(this::isInGrid)
            .count();
    }

    @Override public Object part2() {
        return getAntennae()
            .values().stream()
            .map(points -> Sets.combinations(points, 2))
            .flatMap(Collection::stream)
            .map(pair -> getAntinodes(pair.toArray(Point[]::new)))
            .flatMap(Collection::stream)
            .distinct()
            .count();
    }

    private Set<Point> getAntinodes(Point[] p) {
        var point1 = p[0];
        var point2 = p[1];
        Set<Point> points = new HashSet<>();
        int rowDiff = point1.x() - point2.x();
        int colDiff = point1.y() - point2.y();
        while (isInGrid(point1)) {
            points.add(point1);
            point1 = new Point(point1.x() + rowDiff, point1.y() + colDiff);
        }
        while (isInGrid(point2)) {
            points.add(point2);
            point2 = new Point(point2.x() - rowDiff, point2.y() - colDiff);
        }
        return points;
    }

    private boolean isInGrid(Point point) {
        return point.x() >= 0 && point.x() < grid.length && point.y() >= 0 && point.y() < grid[0].length;
    }

    private Map<Character, Set<Point>> getAntennae() {
        Map<Character, Set<Point>> antennae = new HashMap<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                char item = grid[row][col];
                if (item == '.') continue;
                var point = new Point(row, col);
                antennae.compute(item, (_, points) -> {
                    if (points == null) {
                        points = new HashSet<>();
                    }
                    points.add(point);
                    return points;
                });
            }
        }
        return antennae;
    }

}
