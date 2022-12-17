package com.chandler.aoc.year22;

import com.chandler.aoc.common.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;

public class Day16 extends Day {

    public static void main(String[] args) {
        new Day16().printParts();
    }

    private final Map<String, Valve> valves = new HashMap<>();
    private final Map<Valve, List<Valve>> connectedValves = new HashMap<>();

    private record Valve(String name, int flowRate) {
        @Override
        public String toString() {
            return "%s,%d".formatted(name, flowRate);
        }
    }

    @Override
    protected Object part1() {
        processInput(exampleDayString());

        valves.forEach((k, v) -> System.out.printf("%s -> %s%n", k, v));
        System.out.println();
        connectedValves.forEach((k, v) -> System.out.printf("%s -> %s%n", k, v));

        return null;
    }

    private void processInput(String dayString) {
        compile(" (\\w{2}).*=(\\d+)").matcher(dayString).results()
                                     .forEach(mr -> {
                                         Valve valve = new Valve(mr.group(1), parseInt(mr.group(2)));
                                         valves.put(mr.group(1), valve);
                                     });
        compile(" (\\w{2}).*valve.? (.+)").matcher(dayString).results()
                                          .forEach(mr -> {
                                              Valve valve = valves.get(mr.group(1));
                                              List<Valve> tunnels = Arrays.stream(mr.group(2).split(", ")).map(valves::get).toList();
                                              connectedValves.put(valve, tunnels);
                                          });
    }

    @Override
    protected Object part2() {
        return null;
    }
}
