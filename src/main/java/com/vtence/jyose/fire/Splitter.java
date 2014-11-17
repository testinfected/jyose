package com.vtence.jyose.fire;

import java.util.ArrayList;
import java.util.List;

public class Splitter {

    private final int length;

    public Splitter(int length) {
        this.length = length;
    }

    public static Splitter fixedLength(int length) {
        return new Splitter(length);
    }

    public String[] split(String sequence) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i += length) {
            lines.add(sequence.substring(i, i + length));
        }
        return lines.toArray(new String[lines.size()]);
    }
}
