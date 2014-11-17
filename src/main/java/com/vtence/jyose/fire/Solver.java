package com.vtence.jyose.fire;

import java.util.Optional;
import java.util.stream.Stream;

public class Solver {

    public Optional<Path> solve(Pos from, Pos to) {
        return pathToGoal(Path.startingAt(from), to);
    }

    private Optional<Path> pathToGoal(Path start, Pos goal) {
        Area explored = new Area();
        explored.expand(start);

        while (!explored.done()) {
            Path next = explored.visitNext();
            if (next.pos().equals(goal)) return Optional.of(next);

            Stream<Step> neighbors = next.pos().neighbors();
            neighbors.map(next::advance).forEach(explored::expand);
        }

        return Optional.empty();
    }
}