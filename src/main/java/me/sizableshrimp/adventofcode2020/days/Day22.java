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
        Winner winner = playGame(new LinkedList<>(player1), new LinkedList<>(player2), recursive);
        // System.out.println(winner);

        int score = 0;
        Deque<Integer> player = winner.winner;
        int i = player.size();
        for (int card : player) {
            score += i * card;
            i--;
        }
        return score;
    }

    private Winner playGame(Deque<Integer> player1, Deque<Integer> player2, boolean recursive) {
        Set<List<Integer>> previous = new HashSet<>();

        while (!player1.isEmpty() && !player2.isEmpty()) {
            // NOTE: Possible hashcode collision; for safe results use a Set<List<List<Integer>> in conjunction with List.copyOf
            if (!previous.add(List.of(player1.hashCode(), player2.hashCode()))) {
                return new Winner(player1, true);
            }
            int first = player1.pop();
            int second = player2.pop();

            if (recursive && player1.size() >= first && player2.size() >= second) {
                Winner subGameWinner = playGame(takeLength(player1, first), takeLength(player2, second), true);
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
        }

        boolean player1Won = player2.isEmpty();
        return new Winner(player1Won ? player1 : player2, player1Won);
    }

    private LinkedList<Integer> takeLength(Deque<Integer> player, int length) {
        LinkedList<Integer> next = new LinkedList<>();
        int i = 0;
        for (int card : player) {
            next.add(card);
            i++;
            if (i == length)
                break;
        }
        return next;
        // return StreamEx.of(player.stream()).limit(length).toCollection(LinkedList::new);
    }

    @Override
    protected void parse() {
        player1 = null;
        player2 = null;
        List<List<String>> input = Processor.split(lines, String::isBlank);

        for (List<String> player : input) {
            List<Integer> deck = ListConvert.ints(player.subList(1, player.size()));
            if (player1 == null) {
                player1 = new LinkedList<>(deck);
            } else {
                player2 = new LinkedList<>(deck);
            }
        }
    }

    @Value
    private static class Winner {
        Deque<Integer> winner;
        boolean player1Won;
    }
}
