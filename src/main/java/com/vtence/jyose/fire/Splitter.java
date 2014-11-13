package com.vtence.jyose.fire;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Splitter {

    private final int length;

    public Splitter(int length) {
        this.length = length;
    }

    public static Splitter fixedLength(int length) {
        return new Splitter(length);
    }

    public Iterable<String> split(String sequence) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i += length) {
            lines.add(sequence.substring(i, i + length));
        }
        return lines;
    }

    public Stream<String> stream(String level) {
        return StreamSupport.stream(split(level).spliterator(), false);
    }
}
