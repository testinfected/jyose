package com.vtence.jyose.fire;

import org.junit.Test;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class TerrainTest {

    @Test
    public void parsesEmptyMap() {
        Terrain empty = Terrain.parse("");
        assertThat("terrain (0,0)", empty.contains(Vector.pos(0, 0)), equalTo(false));
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
    public void locatesGivenTargetsOnMap() throws Exception {
        Terrain terrain = Terrain.parse("..F", ".F.", "F..");
        assertThat("fires", terrain.findAll('F').collect(toList()), contains(Vector.pos(2, 0), Vector.pos(1, 1), Vector.pos(0, 2)));
    }

    @Test
    public void locatesAllTargetsOnSameRow() throws Exception {
        Terrain terrain = Terrain.parse("W..W");
        assertThat("fires", terrain.findAll('W').collect(toList()), contains(Vector.pos(0, 0), Vector.pos(3, 0)));
    }

    private void assertContainsCells(Terrain terrain, int height, int width) {
        range(0, height).forEach((row) -> range(0, width).forEach((col) -> assertWithinLimits(terrain, Vector.pos(col, row))));
        assertOffLimits(terrain, Vector.pos(width, height));
    }

    private void assertWithinLimits(Terrain terrain, Vector pos) {
        assertThat(format("%s off limits", pos), terrain.contains(pos));
    }

    private void assertOffLimits(Terrain terrain, Vector pos) {
        assertThat(format("%s within limits", pos), !terrain.contains(pos));
    }
}