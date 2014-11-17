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

    public boolean contains(int row, int col) {
        return map.size() > row && map.get(row).size() > col;
    }

    public boolean legal(Pos pos) {
        return pos.within(this);
    }

    public Pos plane() {
        return findOnMap(PLANE);
    }

    public Pos fire() {
        return findOnMap(FIRE);
    }

    public Pos water() {
        return findOnMap(WATER);
    }

    private Pos findOnMap(int what) {
        int row = map.indexOf(map.stream().filter(line -> line.contains(what)).findFirst().get());
        int col = map.get(row).indexOf(what);
        return Pos.at(row, col);
    }

    public static void main(String[] args) {
        Terrain.parse(Splitter.fixedLength(2).split("...."));
    }
}
