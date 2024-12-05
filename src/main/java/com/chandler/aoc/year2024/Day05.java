package com.chandler.aoc.year2024;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Collections.emptySet;
import static java.util.Map.entry;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Gatherers.windowSliding;

public class Day05 extends Day {

    void main() {
        new Day05().run();
    }

    private final String[] all = string().split("\n\n");

    private final List<List<Integer>> updates =
        Arrays.stream(all[1].split("\n"))
              .map(update -> Arrays.stream(update.split(",")).map(Integer::parseInt).toList())
              .toList();

    private final Map<Integer, Set<Integer>> pages =
        Pattern.compile("(\\d+)\\|(\\d+)")
               .matcher(String.join("", all[0].split("\r\n"))).results()
               .map(m -> entry(parseInt(m.group(1)), parseInt(m.group(2))))
               .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toSet())));

    Comparator<Integer> comparator = (page1, page2) -> pages.getOrDefault(page1, emptySet()).contains(page2) ? 1 : -1;

    @Override public Object part1() {
        return updates.stream().filter(update -> update.stream().gather(windowSliding(2)).allMatch(
                          window -> pages.get(window.getFirst()).contains(window.getLast())))
                      .mapToInt(line -> line.get((line.size() - 1) / 2))
                      .sum();
    }

    @Override public Object part2() {
        return updates.stream().filter(pages -> !pages.stream().gather(windowSliding(2)).allMatch(
                          window -> this.pages.get(window.getFirst()).contains(window.getLast())))
                      .map(list -> list.stream().sorted(comparator).toList())
                      .mapToInt(line -> line.get((line.size() - 1) / 2))
                      .sum();
    }


}
