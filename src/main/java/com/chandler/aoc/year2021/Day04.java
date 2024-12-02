package com.chandler.aoc.year2021;

import com.chandler.aoc.common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.chandler.aoc.year2021.Day04.BingoGame.BOARD_SIZE;

public class Day04 extends Day {

    public static void main(String[] args) {
        new Day04().run();
    }

    @Override
    protected Object part1() {
        return getBingoGame().getFirstPlaceScore();
    }

    @Override
    protected Object part2() {
        return getBingoGame().getLastPlaceScore();
    }

    public BingoGame getBingoGame() {
        var lines = stream().toList();

        var game = new BingoGame();
        game.setDrawNumbers(lines.get(0));
        game.setBoards(lines.subList(2, lines.size()));
        return game;
    }

    static class BingoGame {
        static final int BOARD_SIZE = 5;

        private int[] drawNumbers;
        private final List<Board> boards = new ArrayList<>();
        private final List<Board> winners = new ArrayList<>();

        public void setDrawNumbers(String line) {
            this.drawNumbers = Stream.of(line.split(","))
                                     .mapToInt(Integer::valueOf)
                                     .toArray();
        }

        public void setBoards(List<String> lines) {
            for (int i = 0; i < lines.size(); i++) {
                boards.add(new Board(lines.subList(i, i + BOARD_SIZE)));
                i += BOARD_SIZE;
            }
        }

        public int getFirstPlaceScore() {
            for (int number : drawNumbers) {
                for (Board board : boards) {
                    board.markBoard(number);
                    if (board.hasWon()) {
                        return board.computeResult();
                    }
                }
            }
            throw new RuntimeException("There is no winner");
        }

        public int getLastPlaceScore() {
            for (int number : drawNumbers) {
                for (Board board : boards) {
                    if (!board.hasAlreadyWon) {
                        board.markBoard(number);
                        if (board.hasWon()) {
                            winners.add(board);
                        }
                    }
                }
            }
            return winners.get(winners.size() - 1)
                          .computeResult();
        }
    }

    private static class Board {
        private final BingoNumber[][] rows = new BingoNumber[BOARD_SIZE][BOARD_SIZE];
        private final BingoNumber[][] columns = new BingoNumber[BOARD_SIZE][BOARD_SIZE];
        private final Map<Integer, Coordinate> position = new HashMap<>();
        private int lastDrawnNumber;
        private boolean hasAlreadyWon = false;

        public Board(List<String> input) {
            for (int i = 0; i < input.size(); i++) {
                int[] nums = getRow(input.get(i));
                for (int j = 0; j < nums.length; j++) {
                    addToDataSets(nums, i, j);
                }
            }
        }

        private void addToDataSets(int[] nums, int i, int j) {
            position.put(nums[j], new Coordinate(i, j));
            var bingoNumber = new BingoNumber(nums[j]);
            rows[i][j] = bingoNumber;
            columns[j][i] = bingoNumber;
        }

        private int[] getRow(String input) {
            return Arrays.stream(input.trim()
                                      .replace("  ", " ")
                                      .split(" "))
                         .mapToInt(Integer::valueOf)
                         .toArray();
        }

        public void markBoard(int number) {
            lastDrawnNumber = number;
            var coordinate = position.get(number);
            if (coordinate != null) {
                rows[coordinate.row()][coordinate.column()].setMarked(true);
            }
        }

        public boolean hasWon() {
            var coordinate = position.get(lastDrawnNumber);
            if (coordinate != null) {
                var rowNums = rows[coordinate.row()];
                var colNums = columns[coordinate.column()];
                if (isFullyMarked(rowNums) || isFullyMarked(colNums)) {
                    this.hasAlreadyWon = true;
                    return true;
                }
            }
            return false;
        }

        private boolean isFullyMarked(BingoNumber[] nums) {
            return Arrays.stream(nums)
                         .allMatch(BingoNumber::isMarked);
        }

        private int computeResult() {
            return Stream.of(rows)
                         .flatMap(Stream::of)
                         .filter(BingoNumber::isNotMarked)
                         .map(BingoNumber::getValue)
                         .reduce(Integer::sum)
                         .map(value -> value * lastDrawnNumber)
                         .orElseThrow();
        }
    }

    private static class BingoNumber {
        private final int value;
        private boolean isMarked;

        private BingoNumber(int value) {
            this.value = value;
            this.isMarked = false;
        }

        public int getValue() {
            return value;
        }

        public boolean isMarked() {
            return isMarked;
        }

        public boolean isNotMarked() {
            return !isMarked;
        }

        public void setMarked(boolean marked) {
            isMarked = marked;
        }
    }

    private record Coordinate(int row, int column) {
    }
}
