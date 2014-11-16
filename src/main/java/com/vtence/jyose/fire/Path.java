package com.vtence.jyose.fire;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;

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

    public List<Move> moves() {
        return unmodifiableList(moves);
    }

    public boolean leadsTo(Pos pos) {
        return this.pos.equals(pos);
    }

    public Path advance(Step step) {
        return step.make(this);
    }

    public Path step(Pos pos, Move move) {
        List<Move> history = new ArrayList<>(moves);
        history.add(move);
        return new Path(pos, history);
    }

    @Override
    public String toString() {
        return pos.toString() + moves.stream().map(Move::toString).collect(joining("->", "{", "}"));
    }
}
