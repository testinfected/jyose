package com.vtence.jyose.fire;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

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
        return at(pos.row, pos.col);
    }

    public Optional<Pos> find(int what) {
        return findAll(what).findAny();
    }

    public Stream<Pos> findAll(int what) {
        return allRows().flatMap(row -> cellsContaining(what).apply(row).map(col -> Pos.at(row, col)));
    }

    private Stream<Integer> allRows() {
        return range(0, tiles.size()).boxed();
    }

    private Function<Integer, Stream<Integer>> cellsContaining(int what) {
        return row -> allCellsOf(row).filter(col -> at(row, col) == what);
    }

    private Stream<Integer> allCellsOf(int row) {
        return range(0, tiles.get(row).size()).boxed();
    }

    private int at(int row, int col) {
        return tiles.get(row).get(col);
    }
}
