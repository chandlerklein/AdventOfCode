package com.chandler.aoc.year2022;

import com.chandler.aoc.common.Day;

import java.util.Map;

public class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().run();
    }

    private static final Map<String, String> shapeMappings = Map.of(
        "A", "R",
        "B", "P",
        "C", "S",
        "X", "R",
        "Y", "P",
        "Z", "S"
    );

    private static final Map<String, Integer> pointMap = Map.of(
        "R", 1,
        "P", 2,
        "S", 3
    );

    private static final Map<String, String> strengthMap = Map.of(
        "R", "S",
        "P", "R",
        "S", "P"
    );

    @Override
    protected Object part1() {
        return stream().mapToInt(line -> new Round(line).calculateScorePart1()).sum();
    }

    private record Round(String shape, String opponentShape) {
        public Round(String line) {
            this(shapeMappings.get(line.split(" ")[1]), shapeMappings.get(line.split(" ")[0]));
        }

        private int calculateScorePart1() {
            if (opponentShape.equals(shape)) {
                return pointMap.get(shape) + 3;
            } else if (strengthMap.get(shape).equals(opponentShape)) {
                return pointMap.get(shape) + 6;
            } else {
                return pointMap.get(shape);
            }
        }

        private int calculateScorePart2() {
            return switch (shape) {
                case "P" -> 3 + pointMap.get(opponentShape);
                case "R" -> pointMap.get(strengthMap.get(opponentShape));
                default -> 6 + pointMap.get(strengthMap.get(strengthMap.get(opponentShape)));
            };
        }
    }

    @Override
    protected Object part2() {
        return stream().map(Round::new)
                          .map(Round::calculateScorePart2)
                          .mapToInt(Integer::intValue)
                          .sum();
    }
}
