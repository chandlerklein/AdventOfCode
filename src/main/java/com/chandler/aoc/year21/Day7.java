package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;
import com.google.common.math.Quantiles;
import com.google.common.math.Stats;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class Day7 extends Day {

    protected Day7() {
        super("2021", "7");
    }

    public static void main(String[] args) {
        new Day7().printParts();
    }

    @Override
    protected Object part1() {
        return getFuelCost(getCrabPositions());
    }

    @Override
    protected Object part2() {
        return getFuelCostPartTwo(getCrabPositions());
    }

    private List<Integer> getCrabPositions() {
        return dayStream(",")
                .map(Integer::valueOf)
                .toList();
    }

    private Long getFuelCost(List<Integer> crabPositions) {
        var median = (long) Quantiles.median().compute(crabPositions);
        return crabPositions.stream()
                            .map(it -> (it > median) ? it - median : median - it)
                            .reduce(Long::sum)
                            .orElseThrow();
    }

    private Long getFuelCostPartTwo(List<Integer> crabPositions) {
        var avg = (long) Math.floor(Stats.meanOf(crabPositions));
        return crabPositions.stream()
                            .map(it -> (it > avg) ? it - avg : avg - it)
                            .map(it -> (it * (it + 1)) / 2)
                            .reduce(Long::sum)
                            .orElseThrow();
    }
}
