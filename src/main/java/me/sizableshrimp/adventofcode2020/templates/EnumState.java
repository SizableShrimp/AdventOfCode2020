package me.sizableshrimp.adventofcode2020.templates;

import me.sizableshrimp.adventofcode2020.helper.Parser;

/**
 * An interface meant to be put on enums to support mapping a {@code char} to an enum, and vice versa.
 *
 * @param <T>
 * @see Parser#parseEnumState
 */
public interface EnumState<T extends Enum<T> & EnumState<T>> {
    char getMappedChar();
}
