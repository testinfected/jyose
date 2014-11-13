package com.vtence.jyose.fire;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Terrain {
    private static final int PLANE = 'P';
    private static final int FIRE = 'F';
    private static final int WATER = 'W';

    private final List<List<Integer>> map;

    public Terrain(List<List<Integer>> map) {
        this.map = map;
    }

    public static Terrain parse(String level, int width) {
        List<List<Integer>> map = Splitter.fixedLength(width).stream(level).
                map(line -> line.chars().boxed().collect(toList())).
                collect(toList());
        return new Terrain(map);
    }

    public boolean contains(Integer row, Integer col) {
        return map.size() > row && map.get(row).size() > col;
    }

    public Location planeLocation() {
        return findOnMap(PLANE);
    }

    public Location fireLocation() {
        return findOnMap(FIRE);
    }

    public Location waterLocation() {
        return findOnMap(WATER);
    }

    private Location findOnMap(int what) {
        int row = map.indexOf(map.stream().filter(line -> line.contains(what)).findFirst().get());
        int col = map.get(row).indexOf(what);
        return Location.at(row, col);
    }
}
