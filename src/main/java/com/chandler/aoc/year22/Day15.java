package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

public class Day15 extends Day {

    public static void main(String[] args) {
        new Day15().printParts();
    }

    private boolean isPartOne;

    private record Sensor(int x, int y, int distance) {}

    @Override
    protected Object part1() {
        this.isPartOne = true;
        Set<Sensor> noBeaconsPresent = new HashSet<>();
        List<Sensor> sensors = new ArrayList<>();

        String[] lines = dayString().split("\r\n");

        parseInput(lines, sensors, noBeaconsPresent);

        int yRow = 2_000_000;
        setHashes(noBeaconsPresent, sensors, yRow);

        return noBeaconsPresent.stream().filter(point -> point.y == yRow).count();
    }

    private static void setHashes(Set<Sensor> noBeaconsPresent, List<Sensor> sensors, int yRow) {
        for (Sensor sensor : sensors) {
            int yDiff = abs(sensor.y() - yRow);
            int numHashesInYRow = (2 * sensor.distance() + 1) - (2 * yDiff);
            if (numHashesInYRow > 0) {
                if (numHashesInYRow == 1) {
                    Sensor notBeacon = new Sensor(sensor.x(), yRow, -1);
                    noBeaconsPresent.add(notBeacon);
                }
                int offSetSize = (numHashesInYRow - 1) / 2;
                for (int i = sensor.x() - offSetSize; i < sensor.x() + offSetSize; i++) {
                    Sensor notBeacon = new Sensor(i, yRow, -1);
                    noBeaconsPresent.add(notBeacon);
                }
            }
        }
    }

    private void parseInput(String[] lines, List<Sensor> sensors, Set<Sensor> noBeaconsPresent) {
        for (String line : lines) {
            Pattern.compile("x=(-?\\d+), y=(-?\\d+).*x=(-?\\d+), y=(-?\\d+)")
                   .matcher(line)
                   .results()
                   .forEach(mr -> {
                       int x1 = parseInt(mr.group(1));
                       int y1 = parseInt(mr.group(2));
                       int x2 = parseInt(mr.group(3));
                       int y2 = parseInt(mr.group(4));
                       int distance = abs(x1 - x2) + abs(y1 - y2);
                       Sensor sensor = new Sensor(x1, y1, distance);
                       Sensor beacon = new Sensor(x2, y2, distance);
                       sensors.add(sensor);
                       noBeaconsPresent.addAll(List.of(sensor, beacon));
                   });
        }
    }

    @Override
    protected Object part2() {
        return null;
    }

}
