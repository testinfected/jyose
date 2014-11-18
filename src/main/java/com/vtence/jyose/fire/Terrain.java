package com.vtence.jyose.fire;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Terrain {
    private static final int PLANE = 'P';
    private static final int FIRE = 'F';
    private static final int WATER = 'W';

    private final List<List<Integer>> tiles;

    public Terrain(List<List<Integer>> tiles) {
        this.tiles = tiles;
    }

    public static Terrain parse(String... rows) {
        List<List<Integer>> tiles = Stream.of(rows).
                map(line -> line.chars().boxed().collect(toList())).
                collect(toList());
        return new Terrain(tiles);
    }

    public boolean contains(Pos pos) {
        return isDefined(pos.row, tiles) && isDefined(pos.col, tiles.get(pos.row));
    }

    private boolean isDefined(int index, List<?> map) {
        return index >= 0 && index < map.size();
    }

    public int at(Pos pos) {
        return tiles.get(pos.row).get(pos.col);
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
        int row = tiles.indexOf(tiles.stream().filter(line -> line.contains(what)).findFirst().get());
        int col = tiles.get(row).indexOf(what);
        return Pos.at(row, col);
    }
}
