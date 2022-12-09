package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.Math.signum;

public class Day5 extends Day {

    public static void main(String[] args) {
        new Day5().printParts();
    }

    @Override
    protected Object part1() {
        Map<Point, Integer> frequency = new HashMap<>();
        getLines().filter(Line::isHorizontalOrVertical)
                  .forEach(line -> addPoints(frequency, line));
        return getCountGreaterThanTwo(frequency);
    }

    @Override
    protected Object part2() {
        Map<Point, Integer> frequency = new HashMap<>();
        getLines().filter(Line::isHorizontalOrVerticalOrDiagonal)
                  .forEach(line -> addPoints(frequency, line));
        return getCountGreaterThanTwo(frequency);
    }

    private Stream<Line> getLines() {
        return dayStream().map(str -> str.split(",| -> "))
                          .map(Line::parse);
    }

    private long getCountGreaterThanTwo(Map<Point, Integer> frequency) {
        return frequency.values()
                        .stream()
                        .filter(v -> v >= 2)
                        .count();
    }

    private void addPoints(Map<Point, Integer> frequency, Line line) {
        Point step = new Point(
            (int) signum(line.b.x - line.a.x),
            (int) signum(line.b.y - line.a.y)
        );
        Stream.iterate(
                  new Point(line.a.x, line.a.y),
                  point -> point.increment(step))
              .limit(line.length())
              .forEach(point -> putPoint(point, frequency));
    }

    private void putPoint(Point current, Map<Point, Integer> frequency) {
        frequency.compute(current, (k, v) -> (v == null) ? 1 : v + 1);
    }

    private record Line(Point a, Point b) {
        private static Line parse(String[] input) {
            Point a = new Point(parseInt(input[0]), parseInt(input[1]));
            Point b = new Point(parseInt(input[2]), parseInt(input[3]));
            return new Line(a, b);
        }

        private int length() {
            int length = abs(a.x - b.x) + abs(a.y - b.y);
            if (isDiagonal()) {
                return length / 2 + 1;
            }
            return length + 1;
        }

        private boolean isHorizontalOrVerticalOrDiagonal() {
            return isHorizontalOrVertical() || isDiagonal();
        }

        private boolean isHorizontalOrVertical() {
            return a.x == b.x || a.y == b.y;
        }

        private boolean isDiagonal() {
            return abs(a.x - b.x) == abs(a.y - b.y);
        }
    }

    private record Point(int x, int y) {
        Point increment(Point point) {
            return new Point(point.x + x, point.y + y);
        }
    }
}
