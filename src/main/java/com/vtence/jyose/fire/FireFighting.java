package com.vtence.jyose.fire;

import com.google.gson.Gson;
import com.vtence.molecule.Application;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;

import java.util.stream.Stream;

import static com.vtence.jyose.fire.CommandCenter.FIRE;
import static com.vtence.molecule.http.MimeTypes.JSON;
import static java.lang.Integer.parseInt;

public class FireFighting implements Application {

    private final Gson gson;

    public FireFighting(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        int width = parseInt(request.parameter("width"));
        String[] map = Splitter.fixedLength(width).split(request.parameter("map"));
        Terrain terrain = Terrain.parse(map);
        response.contentType(JSON);
        response.body(gson.toJson(new Solution(map, fireSolutionFor(terrain))));
    }

    private Stream<Move> fireSolutionFor(Terrain terrain) {
        CommandCenter base = new CommandCenter();
        if (terrain.find(FIRE).isPresent()) {
            return base.planAttack(terrain);
        } else {
            return base.trainPilot(terrain);
        }
    }

    private static class Solution {
        private final String[] map;
        private final DxDy[] moves;

        public Solution(String[] map, Stream<Move> moves) {
            this.map = map;
            this.moves = moves.map(move -> {
                switch (move) {
                    case Right: return new DxDy(1, 0);
                    case Left: return new DxDy(-1, 0);
                    case Down: return new DxDy(0, 1);
                    case Up: return new DxDy(0, -1);
                    default: return new DxDy(0, 0);
                }
            }).toArray(DxDy[]::new);
        }

        public static class DxDy {
            public int dx;
            public int dy;

            public DxDy(int dx, int dy) {
                this.dx = dx;
                this.dy = dy;
            }
        }
    }
}
