package com.chandler.aoc.year2023;

import com.chandler.aoc.common.Day;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

public class Day05 extends Day {

    public static void main(String[] args) {
        new Day05().run();
    }

    @Override
    protected Object part1() {
        List<String> groups = Pattern.compile("\n\n").splitAsStream(string()).toList();

        Pattern mappingPattern = Pattern.compile("(\\w+)-to-(\\w+)");

        Map<String, String> mapTitleToMapTitle = new HashMap<>();

        Map<String, RangeMap<Long, Map.Entry<Long, Long>>> mapTitleToRanges = new HashMap<>();

        for (int i = 1; i < groups.size(); i++) {
            String[] group = groups.get(i).split("\n");

            Matcher matcher = mappingPattern.matcher(group[0]);
            if (!matcher.find()) throw new IllegalStateException("No match");
            String from = matcher.group(1);
            String to = matcher.group(2);
            mapTitleToMapTitle.put(from, to);

            RangeMap<Long, Map.Entry<Long, Long>> rangeMap = TreeRangeMap.create();

            for (int j = 1; j < group.length; j++) {
                String[] mapDetails = group[j].split("\\s");
                long destStart = parseLong(mapDetails[0]);
                long sourceStart = parseLong(mapDetails[1]);
                long rangeLength = parseLong(mapDetails[2]);

                rangeMap.put(Range.closed(sourceStart, sourceStart + rangeLength - 1),
                             Map.entry(sourceStart, destStart));
            }
            mapTitleToRanges.put(to, rangeMap);
        }

        return Arrays.stream(groups.get(0).substring(7).split("\\s"))
                     .map(Long::parseLong)
                     .map(seed -> getLocation(seed, mapTitleToMapTitle, mapTitleToRanges))
                     .min(Long::compareTo)
                     .orElseThrow();
    }

    @Override
    protected Object part2() {
        // wtf 🤠
        return null;
    }

    private long getLocation(
        long rangeInput,
        Map<String, String> mapTitleToMapTitle,
        Map<String, RangeMap<Long, Map.Entry<Long, Long>>> mapTitleToRanges
    ) {
        String title = "seed";
        do {
            title = mapTitleToMapTitle.get(title);
            var startValues = mapTitleToRanges.get(title).get(rangeInput);
            rangeInput = startValues != null ? rangeInput - startValues.getKey() + startValues.getValue() : rangeInput;
        } while (!title.equals("location"));

        return rangeInput;
    }

}
