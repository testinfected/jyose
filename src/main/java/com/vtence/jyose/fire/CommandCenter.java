package com.vtence.jyose.fire;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class CommandCenter {

    private static final int PLANE = 'P';
    private static final int FIRE = 'F';
    private static final int WATER = 'W';

    private final Supplier<? extends RuntimeException> invalidMap = () -> new IllegalArgumentException("Invalid map");

    public List<Move> planAttack(Terrain terrain) {
        Pos plane = terrain.find(PLANE).orElseThrow(invalidMap);
        Pos water = terrain.find(WATER).orElseThrow(invalidMap);
        Pos fire = terrain.find(FIRE).orElseThrow(invalidMap);
        List<Move> moves =  Navigation.on(terrain).avoiding(FIRE).
                findPath(plane, water).orElseThrow(invalidMap).
                moves().collect(toList());
        moves.addAll(Navigation.on(terrain).
                findPath(water, fire).orElseThrow(invalidMap).
                moves().collect(toList()));
        return moves;
    }
}
