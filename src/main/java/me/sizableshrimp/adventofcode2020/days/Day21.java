package me.sizableshrimp.adventofcode2020.days;

import me.sizableshrimp.adventofcode2020.helper.MatchWrapper;
import me.sizableshrimp.adventofcode2020.helper.MultiMap;
import me.sizableshrimp.adventofcode2020.helper.Parser;
import me.sizableshrimp.adventofcode2020.helper.Processor;
import me.sizableshrimp.adventofcode2020.templates.SeparatedDay;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Day21 extends SeparatedDay {
    private Map<List<String>, Set<String>> foods;
    private Set<String> allIngredients;
    private MultiMap<String, String> allergenCandidates;

    @Override
    protected Object part1() {
        allergenCandidates = new MultiMap<>();
        for (Map.Entry<List<String>, Set<String>> food : foods.entrySet()) {
            List<String> ingredients = food.getKey();
            Set<String> allergens = food.getValue();

            for (String allergen : allergens) {
                allergenCandidates.put(allergen, Processor.intersection(allergenCandidates.get(allergen), ingredients));
            }
        }

        Set<String> impossible = new HashSet<>(allIngredients);
        for (Set<String> possible : allergenCandidates.values()) {
            impossible.removeAll(possible);
        }

        long count = 0;
        for (List<String> ingredients : foods.keySet()) {
            for (String ingredient : ingredients) {
                if (impossible.contains(ingredient))
                    count++;
            }
        }
        return count;
    }

    @Override
    protected Object part2() {
        Map<String, String> found = new TreeMap<>(Comparator.naturalOrder());

        // Similar to Day 16 part 2
        while (!allergenCandidates.isEmpty()) {
            for (Map.Entry<String, Set<String>> entry : allergenCandidates.entrySet()) {
                Set<String> set = entry.getValue();
                if (set.isEmpty())
                    continue;
                set.removeAll(found.values());
                if (set.size() == 1) {
                    found.put(entry.getKey(), set.iterator().next());
                }
            }
            allergenCandidates.entrySet().removeIf(e -> e.getValue().isEmpty());
        }

        return String.join(",", found.values());
    }

    @Override
    protected void parse() {
        foods = new HashMap<>();
        allIngredients = new HashSet<>();

        for (String line : lines) {
            MatchWrapper match = Parser.parseMatch("(.+) \\(contains (.+)\\)", line);

            List<String> ingredients = Arrays.asList(match.group(1).split(" "));
            Set<String> allergens = new HashSet<>(Arrays.asList(match.group(2).split(", ")));
            foods.put(ingredients, allergens);
            allIngredients.addAll(ingredients);
        }
    }
}
