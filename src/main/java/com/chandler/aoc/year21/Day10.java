package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day10 extends Day {

    private final Map<Character, Character> closingsMappings =
            Map.of(')', '(', ']', '[', '}', '{', '>', '<');

    private final Map<Character, Long> syntaxScores =
            Map.of(')', 3L, ']', 57L, '}', 1197L, '>', 25137L);

    private final Map<Character, Integer> completionScores =
            Map.of('(', 1, '[', 2, '{', 3, '<', 4);

    protected Day10() {
        super("2021", "10");
    }

    public static void main(String[] args) {
        new Day10().printParts();
    }

    @Override
    protected Object part1() {
        return dayStream().map(line -> getScore(line, true)).mapToLong(l -> l)
                          .sum();
    }

    @Override
    protected Object part2() {
        List<Long> scores = dayStream().map(line -> getScore(line, false))
                                       .filter(Objects::nonNull)
                                       .sorted()
                                       .toList();
        return scores.get(scores.size() / 2);
    }

    private Long getScore(String line, boolean isPartOne) {
        ArrayDeque<Character> deque = new ArrayDeque<>();

        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (closingsMappings.containsKey(currentChar)) {
                if (!closingsMappings.get(currentChar).equals(deque.pop())) {
                    return isPartOne ? syntaxScores.get(currentChar) : null;
                }
            } else {
                deque.push(currentChar);
            }
        }
        return isPartOne ? 0 : getCompletionScore(deque);
    }

    private Long getCompletionScore(ArrayDeque<Character> deque) {
        long totalScore = 0;
        while (!deque.isEmpty()) {
            totalScore = (5 * totalScore) + completionScores.get(deque.poll());
        }
        return totalScore;
    }
}
