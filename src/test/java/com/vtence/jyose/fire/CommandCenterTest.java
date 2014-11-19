package com.vtence.jyose.fire;

import org.junit.Test;

import java.util.List;

import static com.vtence.jyose.fire.Move.Down;
import static com.vtence.jyose.fire.Move.Left;
import static com.vtence.jyose.fire.Move.Right;
import static com.vtence.jyose.fire.Move.Up;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class CommandCenterTest {

    @Test
    public void directsPilotToFillPlaneWithWaterThenAttackFire() throws Exception {
        Terrain terrain = Terrain.parse(
                "P...",
                ".F..",
                "....",
                "..W.");
        CommandCenter base = new CommandCenter();
        List<Move> moves = base.planAttack(terrain).collect(toList());
        assertThat("moves", moves, contains(Right, Right, Down, Down, Down, Left, Up, Up));
    }

    @Test
    public void trainsPilotToGrabNearestWater() throws Exception {
        Terrain terrain = Terrain.parse(
                "W....",
                ".....",
                "P...W");
        CommandCenter base = new CommandCenter();
        List<Move> moves = base.trainPilot(terrain).collect(toList());
        assertThat("moves", moves, contains(Up, Up));
    }
}
