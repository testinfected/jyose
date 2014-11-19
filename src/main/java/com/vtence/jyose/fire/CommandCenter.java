package com.vtence.jyose.fire;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.function.BinaryOperator.minBy;

public class CommandCenter {

    public static final int PLANE = 'P';
    public static final int FIRE = 'F';
    public static final int WATER = 'W';

    private final Supplier<? extends RuntimeException> invalidMap = () -> new IllegalArgumentException("Invalid map");

    public Stream<Move> planAttack(Terrain terrain) {
        Pos plane = terrain.find(PLANE).orElseThrow(invalidMap);
        Pos water = terrain.find(WATER).orElseThrow(invalidMap);
        Pos fire = terrain.find(FIRE).orElseThrow(invalidMap);
        return Stream.concat(
                Navigation.on(terrain).avoiding(FIRE).findPath(plane, water).orElseThrow(invalidMap).moves(),
                Navigation.on(terrain).findPath(water, fire).orElseThrow(invalidMap).moves());
    }

    public Stream<Move> trainPilot(Terrain terrain) {
        Pos plane = terrain.find(PLANE).orElseThrow(invalidMap);
        Stream<Pos> water = terrain.findAll(WATER);

        Optional<Path> shortest =
                water.map(w -> Navigation.on(terrain).findPath(plane, w).get()).reduce(shortestPath());
        return shortest.orElseThrow(invalidMap).moves();
    }

    private BinaryOperator<Path> shortestPath() {
        return minBy(comparing(Path::length, naturalOrder()));
    }
}
