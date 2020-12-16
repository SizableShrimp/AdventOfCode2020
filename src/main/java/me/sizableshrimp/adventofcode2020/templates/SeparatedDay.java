package me.sizableshrimp.adventofcode2020.templates;

/**
 * A {@link Day} which has both parts of the challenge separated into different internal methods.
 * This might be applied to a completed day after I have solved it and believe it would promote readability to
 * separate the parts.
 */
public abstract class SeparatedDay extends Day {
    @Override
    protected final Result evaluate() {
        return new Result(part1(), part2());
    }

    /**
     * @return The result of part 1
     */
    protected abstract Object part1();

    /**
     * @return The result of part 2
     */
    protected abstract Object part2();
}
