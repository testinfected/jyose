package com.vtence.jyose.fire;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Path {
    private final Pos pos;
    private final List<Move> moves;

    private Path(Pos pos, List<Move> history) {
        this.pos = pos;
        this.moves = history;
    }

    public static Path startingAt(Pos start) {
        return new Path(start, emptyList());
    }

    public Pos pos() {
        return pos;
    }

    public Stream<Move> moves() {
        return moves.stream();
    }

    public int length() {
        return moves.size();
    }

    public Path advance(Step step) {
        List<Move> history = new ArrayList<>(moves);
        history.add(step.move);
        return new Path(step.pos, history);
    }

    @Override
    public String toString() {
        return pos.toString() + moves.stream().map(Move::toString).collect(joining("->", "{", "}"));
    }

    public static Path concat(Path first, Path second) {
        return new Path(second.pos(), allMoves(first, second));
    }

    private static List<Move> allMoves(Path first, Path second) {
        List<Move> allMoves = first.moves().collect(toList());
        allMoves.addAll(second.moves().collect(toList()));
        return allMoves;
    }
}
