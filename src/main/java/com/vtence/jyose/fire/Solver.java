package com.vtence.jyose.fire;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Solver {

    private final Terrain terrain;
    private final Set<Integer> obstacles = new HashSet<>();

    public Solver(Terrain terrain) {
        this.terrain = terrain;
    }

    public Solver avoid(int obstacle) {
        obstacles.add(obstacle);
        return this;
    }

    public Optional<Path> solve(Pos from, Pos to) {
        return pathToGoal(Path.startingAt(from), to);
    }

    private Optional<Path> pathToGoal(Path start, Pos goal) {
        Area explored = new Area();
        explored.expand(start);

        while (!explored.done()) {
            Path next = explored.visitNext();
            if (next.pos().equals(goal)) return Optional.of(next);

            Stream<Step> neighbors = legalNeighborsOnly(allNeighbors(next));
            neighbors.map(next::advance).forEach(explored::expand);
        }

        return Optional.empty();
    }

    private Stream<Step> legalNeighborsOnly(Stream<Step> neighbors) {
        return neighbors.filter(legal().and(obstacle().negate()));
    }

    private Stream<Step> allNeighbors(Path next) {
        return next.pos().neighbors();
    }

    private Predicate<Step> legal() {
        return s -> terrain.contains(s.pos);
    }

    private Predicate<Step> obstacle() {
        return s -> obstacles.contains(terrain.at(s.pos));
    }
}