package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;

import java.util.stream.Stream;

public class Day02 extends Day {

    public static void main(String[] args) {
        new Day02().run();
    }

    @Override
    protected Object part1() {
        var submarine = new Submarine();
        getCommands().forEach(submarine::move);
        return calculateResult(submarine);
    }

    @Override
    protected Object part2() {
        var submarine = new Submarine();
        getCommands().forEach(submarine::movePartTwo);
        return calculateResult(submarine);
    }

    public Stream<Command> getCommands() {
        return stream().map(line -> line.split(" "))
                          .map(Command::new);
    }

    public long calculateResult(Submarine submarine) {
        return submarine.getHorizontalPosition() * submarine.getDepth();
    }

    static class Command {
        private final String direction;
        private final int distance;

        public Command(String[] input) {
            direction = input[0];
            distance = Integer.parseInt(input[1]);
        }

        public String getDirection() {
            return direction;
        }

        public int getDistance() {
            return distance;
        }
    }

    static class Submarine {
        private long horizontalPosition = 0;
        private long depth = 0;
        private long aim = 0;

        public long getHorizontalPosition() {
            return horizontalPosition;
        }

        public long getDepth() {
            return depth;
        }

        void move(Command command) {
            String direction = command.getDirection();
            int distance = command.getDistance();

            switch (direction) {
                case "forward" -> increaseHorizontalPosition(distance);
                case "down" -> increaseDepth(distance);
                case "up" -> decreaseDepth(distance);
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }
        }

        void movePartTwo(Command command) {
            String direction = command.getDirection();
            int distance = command.getDistance();

            switch (direction) {
                case "forward" -> increaseHorizontalPositionAndDepth(distance);
                case "down" -> increaseAim(distance);
                case "up" -> decreaseAim(distance);
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }
        }

        private void increaseHorizontalPositionAndDepth(int distance) {
            horizontalPosition += distance;
            depth = depth + (aim * distance);
        }

        private void increaseAim(int distance) {
            aim += distance;
        }

        private void decreaseAim(int distance) {
            aim -= distance;
        }

        private void increaseHorizontalPosition(int distance) {
            horizontalPosition += distance;
        }

        private void increaseDepth(int distance) {
            depth += distance;
        }

        private void decreaseDepth(int distance) {
            depth -= distance;
        }
    }
}
