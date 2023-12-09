package com.chandler.aoc.year23;

import com.chandler.aoc.common.Day;

import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

class Day08 extends Day {

    public static void main(String[] args) {
        new Day08().run();
    }

    private final Map<String, Node> nodes =
        stream().filter(not(String::isBlank))
                .map(Node::from)
                .collect(toMap(Node::label, identity()));

    @Override
    protected Object part1() {
        var instructions = nodes.get("instructions").left();

        return getNumSteps(nodes.get("AAA"), instructions, node -> !node.label().equalsIgnoreCase("ZZZ"));
    }

    @Override
    protected Object part2() {
        var instructions = nodes.get("instructions").left();

        return nodes.entrySet().stream()
                    .filter(labelToNode -> labelToNode.getKey().endsWith("A"))
                    .map(Map.Entry::getValue)
                    .map(it -> getNumSteps(it, instructions, node -> !node.label().endsWith("Z")))
                    .reduce(1L, (a, b) -> (a * b) / gcd(a, b));
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private long getNumSteps(Node node, String instructions, Predicate<Node> condition) {
        int i = 0;
        while (condition.test(node)) {
            node = instructions.charAt(i++ % instructions.length()) == 'L'
                ? nodes.get(node.left())
                : nodes.get(node.right());
        }
        return i;
    }

    private static final Pattern instructionsPattern = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");

    private record Node(String label, String left, String right, boolean endsInZ) {
        public static Node from(String line) {
            Matcher matcher = instructionsPattern.matcher(line);

            return matcher.matches()
                ? new Node(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(1).endsWith("Z"))
                : new Node("instructions", line, null, false);
        }
    }

}
