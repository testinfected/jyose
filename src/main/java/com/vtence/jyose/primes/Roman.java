package com.vtence.jyose.primes;

import static java.util.EnumSet.allOf;

public class Roman {

    public static int toArabic(String roman) {
        for (Numeral numeral : allOf(Numeral.class)) {
            if (numeral.prefixes(roman)) return numeral.value() + toArabic(numeral.stripFrom(roman));
        }
        return 0;
    }

    public static enum Numeral {
        M(1000), CM(900), D(500), CD(400), C(100), XC(90), L(50), XL(40), X(10), IX(9), V(5), IV(4), I(1);

        private final int value;

        Numeral(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }

        public String stripFrom(String roman) {
            return roman.substring(name().length());
        }

        public boolean prefixes(String roman) {
            return roman.startsWith(name());
        }
    }
}
