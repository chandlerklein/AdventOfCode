package com.chandler.aoc.year2021;

import com.chandler.aoc.common.Day;

import static java.lang.Integer.parseInt;

public class Day03 extends Day {

    public static void main(String[] args) {
        new Day03().run();
    }

    @Override
    public Object part1() {
        String[] strings = stream().toArray(String[]::new);

        int zeroCount = 0;
        StringBuilder tempGammaRate = new StringBuilder();

        for (int i = 0; i < strings[0].length(); i++) {
            for (String string : strings) {
                if (string.charAt(i) == '0') zeroCount++;
            }
            int oneCount = strings.length - zeroCount;
            tempGammaRate.append(zeroCount > oneCount ? "0" : "1");
            zeroCount = 0;
        }
        int gammaRate = parseInt(tempGammaRate.toString(), 2);
        int epsilonRate = parseInt(getFlippedBinary(tempGammaRate), 2);
        return gammaRate * epsilonRate;
    }

    private String getFlippedBinary(StringBuilder tempEpsilonRate) {
        return tempEpsilonRate.toString().replace("10", "01");
    }

    @Override
    public Object part2() {
        // nope 🤠
        return null;
    }

}
