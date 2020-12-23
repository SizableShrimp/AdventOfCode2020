package me.sizableshrimp.adventofcode2020.days;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.List;

public class Day23 extends SeparatedDay {
    private Node start;
    private Node end;
    private int max;
    private int labelSize;

    @Override
    protected Object part1() {
        Node one = playCupsGame(start, labelSize, 100);

        StringBuilder builder = new StringBuilder();
        Node current = one.next;
        do {
            builder.append(current.value);
            current = current.next;
        } while (current != one);

        return builder.toString();
    }

    @Override
    protected Object part2() {
        // Re-make the node
        parse();
        for (int i = max + 1; i <= 1_000_000; i++) {
            addToEnd(i);
        }

        Node one = playCupsGame(start, 1_000_000, 10_000_000);
        return (long) one.next.value * one.next.next.value;
    }

    private Node playCupsGame(Node current, int size, int moves) {
        // Used to access a node from its value
        Node[] fastPath = createFastPath(current, size);

        for (int move = 0; move < moves; move++) {
            current = doMove(current, fastPath);
        }

        return fastPath[1];
    }

    private Node doMove(Node current, Node[] fastPath) {
        Node startSub = current.next;
        Node endSub = current.next.next.next;
        current.setNext(endSub.next);
        endSub.setNext(null);
        List<Node> sub = List.of(startSub, startSub.next, endSub);

        Node destination = current;
        do {
            int nextIndex = destination.value - 1;
            if (nextIndex <= 0) {
                nextIndex = fastPath[fastPath.length - 1].value;
            }
            destination = fastPath[nextIndex];
        } while (sub.contains(destination));

        Node after = destination.next;
        destination.setNext(startSub);
        endSub.setNext(after);

        return current.next;
    }

    private Node[] createFastPath(Node start, int size) {
        Node[] fastPath = new Node[size + 1];
        Node current = start;

        for (int i = 0; i < size; i++) {
            fastPath[current.value] = current;
            current = current.next;
        }

        return fastPath;
    }

    @Override
    protected void parse() {
        start = null;
        end = null;
        max = -1;
        String line = lines.get(0);
        labelSize = line.length();
        String[] split = line.split("");

        for (String s : split) {
            int digit = Integer.parseInt(s);
            addToEnd(digit);
            if (digit > max)
                max = digit;
        }
    }

    private void addToEnd(int digit) {
        Node next = new Node(digit);
        if (start == null) {
            start = next;
            start.setNext(start);
            end = start;
        } else {
            next.setNext(start);
            end.setNext(next);
            end = next;
        }
    }

    @Data
    private static class Node {
        final int value;
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        Node next;
    }
}
