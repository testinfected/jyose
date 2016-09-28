package com.vtence.jyose.fire;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class CommandCenter {

    public static final int PLANE = 'P';
    public static final int FIRE = 'F';
    public static final int WATER = 'W';

    private final Supplier<? extends RuntimeException> invalidMap = () -> new IllegalArgumentException("Invalid map");

    public Stream<Vector> planAttack(Terrain terrain) {
        Vector plane = terrain.find(PLANE).orElseThrow(invalidMap);
        Vector fire = terrain.find(FIRE).orElseThrow(invalidMap);
        Stream<Vector> waters = terrain.findAll(WATER);
        Optional<Path> shortest = waters.map(water ->
                Path.concat(
                        Navigation.on(terrain).avoiding(FIRE).findPath(plane, water).orElseThrow(invalidMap),
                        Navigation.on(terrain).findPath(water, fire).orElseThrow(invalidMap)))
                .min(pathLength());

        return shortest.orElseThrow(invalidMap).steps();
    }

    public Stream<Vector> trainPilot(Terrain terrain) {
        Vector plane = terrain.find(PLANE).orElseThrow(invalidMap);
        Stream<Vector> water = terrain.findAll(WATER);

        Optional<Path> shortest =
                water.map(w -> Navigation.on(terrain).findPath(plane, w).orElseThrow(invalidMap)).min(pathLength());

        return shortest.orElseThrow(invalidMap).steps();
    }

    private Comparator<Path> pathLength() {
        return comparing(Path::length);
    }
}
