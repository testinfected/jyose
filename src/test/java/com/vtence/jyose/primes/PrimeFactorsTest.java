package com.vtence.jyose.primes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PrimeFactorsTest {

    @Parameters(name = "{0}={1}")
    public static Collection<Object[]> data() {
        return asList(new Object[][] {
                { 1, asList() },
                { 2, asList(2) },
                { 3, asList(3) },
                { 4, asList(2, 2) },
                { 5, asList(5) },
                { 6, asList(2, 3) },
                { 8, asList(2, 2, 2) },
                { 9, asList(3, 3) },
                { 25, asList(5, 5) },
                { 144, asList(2, 2, 2, 2, 3, 3) },
                { 360, asList(2, 2, 2, 3, 3, 5)},
                { 587, asList(587) }
        });
    }

    private final int number;
    private final List<Integer> primes;

    public PrimeFactorsTest(final int number, final List<Integer> primes) {
        this.number = number;
        this.primes = primes;
    }

    @Test public void
    primeFactorsOf() {
        assertThat("prime factors of " + number, PrimeFactors.of(number), equalTo(primes));
    }
}
