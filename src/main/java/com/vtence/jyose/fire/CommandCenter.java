package com.vtence.jyose.fire;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class CommandCenter {

    public static final char FIRE = 'F';
    private static final char WATER = 'W';

    public List<Move> planAttack(Terrain terrain) {
        Pos plane = terrain.plane();
        Pos water = terrain.water();
        Pos fire = terrain.fire();
        List<Move> moves =  Navigation.on(terrain).avoiding(FIRE).
                findPath(plane, water).orElseThrow(noPathTo(WATER)).
                moves().collect(toList());
        moves.addAll(Navigation.on(terrain).
                findPath(water, fire).orElseThrow(noPathTo(FIRE)).
                moves().collect(toList()));
        return moves;
    }

    private Supplier<IllegalArgumentException> noPathTo(int what) {
        return () -> new IllegalArgumentException("No path to " + what);
    }
}
