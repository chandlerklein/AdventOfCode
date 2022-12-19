package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;

// WIP
public class Day18 extends Day {

    public static void main(String[] args) {
        new Day18().printParts();
    }

    private record Square(int x, int y, int z, Set<String> points) {
        public static Square of(String line) {
            String[] values = line.split(",");
            int x = parseInt(values[0]);
            int y = parseInt(values[1]);
            int z = parseInt(values[2]);
            return new Square(x, y, z, getPoints(x, y, z));
        }

        private static Set<String> getPoints(int x, int y, int z) {
            String str = "%d,%d,%d";
            return Set.of(str.formatted(x - 1, y, z), str.formatted(x, y, z), str.formatted(x - 1, y, z - 1), str.formatted(x, y, z - 1), str.formatted(x - 1, y - 1, z), str.formatted(x, y - 1, z), str.formatted(x - 1, y - 1, z - 1), str.formatted(x, y - 1, z - 1));
        }
    }

    @Override
    protected Object part1() {
        List<Square> squares = dayStream().map(Square::of).toList();

        Set<Sets.SetView<String>> intersections = new HashSet<>();
        int numIntersections = 0;
        int numCubes = squares.size();

        for (int i = 0; i < squares.size(); i++) {
            Square square1 = squares.get(i);
            for (int j = 1; j < squares.size(); j++) {
                Square square2 = squares.get(j);
                Sets.SetView<String> intersection = Sets.intersection(square1.points(), square2.points());
                int intersectionSize = intersection.size();
                if (!intersections.contains(intersection) && intersectionSize % 4 == 0) {
                    intersections.add(intersection);
                    numIntersections = numIntersections + (intersectionSize / 4);
                }
            }
        }
        return null;
    }

    @Override
    protected Object part2() {
        return null;
    }
}
