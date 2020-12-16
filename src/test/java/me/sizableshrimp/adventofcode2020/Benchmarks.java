package me.sizableshrimp.adventofcode2020;

import me.sizableshrimp.adventofcode2020.templates.Day;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class Benchmarks {
    @Benchmark
    // @Fork(value = 3, warmups = 1)
    // @Measurement(iterations = 3, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void parseAndEvaluate(DayState state) {
        state.instance.parseAndEvaluate();
    }

    @Benchmark
    @SuppressWarnings("deprecation")
    public void parseOnly(DayState state) {
        state.instance.parseTesting();
    }

    @State(Scope.Thread)
    public static class DayState {
        Day instance;

        @Setup(Level.Trial)
        public void setup() throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
            instance = Main.getCurrentDayConstructor().newInstance();
        }

        @TearDown(Level.Trial)
        public void teardown() {
            instance = null;
        }
    }
}
