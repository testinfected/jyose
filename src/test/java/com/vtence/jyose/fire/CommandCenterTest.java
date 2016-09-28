package com.vtence.jyose.fire;

import org.junit.Test;

import java.util.List;

import static com.vtence.jyose.fire.Moves.*;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public class CommandCenterTest {

    @Test
    public void directsPilotToFillPlaneWithWaterThenAttackFire() throws Exception {
        Terrain terrain = Terrain.parse(
                "P...",
                ".F..",
                "....",
                "..W.");
        CommandCenter base = new CommandCenter();
        List<Vector> moves = base.planAttack(terrain).collect(toList());
        assertThat("moves", moves, contains(RIGHT, RIGHT, DOWN, DOWN, DOWN, LEFT, UP, UP));
    }

    @Test
    public void trainsPilotToGrabNearestWater() throws Exception {
        Terrain terrain = Terrain.parse(
                "W....",
                ".....",
                "P...W");
        CommandCenter base = new CommandCenter();
        List<Vector> moves = base.trainPilot(terrain).collect(toList());
        assertThat("moves", moves, contains(UP, UP));
    }

    @Test
    public void calculatesShortestPathToAttack() throws Exception {
        Terrain terrain = Terrain.parse(
                "...........",
                ".W...P..W..",
                "...F.......");
        CommandCenter base = new CommandCenter();
        List<Vector> moves = base.planAttack(terrain).collect(toList());
        assertThat("moves", moves, hasSize(7));
    }
}
