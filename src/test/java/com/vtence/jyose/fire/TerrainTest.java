package com.vtence.jyose.fire;

import org.junit.Test;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TerrainTest {

    @Test
    public void parsesEmptyMap() {
        Terrain empty = Terrain.parse("", 0);
        assertThat("terrain (0,0)", empty.contains(0, 0), equalTo(false));
    }

    @Test
    public void parsesSingleNodeMap() {
        Terrain terrain = Terrain.parse(".", 1);
        assertThat("terrain (0,0)", terrain.contains(0, 0), equalTo(true));
        assertThat("terrain (1,0)", terrain.contains(1, 0), equalTo(false));
        assertThat("terrain (0,1)", terrain.contains(0, 1), equalTo(false));
    }

    @Test
    public void parsesSquareMaps() {
        int size = 3;
        Terrain terrain = Terrain.parse(".........", size);
        assertContainsCells(terrain, size, size);
    }

    @Test
    public void parsesRectangularMap() {
        int height = 2;
        int width = 3;
        Terrain terrain = Terrain.parse("......", width);
        assertContainsCells(terrain, height, width);
    }

    @Test
    public void knowsPlaneStartingLocation() throws Exception {
        Terrain terrain = Terrain.parse("...P..", 2);
        assertThat("plane pos", terrain.plane(), equalTo(Pos.at(1, 1)));
    }

    @Test
    public void knowsFireLocation() throws Exception {
        Terrain terrain = Terrain.parse(".....F", 2);
        assertThat("fire pos", terrain.fire(), equalTo(Pos.at(2, 1)));
    }

    @Test
    public void knowsWaterLocation() throws Exception {
        Terrain terrain = Terrain.parse(".W....", 2);
        assertThat("water pos", terrain.water(), equalTo(Pos.at(0, 1)));
    }

    private void assertContainsCells(Terrain terrain, int height, int width) {
        range(0, height).forEach((row) -> range(0, width).forEach((col) -> assertWithinLimits(terrain, row, col)));
        assertOffLimits(terrain, height, width);
    }

    private void assertWithinLimits(Terrain terrain, int row, int col) {
        assertThat(format("(%s,%s) off limits", row, col), terrain.contains(row, col));
    }

    private void assertOffLimits(Terrain terrain, int row, int col) {
        assertThat(format("(%s,%s) within limits", row, col), !terrain.contains(row, col));
    }
}