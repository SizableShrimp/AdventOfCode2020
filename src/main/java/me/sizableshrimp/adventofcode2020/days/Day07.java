package me.sizableshrimp.adventofcode2020.days;

import lombok.EqualsAndHashCode;
import lombok.Value;
import me.sizableshrimp.adventofcode2020.helper.MatchWrapper;
import me.sizableshrimp.adventofcode2020.helper.Parser;
import me.sizableshrimp.adventofcode2020.templates.Day;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Day07 extends Day {
    private static final Pattern bagPattern = Pattern.compile("(.+) bags contain (.+)\\.");
    private HashMap<String, Node> nodes;

    @Override
    protected Result evaluate() {
        // SHINY!!!! MY PRECIOUS!!!!
        Node gold = nodes.get("shiny gold");

        Set<Node> seen = new HashSet<>();
        Deque<Node> queue = new ArrayDeque<>(gold.parents);

        while (queue.peek() != null) {
            Node selected = queue.poll();
            if (!seen.add(selected))
                continue;
            Set<Node> parents = selected.parents;
            queue.addAll(parents);
        }

        return new Result(seen.size(), gold.calcChildren());
    }

    @Override
    protected void parse() {
        nodes = new HashMap<>();
        Parser.parseLines(bagPattern, lines, this::parseNode);
    }

    private void parseNode(MatchWrapper bagMatch) {
        Node node = nodes.computeIfAbsent(bagMatch.group(1), Node::new);
        String data = bagMatch.group(2);
        if (data.equals("no other bags"))
            return;

        String[] split = data.split(", ");
        for (String part : split) {
            MatchWrapper partMatch = Parser.parseMatch("(\\d+?) (.+?) bags?", part);
            int num = partMatch.groupInt(1);
            Node n = nodes.computeIfAbsent(partMatch.group(2), Node::new);
            node.children.put(n, num);
            n.parents.add(node);
        }
    }

    @Value
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    private static class Node {
        @EqualsAndHashCode.Include
        String name;
        Set<Node> parents = new HashSet<>();
        Map<Node, Integer> children = new HashMap<>();

        private int calcChildren() {
            int sum = 0;
            for (Map.Entry<Node, Integer> entry : children.entrySet()) {
                int num = entry.getValue();
                // Plus 1 is to count the initial bags themselves, then all of their children!
                sum += num * (entry.getKey().calcChildren() + 1);
            }
            return sum;
        }
    }
}
