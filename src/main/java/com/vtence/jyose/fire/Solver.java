package com.vtence.jyose.fire;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

public class Solver {

    public Optional<Path> solve(Pos from, Pos to) {
        return pathToGoal(Path.startingAt(from), to);
    }

    private Optional<Path> pathToGoal(Path start, Pos goal) {
        Queue<Path> frontier = new ArrayDeque<>();
        frontier.add(start);

        while (!frontier.isEmpty()) {
            Path current = frontier.remove();
            if (current.leadsTo(goal)) return Optional.of(current);

            Stream<Step> neighbors = current.pos().neighbors();
            neighbors.map(current::advance).forEach(frontier::add);
        }

        return Optional.empty();
    }
}