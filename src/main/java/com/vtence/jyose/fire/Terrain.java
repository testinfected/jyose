package com.vtence.jyose.fire;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
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
        return tiles.get(pos.row).get(pos.col);
    }

    public Optional<Pos> find(int what) {
        return findAll(what).findAny();
    }

    public Stream<Pos> findAll(int what) {
        return rowsContaining(what).flatMap(row -> locateAll(what).apply(row));
    }

    private Stream<Integer> rowsContaining(int what) {
        return allRows().filter(rowContains(what));
    }

    private Stream<Integer> allRows() {
        return range(0, tiles.size()).boxed();
    }

    private Predicate<Integer> rowContains(int what) {
        return row -> tiles.get(row).contains(what);
    }

    private Function<Integer, Stream<Pos>> locateAll(int what) {
        return row -> allColsOf(row).filter(colContains(what).apply(row)).map((col) -> Pos.at(row, col));
    }

    private Stream<Integer> allColsOf(int row) {
        return range(0, tiles.get(row).size()).boxed();
    }

    private Function<Integer, Predicate<Integer>> colContains(int what) {
        return (row) -> col -> tiles.get(row).get(col) == what;
    }
}
