package com.vtence.jyose.fire;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Terrain {
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

    public Optional<Pos> find(int what) {
        return findAll(what).findAny();
    }

    public Stream<Pos> findAll(int what) {
        return tiles.stream().
                filter(line -> line.contains(what)).
                flatMap(line -> line.stream().filter(c -> c == what).map(c -> {
                    int row = tiles.indexOf(line);
                    int col = tiles.get(row).indexOf(what);
                    return Pos.at(row, col);
                }));
    }
}
