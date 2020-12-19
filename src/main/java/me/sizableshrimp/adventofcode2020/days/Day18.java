package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.Parser;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Day18 extends SeparatedDay {
    @Override
    protected Object part1() {
        return sumExpressions(Map.of('+', 1, '*', 1));
    }

    @Override
    protected Object part2() {
        return sumExpressions(Map.of('+', 2, '*', 1));
    }

    private long sumExpressions(Map<Character, Integer> priority) {
        long sum = 0;
        for (String line : lines) {
            sum += evaluate(line, priority);
        }
        return sum;
    }

    // Dijkstra's shunting-yard algorithm modified to calculate values on the fly
    private long evaluate(String expression, Map<Character, Integer> priority) {
        // Some expressions return long values; otherwise they overflow
        Deque<Long> values = new ArrayDeque<>();
        Deque<Character> ops = new ArrayDeque<>();

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                String number = Parser.findFirstMatch("(\\d+)", expression.substring(i)).group();
                i += number.length();
                values.push(Long.parseLong(number));
                continue;
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    values.push(parseExpression(values.pop(), ops.pop(), values.pop()));
                }
                ops.pop();
            } else if (c != ' ') { // Operator
                while (!ops.isEmpty() && priority.getOrDefault(ops.peek(), 0) >= priority.get(c)) {
                    values.push(parseExpression(values.pop(), ops.pop(), values.pop()));
                }
                ops.push(c);
            }
            i++;
        }
        while (!ops.isEmpty()) {
            values.push(parseExpression(values.pop(),
                    ops.pop(),
                    values.pop()));
        }

        return values.pop();
    }

    private long parseExpression(long left, char op, long right) {
        return switch (op) {
            case '+' -> left + right;
            case '*' -> left * right;
            default -> throw new IllegalStateException("Unexpected value: " + op);
        };
    }
}
