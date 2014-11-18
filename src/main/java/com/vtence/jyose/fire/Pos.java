package com.vtence.jyose.fire;

import java.util.stream.Stream;

public class Pos {
    public final int row;
    public final int col;

    public Pos(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public static Pos at(int row, int col) {
        return new Pos(row, col);
    }

    public Pos up() {
        return at(row - 1, col);
    }

    public Pos right() {
        return at(row, col + 1);
    }

    public Pos down() {
        return at(row + 1, col);
    }

    public Pos left() {
        return at(row, col - 1);
    }

    public Stream<Step> neighbors() {
        return Stream.of(Move.values()).map(m -> new Step(m.from(this), m));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pos pos = (Pos) o;

        if (col != pos.col) return false;
        if (row != pos.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", row, col);
    }
}