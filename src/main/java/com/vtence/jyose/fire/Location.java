package com.vtence.jyose.fire;

public class Location {
    public final Integer row;
    public final Integer col;

    public Location(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public static Location at(int row, int col) {
        return new Location(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (!col.equals(location.col)) return false;
        if (!row.equals(location.row)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row.hashCode();
        result = 31 * result + col.hashCode();
        return result;
    }
}