package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Character.isUpperCase;

public class Day3 extends Day {

    public static void main(String[] args) {
        new Day3().printParts();
    }

    private record Rucksack(Set<String> compartment1, Set<String> compartment2) {
        public Rucksack(String line) {
            this(new HashSet<>(List.of(line.substring(0, line.length() / 2).split(""))),
                 new HashSet<>(List.of(line.substring(line.length() / 2).split(""))));
        }

        private int getItemPriority() {
            compartment1.retainAll(compartment2);
            char item = compartment1.stream().findFirst().orElseThrow().charAt(0);
            return isUpperCase(item) ? item - 38 : item - 96;
        }
    }

    @Override
    protected Object part1() {
        return dayStream().map(Rucksack::new)
                          .map(Rucksack::getItemPriority)
                          .mapToInt(Integer::valueOf)
                          .sum();
    }

    @Override
    protected Object part2() {
        String[] lines = dayString().split("\r\n");
        List<Integer> priorities = new ArrayList<>();
        for (int i = 0; i < lines.length - 2; i += 3) {
            Set<String> rucksack1 = new HashSet<>(List.of(lines[i].split("")));
            Set<String> rucksack2 = new HashSet<>(List.of(lines[i + 1].split("")));
            Set<String> rucksack3 = new HashSet<>(List.of(lines[i + 2].split("")));
            rucksack1.retainAll(rucksack2);
            rucksack1.retainAll(rucksack3);
            char item = rucksack1.stream().findFirst().orElseThrow().charAt(0);
            priorities.add(isUpperCase(item) ? item - 38 : item - 96);
        }
        return priorities.stream()
                         .mapToInt(Integer::valueOf)
                         .sum();
    }
}
