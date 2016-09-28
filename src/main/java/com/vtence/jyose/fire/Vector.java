package com.vtence.jyose.fire;

public class Vector {
    public final int x;
    public final int y;

    public Vector(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Vector pos(int x, int y) {
        return new Vector(x, y);
    }

    public Vector plus(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector other = (Vector) o;

        if (y != other.y) return false;
        if (x != other.x) return false;

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