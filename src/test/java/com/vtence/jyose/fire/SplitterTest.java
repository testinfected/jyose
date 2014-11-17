package com.vtence.jyose.fire;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayWithSize;

public class SplitterTest {

    @Test
    public void ignoresEmptyString() throws Exception {
        assertThat("empty", Splitter.fixedLength(3).split(""), arrayWithSize(0));
    }

    @Test
    public void splitsAtFixedIntervals() throws Exception {
        assertThat("empty", Splitter.fixedLength(3).split("123456789012"), arrayContaining("123", "456", "789", "012"));
    }
}
