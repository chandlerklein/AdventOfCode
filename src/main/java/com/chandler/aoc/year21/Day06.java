package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Day06 extends Day {

    public static void main(String[] args) {
        new Day06().run();
    }

    @Override
    protected Object part1() {
        var lanternFish =
                stream(",")
                        .map(Integer::valueOf)
                        .map(LanternFish::new)
                        .collect(toList());
        return getFishAfterDays(lanternFish, 80);
    }

    @Override
    protected Object part2() {
        TreeMap<Integer, Long> lanternFishNum =
                stream(",")
                        .collect(
                                groupingBy(
                                        Integer::parseInt,
                                        TreeMap::new,
                                        counting()));
        return getFishAfterDaysOptimized(lanternFishNum, 256);
    }

    private long getFishAfterDays(List<LanternFish> lanternFish, int numDays) {
        for (int i = 0; i < numDays; i++) {
            int numFish = lanternFish.size();
            for (int j = 0; j < numFish; j++) {
                var currentFish = lanternFish.get(j);
                currentFish.decrementTimer();
                if (currentFish.getTimer() == -1) {
                    currentFish.setTimer(6);
                    lanternFish.add(new LanternFish(8));
                }
            }
        }
        return lanternFish.size();
    }

    private long getFishAfterDaysOptimized(Map<Integer, Long> lanternFish, int numDays) {
        for (int i = 0; i < numDays; i++) {
            Map<Integer, Long> temp = new TreeMap<>();
            lanternFish.forEach((k, v) -> {
                temp.put(k - 1, v);
                temp.put(k, 0L);
            });
            lanternFish.putAll(temp);
            var fishReady = lanternFish.get(-1);
            if (fishReady != null && fishReady > 0) {
                lanternFish.put(-1, 0L);
                lanternFish.compute(6, (k, v) -> (v != null) ? v + fishReady : fishReady);
                lanternFish.compute(8, (k, v) -> (v != null) ? v + fishReady : fishReady);
            }
        }
        return lanternFish.values()
                          .stream()
                          .mapToLong(l -> l)
                          .sum();
    }

    static class LanternFish {
        private int timer;

        public LanternFish(int timer) {
            this.timer = timer;
        }

        public int getTimer() {
            return timer;
        }

        public void setTimer(int timer) {
            this.timer = timer;
        }

        public void decrementTimer() {
            this.timer -= 1;
        }

        @Override
        public String toString() {
            return String.valueOf(timer);
        }
    }
}
