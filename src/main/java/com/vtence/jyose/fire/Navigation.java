package com.vtence.jyose.fire;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class Navigation {

    private final Terrain terrain;
    private final Set<Integer> obstacles = new HashSet<>();

    protected Navigation(Terrain terrain) {
        this.terrain = terrain;
    }

    public static Navigation on(Terrain terrain) {
        return new Navigation(terrain);
    }

    public Navigation avoiding(int obstacle) {
        obstacles.add(obstacle);
        return this;
    }

    public Optional<Path> findPath(Vector from, Vector to) {
        Area explored = new Area();
        explored.expand(Path.startingAt(from));

        while (!explored.done()) {
            Path next = explored.visitNext();
            if (destinationReached(next, to)) return Optional.of(next);
            next.expand(legalPaths()).forEach(explored::expand);
        }

        return Optional.empty();
    }

    private boolean destinationReached(Path path, Vector destination) {
        return destination.equals(path.end());
    }

    private Predicate<Path> legalPaths() {
        return legal().and(obstacle().negate());
    }

    private Predicate<Path> legal() {
        return p -> terrain.contains(p.end());
    }

    private Predicate<Path> obstacle() {
        return p -> obstacles.contains(terrain.at(p.end()));
    }
}