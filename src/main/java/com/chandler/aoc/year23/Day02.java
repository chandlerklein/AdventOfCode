package com.chandler.aoc.year23;

import com.chandler.aoc.common.Day;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;

public class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().run();
    }

    @Override
    protected Object part1() {
        return stream().map(Game::from)
                       .filter(Game::isPossible)
                       .mapToInt(Game::id)
                       .sum();
    }

    @Override
    protected Object part2() {
        return stream().map(Game::from)
                       .mapToInt(Game::getPower)
                       .sum();
    }

    private record Game(int id, int maxRed, int maxGreen, int maxBlue) {
        public static Game from(String line) {
            return new Game(
                compile("Game (\\d+):")
                    .matcher(line).results().map(mr -> parseInt(mr.group(1))).findFirst().orElseThrow(),
                getMax(line, "red"),
                getMax(line, "green"),
                getMax(line, "blue")
            );
        }

        private static int getMax(String line, String color) {
            return compile("(\\d+) %s".formatted(color))
                .matcher(line).results().mapToInt(mr -> parseInt(mr.group(1))).max().orElseThrow();
        }

        public boolean isPossible() { return maxRed <= 12 && maxGreen <= 13 && maxBlue <= 14; }

        public int getPower() { return maxRed * maxGreen * maxBlue; }
    }

}
