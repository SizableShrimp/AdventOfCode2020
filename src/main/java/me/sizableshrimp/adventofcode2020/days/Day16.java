package me.sizableshrimp.adventofcode2020.days;

import lombok.Value;
import me.sizableshrimp.adventofcode2020.helper.MatchWrapper;
import me.sizableshrimp.adventofcode2020.helper.Parser;
import me.sizableshrimp.adventofcode2020.helper.Processor;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day16 extends SeparatedDay {
    private List<Rule> rules;
    private Set<Range> ranges;
    private int[] myTicket;
    private Set<int[]> validTickets;
    private int[][] nearby;

    @Override
    protected Object part1() {
        int count = 0;

        for (int[] ticket : nearby) {
            boolean valid = true;
            for (int field : ticket) {
                for (Range range : ranges) {
                    valid = range.inRange(field);
                    if (valid)
                        break;
                }
                if (!valid) {
                    count += field;
                    break;
                }
            }
            if (valid)
                validTickets.add(ticket);
        }

        return count;
    }

    @Override
    protected Object part2() {
        Map<Integer, Set<Rule>> possible = new HashMap<>();

        for (Rule selected : rules) {
            for (int i = 0; i < rules.size(); i++) {
                boolean valid = true;
                for (int[] ticket : validTickets) {
                    int field = ticket[i];
                    valid = selected.first.inRange(field) || selected.second.inRange(field);
                    if (!valid)
                        break;
                }
                if (valid)
                    possible.computeIfAbsent(i, x -> new HashSet<>()).add(selected);
            }
        }

        long val = 1;
        while (!possible.isEmpty()) {
            var iter = possible.entrySet().iterator();
            while (iter.hasNext()) {
                var entry = iter.next();
                int index = entry.getKey();
                Set<Rule> set = entry.getValue();
                if (set.size() == 1) {
                    Rule rule = set.iterator().next();
                    if (rule.field.startsWith("departure"))
                        val *= myTicket[index];
                    iter.remove();
                    possible.forEach((k, v) -> v.remove(rule));
                }
            }
            possible.values().removeIf(Set::isEmpty);
        }

        return val;
    }

    @Override
    protected void parse() {
        List<List<String>> input = Processor.split(lines, String::isBlank);
        rules = new ArrayList<>();
        ranges = new HashSet<>();
        myTicket = parseTicket(input.get(1).get(1));
        validTickets = new HashSet<>();

        for (String rule : input.get(0)) {
            MatchWrapper match = Parser.parseMatch("(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)", rule);

            Range first = new Range(match.groupInt(2), match.groupInt(3));
            Range second = new Range(match.groupInt(4), match.groupInt(5));
            ranges.add(first);
            ranges.add(second);
            rules.add(new Rule(match.group(1), first, second));
        }
        List<String> rawNearby = input.get(2);
        nearby = new int[rawNearby.size() - 1][];

        for (int i = 1; i < rawNearby.size(); i++) {
            nearby[i - 1] = parseTicket(rawNearby.get(i));
        }
    }

    private int[] parseTicket(String line) {
        String[] split = line.split(",");
        int[] ticket = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            ticket[i] = Integer.parseInt(split[i]);
        }

        return ticket;
    }

    @Value
    private static class Rule {
        String field;
        Range first;
        Range second;
    }

    @Value
    private static class Range {
        int lower;
        int upper;

        private boolean inRange(int l) {
            return l >= lower && l <= upper;
        }
    }
}
