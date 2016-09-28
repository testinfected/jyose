package com.vtence.jyose.fire;

import java.util.stream.Stream;

public final class Moves {

    public static final Vector RIGHT = new Vector(1, 0);
    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector UP = new Vector(0, -1);

    public static Stream<Vector> stream() {
        return Stream.of(RIGHT, DOWN, LEFT, UP);
    }
}
