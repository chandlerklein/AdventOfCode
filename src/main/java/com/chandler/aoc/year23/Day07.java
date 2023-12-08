package com.chandler.aoc.year23;

import com.chandler.aoc.common.Day;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day07 extends Day {

    public static void main(String[] args) {
        new Day07().run();
    }

    @Override
    protected Object part1() {
        var handAndBids = getHandAndBids(false);

        return IntStream.range(0, handAndBids.size())
                        .map(i -> handAndBids.get(i).bid() * (i + 1))
                        .sum();
    }

    @Override
    protected Object part2() {
        var handAndBids = getHandAndBids(true);

        return IntStream.range(0, handAndBids.size())
                        .map(i -> handAndBids.get(i).bid() * (i + 1))
                        .sum();
    }

    private List<HandAndBid> getHandAndBids(boolean isPartTwo) {
        return stream().map(line -> HandAndBid.from(line, isPartTwo))
                       .collect(groupingBy(HandAndBid::type))
                       .values().stream()
                       .flatMap(it -> it.stream().sorted(handAndBidComparator(isPartTwo)))
                       .toList();
    }

    private Comparator<HandAndBid> handAndBidComparator(boolean isPartTwo) {
        return (handAndBid1, handAndBid2) -> {
            for (int i = 0; i < handAndBid1.hand().length(); i++) {
                int relativeStrength1 = getRelativeStrength(handAndBid1.hand().charAt(i), isPartTwo);
                int relativeStrength2 = getRelativeStrength(handAndBid2.hand().charAt(i), isPartTwo);
                if (relativeStrength1 > relativeStrength2) return 1;
                if (relativeStrength1 < relativeStrength2) return -1;
            }
            return 0;
        };
    }

    private int getRelativeStrength(char card, boolean isPartTwo) {
        if (isPartTwo && card == 'J') card = '1';
        return switch (card) {
            case 'A' -> 62;
            case 'K' -> 61;
            case 'Q' -> 60;
            case 'J' -> 59;
            case 'T' -> 58;
            default -> card;
        };
    }

    private record HandAndBid(String hand, int bid, int type) {

        public static HandAndBid from(String line, boolean isPartTwo) {
            String[] split = line.split("\\s");
            return new HandAndBid(split[0], parseInt(split[1]), getType(split[0], isPartTwo));
        }

        private static int getType(String hand, boolean isPartTwo) {
            var cardFrequency = hand.chars()
                                    .mapToObj(i -> (char) i)
                                    .collect(groupingBy(identity(), counting()));

            var groupFrequency = cardFrequency.values()
                                              .stream()
                                              .collect(groupingBy(identity(), counting()));

            var wildCards = cardFrequency.getOrDefault('J', 0L);

            var singles = groupFrequency.getOrDefault(1L, -1L);
            var pairs = groupFrequency.getOrDefault(2L, -1L);
            var trios = groupFrequency.getOrDefault(3L, -1L);
            var quartets = groupFrequency.getOrDefault(4L, -1L);

            // high card
            if (singles == hand.length()) {
                if (!isPartTwo) return 0;
                return wildCards > 0 ? 1 : 0;
            }
            // one pair
            if (pairs == 1 && singles == 3) {
                if (!isPartTwo) return 1;
                return wildCards > 0 ? 3 : 1;
            }
            // two pair
            if (pairs == 2 && singles == 1) {
                if (!isPartTwo) return 2;
                if (wildCards == 0) return 2;
                return wildCards == 1 ? 4 : 5;
            }
            // three of a kind
            if (trios == 1 && singles == 2) {
                if (!isPartTwo) return 3;
                return wildCards > 0 ? 5 : 3;
            }
            // full house
            if (trios == 1 && pairs == 1) {
                if (!isPartTwo) return 4;
                return wildCards > 0 ? 6 : 4;
            }
            // four of a kind
            if (quartets == 1 && singles == 1) {
                if (!isPartTwo) return 5;
                return wildCards > 0 ? 6 : 5;
            }
            // five of a kind
            return 6;
        }
    }

}
