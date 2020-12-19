package me.sizableshrimp.adventofcode2020.days;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.sizableshrimp.adventofcode2020.helper.MatchWrapper;
import me.sizableshrimp.adventofcode2020.helper.Parser;
import me.sizableshrimp.adventofcode2020.helper.Processor;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class Day19 extends SeparatedDay {
    Map<Integer, Rule> rules;
    List<String> messages;

    @Override
    protected Object part1() {
        return matchCount(false);
    }

    @Override
    protected Object part2() {
        return matchCount(true);
    }

    private int matchCount(boolean recursive) {
        String regex = rules.get(0).genRegex(recursive);
        Pattern pattern = Pattern.compile(regex);
        int count = 0;

        for (String message : messages) {
            if (pattern.matcher(message).matches())
                count++;
        }

        return count;
    }

    @Override
    protected void parse() {
        List<List<String>> input = Processor.split(lines, String::isBlank);
        rules = new HashMap<>();
        messages = input.get(1);

        for (String ruleStr : input.get(0)) {
            MatchWrapper match = Parser.parseMatch("(\\d+): (.+)", ruleStr);
            int id = match.groupInt(1);
            String raw = match.group(2);
            Rule rule = rules.computeIfAbsent(id, Rule::new);

            if (raw.startsWith("\"")) {
                rule.setRaw(Character.toString(raw.charAt(1)));
                rule.setLiteral(true);
            } else {
                rule.setLiteral(false);

                for (String groupStr : raw.split("\\|")) {
                    String[] split = groupStr.trim().split(" ");
                    List<Rule> group = new ArrayList<>(split.length);
                    for (String subId : split) {
                        group.add(rules.computeIfAbsent(Integer.parseInt(subId), Rule::new));
                    }
                    rule.children.add(group);
                }
            }
        }
    }

    @Data
    @RequiredArgsConstructor
    private static class Rule {
        final int id;
        final List<List<Rule>> children = new ArrayList<>();
        boolean isLiteral;
        String raw;

        private String genRegex(boolean recursive) {
            if (isLiteral) {
                return raw;
            } else if (recursive && id == 8) {
                return '(' + children.get(0).get(0).genRegex(false) + "+" + ')';
            } else if (recursive && id == 11) {
                String first = children.get(0).get(0).genRegex(false);
                String second = children.get(0).get(1).genRegex(false);
                return gen(15, (i, builder) -> builder
                        .append(first).append('{').append(i + 1).append('}')
                        .append(second).append('{').append(i + 1).append('}'));
            }

            return gen(children.size(), (i, builder) -> {
                for (Rule rule : children.get(i)) {
                    builder.append(rule.genRegex(recursive));
                }
            });
        }

        private String gen(int length, BiConsumer<Integer, StringBuilder> consumer) {
            StringBuilder builder = new StringBuilder("(");

            for (int i = 0; i < length; i++) {
                consumer.accept(i, builder);
                if (i < length - 1)
                    builder.append('|');
            }

            return builder.append(')').toString();
        }
    }
}
