package com.chandler.aoc.year23;

import com.chandler.aoc.common.Day;

import java.util.Comparator;
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
        var handAndBids = stream().map(HandAndBid::from)
                                  .collect(groupingBy(HandAndBid::type))
                                  .values().stream()
                                  .flatMap(it -> it.stream().sorted(handAndBidComparator))
                                  .toList();

        return IntStream.range(0, handAndBids.size())
                        .map(i -> handAndBids.get(i).bid() * (i + 1))
                        .sum();
    }

    private final Comparator<HandAndBid> handAndBidComparator = (handAndBid1, handAndBid2) -> {
        for (int i = 0; i < handAndBid1.hand().length(); i++) {
            int relativeStrength1 = getRelativeStrength(handAndBid1.hand().charAt(i));
            int relativeStrength2 = getRelativeStrength(handAndBid2.hand().charAt(i));
            if (relativeStrength1 > relativeStrength2) return 1;
            if (relativeStrength1 < relativeStrength2) return -1;
        }
        return 0;
    };

    private int getRelativeStrength(char card) {
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

        public static HandAndBid from(String line) {
            String[] split = line.split("\\s");
            return new HandAndBid(split[0], parseInt(split[1]), getType(split[0]));
        }

        private static int getType(String s) {
            var frequency = s.chars().mapToObj(i -> (char) i)
                             .collect(groupingBy(identity(), counting()))
                             .values()
                             .stream()
                             .collect(groupingBy(identity(), counting()));

            var singles = frequency.getOrDefault(1L, -1L);
            var pairs = frequency.getOrDefault(2L, -1L);
            var trios = frequency.getOrDefault(3L, -1L);
            var quartets = frequency.getOrDefault(4L, -1L);

            if (singles == s.length()) return 0;            // high card
            if (pairs == 1 && singles == 3) return 1;       // one pair
            if (pairs == 2 && singles == 1) return 2;       // two pair
            if (trios == 1 && singles == 2) return 3;       // three of a kind
            if (trios == 1 && pairs == 1) return 4;         // full house
            if (quartets == 1 && singles == 1) return 5;    // four of a kind
            return 6;                                       // five of a kind
        }
    }

}
