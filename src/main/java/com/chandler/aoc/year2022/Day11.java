package com.chandler.aoc.year2022;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.lang.Long.valueOf;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

public class Day11 extends Day {

    public static void main(String[] args) {
        new Day11().run();
    }

    private final String[] groups = string().split("\\r\\n\\r\\n");

    private boolean isPartOne;
    private List<Monkey> monkeys;

    private static final int NUM_ROUNDS_PART_1 = 20;
    private static final int NUM_ROUNDS_PART_2 = 10_000;
    private int leastCommonMultiple = 1;

    private long getResult(int numberOfRounds) {
        monkeys = Arrays.stream(groups).map(Monkey::new).toList();

        IntStream.range(0, numberOfRounds).forEach(i -> monkeys.forEach(Monkey::inspectAndThrow));

        return monkeys.stream()
                      .map(Monkey::getInspectCount)
                      .sorted(reverseOrder())
                      .limit(2)
                      .reduce(1L, Math::multiplyExact);
    }

    @Override
    public Object part1() {
        isPartOne = true;
        return getResult(NUM_ROUNDS_PART_1);
    }

    @Override
    public Object part2() {
        isPartOne = false;
        return getResult(NUM_ROUNDS_PART_2);
    }

    public class Monkey {
        private final List<Long> items;
        private final UnaryOperator<Long> operation;
        private final Predicate<Long> isDivisible;
        private final int trueMonkey;
        private final int falseMonkey;
        private long inspectCount = 0;

        public Monkey(String group) {
            String[] rows = group.split("\r\n");
            this.items = Arrays.stream(rows[1].substring(18).split(", ")).map(Long::valueOf).collect(toList());
            this.operation = setOperation(rows[2]);

            int divisor = parseInt(rows[3].substring(21));
            if (!isPartOne) {
                leastCommonMultiple *= divisor;
            }
            this.isDivisible = item -> item % divisor == 0;

            this.trueMonkey = parseInt(rows[4].substring(29));
            this.falseMonkey = parseInt(rows[5].substring(30));
        }

        private UnaryOperator<Long> setOperation(String row) {
            String multiplicand = row.substring(25);
            boolean isIdentity = multiplicand.equals("old");
            LongBinaryOperator operator = (x, y) -> row.charAt(23) == '*' ? x * y : x + y;
            return item -> operator.applyAsLong(item, isIdentity ? item : valueOf(multiplicand)) / (isPartOne ? 3 : 1);
        }

        private void inspectAndThrow() {
            items.stream()
                 .map(operation).map(this::part2Mod)
                 .forEach(this::throwItem);
            inspectCount += items.size();
            items.clear();
        }

        private Long part2Mod(Long item) {
            return !isPartOne ? item % leastCommonMultiple : item;
        }

        private void throwItem(Long item) {
            monkeys.get(isDivisible.test(item) ? trueMonkey : falseMonkey).items.add(item);
        }

        private long getInspectCount() {
            return inspectCount;
        }
    }

}
