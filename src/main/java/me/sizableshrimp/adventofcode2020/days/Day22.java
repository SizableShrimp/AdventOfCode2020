package me.sizableshrimp.adventofcode2020.days;

import lombok.Value;
import me.sizableshrimp.adventofcode2020.helper.ListConvert;
import me.sizableshrimp.adventofcode2020.helper.Processor;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day22 extends SeparatedDay {
    private Deque<Integer> player1;
    private Deque<Integer> player2;

    @Override
    protected Object part1() {
        return getScore(false);
    }

    @Override
    protected Object part2() {
        return getScore(true);
    }

    private int getScore(boolean recursive) {
        Deque<Integer> winner = playGame(new LinkedList<>(player1), new LinkedList<>(player2), recursive).winner;
        // System.out.println(winner);

        int score = 0;
        int i = 1;
        while (!winner.isEmpty()) {
            score += i * winner.pollLast();
            i++;
        }
        return score;
    }

    private Winner playGame(Deque<Integer> player1, Deque<Integer> player2, boolean recursive) {
        Set<Integer> previous = recursive ? new HashSet<>() : null;

        while (true) {
            if (recursive && !previous.add(player1.hashCode() * 31 + player2.hashCode())) {
                return new Winner(player1, true);
            }
            int first = player1.pop();
            int second = player2.pop();

            if (recursive && player1.size() >= first && player2.size() >= second) {
                Deque<Integer> copy1 = player1.stream().limit(first).collect(Collectors.toCollection(LinkedList::new));
                Deque<Integer> copy2 = player2.stream().limit(second).collect(Collectors.toCollection(LinkedList::new));
                Winner subGameWinner = playGame(copy1, copy2, true);
                // System.out.println(subGameWinner);
                if (subGameWinner.player1Won) {
                    player1.addLast(first);
                    player1.addLast(second);
                } else {
                    player2.addLast(second);
                    player2.addLast(first);
                }
            } else if (first > second) {
                player1.addLast(first);
                player1.addLast(second);
            } else if (first < second) {
                player2.addLast(second);
                player2.addLast(first);
            }

            if (player1.isEmpty() || player2.isEmpty()) {
                boolean player1Won = player2.isEmpty();
                return new Winner(player1Won ? player1 : player2, player1Won);
            }
        }
    }

    @Override
    protected void parse() {
        player1 = new LinkedList<>();
        player2 = new LinkedList<>();
        List<List<String>> input = Processor.split(lines, String::isBlank);

        for (List<String> player : input) {
            List<Integer> deck = ListConvert.ints(player.subList(1, player.size()));
            if (player1.isEmpty()) {
                player1.addAll(deck);
            } else {
                player2.addAll(deck);
            }
        }
    }

    @Value
    private static class Winner {
        Deque<Integer> winner;
        boolean player1Won;
    }
}
