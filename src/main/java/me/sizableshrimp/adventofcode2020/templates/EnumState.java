/*
 * Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 */

package me.sizableshrimp.adventofcode2020.templates;

import me.sizableshrimp.adventofcode2020.helper.Parser;

/**
 * An interface meant to be put on enums to support mapping a {@code char} to an enum, and vice versa.
 * @see Parser#parseEnumState
 *
 * @param <T>
 */
public interface EnumState<T extends Enum<T> & EnumState<T>> {
    char getMappedChar();
}
