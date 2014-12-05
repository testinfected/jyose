package com.vtence.jyose.primes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

import static com.vtence.jyose.primes.Roman.fromArabic;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class ArabicToRomanConversionTest {

    private final String roman;
    private final int arabic;

    public ArabicToRomanConversionTest(int arabic, String roman) {
        this.roman = roman;
        this.arabic = arabic;
    }

    @Parameters(name = "{0} -> {1}")
    public static Collection<Object[]> data() {
        return asList(new Object[][] {
                {0, ""},
                {1, "I"},
                {2, "II"},
                {3, "III"},
                {4, "IV"},
                {5, "V"},
                {8, "VIII"},
                {13, "XIII"},
                {40, "XL"},
                {50, "L"},
                {90, "XC"},
                {100, "C"},
                {400, "CD"},
                {500, "D"},
                {900, "CM"},
                {1000, "M"},
                {3887, "MMMDCCCLXXXVII"}
        });
    }

    @Test
    public void
    romanOf() {
        assertThat("roman of " + arabic, fromArabic(arabic), equalTo(roman));
    }
}
