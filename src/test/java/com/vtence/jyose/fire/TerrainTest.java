package com.vtence.jyose.fire;

import org.junit.Test;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TerrainTest {

    @Test
    public void parsesEmptyMap() {
        Terrain empty = Terrain.parse("");
        assertThat("terrain (0,0)", empty.contains(Pos.at(0, 0)), equalTo(false));
    }

    @Test
    public void parsesSingleCellMap() {
        Terrain terrain = Terrain.parse(".");
        assertContainsCells(terrain, 1, 1);
    }

    @Test
    public void parsesSquareMaps() {
        Terrain terrain = Terrain.parse("...", "...", "...");
        assertContainsCells(terrain, 3, 3);
    }

    @Test
    public void parsesRectangularMaps() {
        Terrain terrain = Terrain.parse("...", "...");
        assertContainsCells(terrain, 2, 3);
    }

    @Test
    public void knowsPlaneStartingLocation() throws Exception {
        Terrain terrain = Terrain.parse("..", ".P", "..");
        assertThat("plane pos", terrain.plane(), equalTo(Pos.at(1, 1)));
    }

    @Test
    public void knowsFireLocation() throws Exception {
        Terrain terrain = Terrain.parse("..", "..", ".F");
        assertThat("fire pos", terrain.fire(), equalTo(Pos.at(2, 1)));
    }

    @Test
    public void knowsWaterLocation() throws Exception {
        Terrain terrain = Terrain.parse(".W.", "...");
        assertThat("water pos", terrain.water(), equalTo(Pos.at(0, 1)));
    }

    private void assertContainsCells(Terrain terrain, int height, int width) {
        range(0, height).forEach((row) -> range(0, width).forEach((col) -> assertWithinLimits(terrain, Pos.at(row, col))));
        assertOffLimits(terrain, Pos.at(height, width));
    }

    private void assertWithinLimits(Terrain terrain, Pos pos) {
        assertThat(format("(%s,%s) off limits", pos.row, pos.col), terrain.contains(pos));
    }

    private void assertOffLimits(Terrain terrain, Pos pos) {
        assertThat(format("(%s,%s) within limits", pos.row, pos.col), !terrain.contains(pos));
    }
}