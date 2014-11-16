package com.vtence.jyose.fire;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

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

            List<Step> neighbors = current.pos().neighbors();
            neighbors.stream().map(current::advance).forEach(frontier::add);
        }

        return Optional.empty();
    }
}