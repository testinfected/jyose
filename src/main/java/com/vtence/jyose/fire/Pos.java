package com.vtence.jyose.fire;

import java.util.stream.Stream;

public class Pos {
    public final int x;
    public final int y;

    public Pos(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Pos at(int x, int y) {
        return new Pos(x, y);
    }

    public Pos plus(Pos other) {
        return new Pos(x + other.x, y + other.y);
    }

    public Stream<Step> neighbors() {
        return Stream.of(Move.values()).map(m -> new Step(m.from(this), m));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pos pos = (Pos) o;

        if (y != pos.y) return false;
        if (x != pos.x) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", x, y);
    }
}