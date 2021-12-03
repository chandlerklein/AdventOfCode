package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;

import static java.lang.Integer.parseInt;

public class Day3 extends Day {

    public Day3() {
        super("2021", "3");
    }

    public static void main(String[] args) {
        new Day3().printParts();
    }

    @Override
    protected Object part1() {
        String[] strings = dayStream().toArray(String[]::new);
        int strLength = strings[0].length();

        int zeroCount = 0;
        int oneCount = 0;
        StringBuilder tempGammaRate = new StringBuilder();
        StringBuilder tempEpsilonRate = new StringBuilder();

        for (int i = 0; i < strLength; i++) {
            for (String string : strings) {
                if (string.charAt(i) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }
            if (zeroCount > oneCount) {
                tempGammaRate.append("0");
                tempEpsilonRate.append("1");
            } else {
                tempGammaRate.append("1");
                tempEpsilonRate.append("0");
            }
            zeroCount = 0;
            oneCount = 0;
        }
        int gammaRate = parseInt(tempGammaRate.toString(), 2);
        int epsilonRate = parseInt(tempEpsilonRate.toString(), 2);

        return gammaRate * epsilonRate;
    }

    @Override
    protected Object part2() {
        return null;
    }

}
