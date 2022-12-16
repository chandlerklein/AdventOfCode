package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

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

    private record Sensor(int x, int y, int distance) {}


    @Override
    protected Object part1() {
        Set<Sensor> noBeaconPresent = new HashSet<>();
        List<Sensor> sensors = new ArrayList<>();

        String[] lines = exampleDayString().split("\r\n");

        parseInput(lines, sensors, noBeaconPresent);

        for (Sensor sensor : sensors) {
            System.out.println(sensor);
        }

        return null;
    }

    private void parseInput(String[] lines, List<Sensor> sensors, Set<Sensor> noBeaconPresent) {
        for (String line : lines) {
            Pattern.compile("x=(-?\\d+), y=(-?\\d+).*x=(-?\\d+), y=(-?\\d+)")
                   .matcher(line)
                   .results()
                   .forEach(mr -> {
                       Sensor sensor = new Sensor(parseInt(mr.group(1)), parseInt(mr.group(2)), abs(parseInt(mr.group(1)) - parseInt(mr.group(3))) + abs(parseInt(mr.group(2)) - parseInt(mr.group(4))));
                       Sensor beacon = new Sensor(parseInt(mr.group(3)), parseInt(mr.group(4)), -1);
                       sensors.add(sensor);
                       noBeaconPresent.addAll(List.of(sensor, beacon));
                   });
        }
    }

    @Override
    protected Object part2() {
        return null;
    }
}
