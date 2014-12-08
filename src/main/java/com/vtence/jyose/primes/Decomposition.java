package com.vtence.jyose.primes;

import java.util.List;

import static java.lang.Integer.parseInt;

public interface Decomposition {

    public static class None implements Decomposition {}

    public static class ValidNumber implements Decomposition {
        private final int number;
        private final List<Integer> decomposition;

        public ValidNumber(String number, List<Integer> primes) {
            this.number = parseInt(number);
            this.decomposition = primes;
        }
    }

    public static class NumberTooBig implements Decomposition {
        private final int number;
        private final String error = "too big number (>1e6)";

        public NumberTooBig(String number) {
            this.number = parseInt(number);
        }

        public static boolean verify(String number) {
            return parseInt(number) > 1000000;
        }
    }

    public static class NotANumber implements Decomposition {
        private final String number;
        private final String error = "not a number";

        public NotANumber(String number) {
            this.number = number;
        }

        public static boolean verify(String candidate) {
            return !candidate.matches("-?\\d+");
        }
    }

    public static class NotGreaterThanOne implements Decomposition {
        private final int number;
        private final String error;

        public NotGreaterThanOne(String number) {
            this.number = parseInt(number);
            this.error = number + " is not an integer > 1";
        }

        public static boolean verify(String number) {
            return parseInt(number) <= 1;
        }
    }

    public static class RomanNumber implements Decomposition {
        private final String number;
        private final List<String> decomposition;

        public RomanNumber(String roman, List<String> factors) {
            this.number = roman;
            this.decomposition = factors;
        }

        public static boolean verify(String number) {
            return number.matches("[MDCLXVI]+");
        }
    }
}
