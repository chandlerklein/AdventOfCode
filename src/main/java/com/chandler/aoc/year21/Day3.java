package com.chandler.aoc.year21;

import com.chandler.aoc.common.Day;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.replaceChars;

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
        return replaceChars(tempEpsilonRate.toString(), "10", "01");
    }

    @Override
    protected Object part2() {
        // nope 🤠
        return null;
    }

}
