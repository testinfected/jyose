package com.vtence.jyose.primes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

import static com.vtence.jyose.primes.Roman.toArabic;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class RomanToArabicTest {

    private final String roman;
    private final int arabic;

    public RomanToArabicTest(String roman, int arabic) {
        this.roman = roman;
        this.arabic = arabic;
    }

    @Parameters(name = "{0} -> {1}")
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
                {"I", 1},
                {"III", 3},
                {"IV", 4},
                {"V", 5},
                {"VIII", 8},
                {"IX", 9},
                {"X", 10},
                {"XIX", 19},
                {"XL", 40},
                {"L", 50},
                {"XC", 90},
                {"C", 100},
                {"CD", 400},
                {"D", 500},
                {"CM", 900},
                {"M", 1000},
                {"MMMDCCCLXXXVII", 3887}
        });
    }

    @Test
    public void
    arabicOf() {
        assertThat("arabic of " + roman, toArabic(roman), equalTo(arabic));
    }
}
