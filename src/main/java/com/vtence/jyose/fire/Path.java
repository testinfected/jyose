package com.vtence.jyose.fire;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Path {
    private final Vector end;
    private final List<Vector> steps;

    Path(Vector end, List<Vector> steps) {
        this.end = end;
        this.steps = steps;
    }

    public static Path startingAt(Vector start) {
        return new Path(start, emptyList());
    }

    public Vector end() {
        return end;
    }

    public Stream<Vector> steps() {
        return steps.stream();
    }

    public int length() {
        return steps.size();
    }

    public Stream<Path> expand(Predicate<Path> matching) {
        return neighbours().map(p -> Path.concat(this, p)).filter(matching);
    }

    private Stream<Path> neighbours() {
        return Moves.stream().map(m -> new Path(end.plus(m), singletonList(m)));
    }

    public Path concat(Path other) {
        return new Path(other.end(), Stream.concat(steps.stream(), other.steps()).collect(toList()));
    }

    @Override
    public String toString() {
        return end.toString() + steps.stream().map(Vector::toString).collect(joining("->", "{", "}"));
    }

    public static Path concat(Path first, Path second) {
        return first.concat(second);
    }
}
