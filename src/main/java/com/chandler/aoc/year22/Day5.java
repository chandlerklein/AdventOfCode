package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toCollection;

public class Day5 extends Day {
    public Day5() {
        super("2022", "5");
    }

    public static void main(String[] args) {
        new Day5().printParts();
    }

    @Override
    protected Object part1() {
        List<Deque<Character>> deques = new ArrayList<>();

        String[] lines = dayString().split("\r\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith(" 1")) {
                continue;
            }
            if (line.startsWith("move")) {
                return processInstructionsPart1(lines, i, deques);
            }
            int k = 0;
            for (int j = 1; j < line.length(); j += 4) {
                try {
                    int finalK = k;
                    addToDeque(() -> deques.get(finalK), line, j);
                    k++;
                } catch (IndexOutOfBoundsException e) {
                    Deque<Character> deque = addToDeque(ArrayDeque::new, line, j);
                    deques.add(deque);
                    k++;
                }
            }
        }
        return null;
    }

    private static Deque<Character> addToDeque(Supplier<Deque<Character>> dequeSupplier, String line, int j) {
        Deque<Character> deque = dequeSupplier.get();
        if (line.charAt(j) != ' ') {
            deque.add(line.charAt(j));
        }
        return deque;
    }

    private String processInstructionsPart1(String[] lines, int k, List<Deque<Character>> deques) {
        while (k < lines.length) {
            String[] instruction = lines[k++].replace("move ", "").replace(" from ", ",").replace(" to ", ",").split(",");
            int numElements = parseInt(instruction[0]);
            Deque<Character> fromStack = deques.get(parseInt(instruction[1]) - 1);
            Deque<Character> toStack = deques.get(parseInt(instruction[2]) - 1);

            IntStream.range(0, numElements)
                     .mapToObj(i -> fromStack.removeFirst())
                     .forEach(toStack::addFirst);
        }
        return deques.stream()
                     .filter(not(Collection::isEmpty))
                     .map(Deque::getFirst)
                     .map(String::valueOf)
                     .reduce("", (a, b) -> a + b);
    }

    private String processInstructionsPart2(String[] lines, int k, List<Deque<Character>> deques) {
        while (k < lines.length) {
            String[] instruction = lines[k++].replace("move ", "").replace(" from ", ",").replace(" to ", ",").split(",");
            int numElements = parseInt(instruction[0]);
            Deque<Character> fromStack = deques.get(parseInt(instruction[1]) - 1);
            Deque<Character> toStack = deques.get(parseInt(instruction[2]) - 1);

            Deque<Character> tempList = IntStream.range(0, numElements)
                                                 .mapToObj(i -> fromStack.removeFirst())
                                                 .collect(toCollection(ArrayDeque::new));
            while (!tempList.isEmpty()) {
                toStack.addFirst(tempList.removeLast());
            }
        }
        return deques.stream()
                     .filter(not(Collection::isEmpty))
                     .map(Deque::getFirst)
                     .map(String::valueOf)
                     .reduce("", (a, b) -> a + b);
    }

    @Override
    protected Object part2() {
        List<Deque<Character>> deques = new ArrayList<>();

        String[] lines = dayString().split("\r\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith(" 1")) {
                continue;
            }
            if (line.startsWith("move")) {
                return processInstructionsPart2(lines, i, deques);
            }
            int k = 0;
            for (int j = 1; j < line.length(); j += 4) {
                try {
                    int finalK = k;
                    addToDeque(() -> deques.get(finalK), line, j);
                    k++;
                } catch (IndexOutOfBoundsException e) {
                    Deque<Character> deque = addToDeque(ArrayDeque::new, line, j);
                    deques.add(deque);
                    k++;
                }
            }
        }
        return null;
    }
}
