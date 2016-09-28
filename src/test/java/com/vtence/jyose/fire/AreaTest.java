package com.vtence.jyose.fire;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class AreaTest {

    @Test
    public void visitsLocationsInOrderOfAddition() throws Exception {
        Area area = new Area();
        area.expand(Path.startingAt(Vector.pos(0, 1)));
        area.expand(Path.startingAt(Vector.pos(1, 3)));
        area.expand(Path.startingAt(Vector.pos(2, 1)));

        assertThat("(1, 0)", area.visitNext(), hasPosition(1, 0));
        assertThat("(3, 1)", area.visitNext(), hasPosition(3, 1));
        assertThat("(1, 2)", area.visitNext(), hasPosition(1, 2));
        assertThat("locations not consumed" , area.done());
    }

    @Test
    public void ignoresLocationsAlreadyVisited() throws Exception {
        Area area = new Area();
        Path location = Path.startingAt(Vector.pos(5, 4));
        area.expand(location);
        area.visitNext();
        assertTrue(area.done());
        area.expand(location);
        assertThat("location revisited", area.done());
    }

    private Matcher<? super Path> hasPosition(int row, int col) {
        return new FeatureMatcher<Path, Vector>(equalTo(Vector.pos(col, row)), "a location", "location") {
            @Override
            protected Vector featureValueOf(Path actual) {
                return actual.end();
            }
        };
    }
}