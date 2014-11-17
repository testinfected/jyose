package com.vtence.jyose.fire;

public class Step {
    private final Pos pos;
    private final Move move;

    public Step(Pos pos, Move move) {
        this.pos = pos;
        this.move = move;
    }

    public Path takeOn(Path path) {
        return path.step(pos, move);
    }
}
