package com.vtence.jyose.primes;

import java.util.stream.Stream;

import static java.util.EnumSet.allOf;

public class Roman {

    public static int toArabic(String roman) {
        return Numeral.all().
                filter(n -> n.prefixes(roman)).
                map(n -> n.value() + toArabic(n.dropFrom(roman))).
                findFirst().orElse(0);
    }
    public static String fromArabic(int arabic) {
        return Numeral.all().
                filter(n -> n.value() <= arabic).
                map(n -> n.name() + fromArabic(arabic - n.value())).
                findFirst().orElse("");
    }

    public static enum Numeral {
        M(1000), CM(900), D(500), CD(400), C(100), XC(90), L(50), XL(40), X(10), IX(9), V(5), IV(4), I(1);

        private final int value;

        Numeral(int value) {
            this.value = value;
        }

        public static Stream<Numeral> all() {
            return allOf(Numeral.class).stream();
        }

        public int value() {
            return value;
        }

        public String dropFrom(String roman) {
            return roman.substring(name().length());
        }

        public boolean prefixes(String roman) {
            return roman.startsWith(name());
        }
    }
}
