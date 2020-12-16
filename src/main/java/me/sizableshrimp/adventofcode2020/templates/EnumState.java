/*
 * AdventOfCode2020
 * Copyright (C) 2020 SizableShrimp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
