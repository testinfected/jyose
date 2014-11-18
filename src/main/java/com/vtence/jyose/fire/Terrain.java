package com.vtence.jyose.fire;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Terrain {
    private static final int PLANE = 'P';
    private static final int FIRE = 'F';
    private static final int WATER = 'W';

    private final List<List<Integer>> map;

    public Terrain(List<List<Integer>> map) {
        this.map = map;
    }

    public static Terrain parse(String... rows) {
        List<List<Integer>> map = Stream.of(rows).
                map(line -> line.chars().boxed().collect(toList())).
                collect(toList());
        return new Terrain(map);
    }

    public boolean contains(Pos pos) {
        return isDefined(pos.row, map) && isDefined(pos.col, map.get(pos.row));
    }

    private boolean isDefined(int index, List<?> map) {
        return index >= 0 && index < map.size();
    }

    public int at(Pos pos) {
        return map.get(pos.row).get(pos.col);
    }

    public Pos plane() {
        return find(PLANE);
    }

    public Pos fire() {
        return find(FIRE);
    }

    public Pos water() {
        return find(WATER);
    }

    public Pos find(int what) {
        int row = map.indexOf(map.stream().filter(line -> line.contains(what)).findFirst().get());
        int col = map.get(row).indexOf(what);
        return Pos.at(row, col);
    }
}
