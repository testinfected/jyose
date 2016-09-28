package com.vtence.jyose.fire;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Area {
    private final Queue<Path> frontier = new ArrayDeque<>();
    private final Set<Vector> visited = new HashSet<>();

    public boolean done() {
        return frontier.isEmpty();
    }

    public Path visitNext() {
        return frontier.remove();
    }

    public void expand(Path path) {
        if (!visited.contains(path.end())) {
            visited.add(path.end());
            frontier.add(path);
        }
    }
}
