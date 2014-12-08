package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.helpers.Joiner;
import com.vtence.molecule.http.MimeTypes;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

public class Primes {

    private final Gson gson;
    private Decomposition lastDecomposition = new NoDecomposition();

    public Primes(Gson gson) {
        this.gson = gson;
    }

    public void list(Request request, Response response) throws Exception {
        List<Decomposition> decompositions = decomposeNumbers(request);
        storeLastDecomposition(decompositions);
        respondWith(response, decompositions);
    }

    public void last(Request request, Response response) throws IOException {
        response.body(gson.toJson(lastDecomposition));
    }

    private List<Decomposition> decomposeNumbers(Request request) {
        return request.parameters("number").stream().map(this::decompose).collect(toList());
    }

    private void storeLastDecomposition(List<Decomposition> decompositions) {
        if (decompositions.isEmpty()) return;
        lastDecomposition = lastOf(decompositions);
    }

    private Decomposition lastOf(List<Decomposition> decompositions) {
        return decompositions.get(decompositions.size() - 1);
    }

    private void respondWith(Response response, List<Decomposition> decompositions) throws IOException {
        response.contentType(MimeTypes.JSON);
        response.body(toJson(decompositions));
    }

    private String toJson(List<?> decompositions) {
        return gson.toJson(resultOf(decompositions));
    }

    private Object resultOf(List<?> decompositions) {
        if (decompositions.isEmpty()) return new NoDecomposition();
        return decompositions.size() > 1 ? decompositions : decompositions.get(0);
    }

    private Decomposition decompose(String input) {
        if (RomanNumber.verify(input)) return new RomanNumber(input, romanFactorsOf(input));
        if (NotANumber.verify(input)) return new NotANumber(input);
        if (NumberTooBig.verify(input)) return new NumberTooBig(input);
        if (NotGreaterThanOne.verify(input)) return new NotGreaterThanOne(input);
        return new ValidNumber(input, primeFactorsOf(input));
    }

    private List<String> romanFactorsOf(String input) {
        return PrimeFactors.of(Roman.toArabic(input)).stream().map(Roman::fromArabic).collect(toList());
    }

    private List<Integer> primeFactorsOf(String input) {
        return PrimeFactors.of(parseInt(input));
    }

    public static interface Decomposition {
        String result();
    }

    public static class NoDecomposition implements Decomposition {
        public String result() {
            return "";
        }
    }

    public static class ValidNumber implements Decomposition {
        private final int number;
        private final List<Integer> decomposition;

        public ValidNumber(String number, List<Integer> primes) {
            this.number = parseInt(number);
            this.decomposition = primes;
        }

        public String result() {
            return number + " = " + Joiner.on(" x ").join(decomposition);
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

        public String result() {
            return error;
        }
    }

    public static class NotANumber implements Decomposition {
        private final String number;
        private final String error = "not a number";

        public NotANumber(String number) {
            this.number = number;
        }

        public String result() {
            return number + " is " + error;
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

        public String result() {
            return error;
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

        public String result() {
            return number + " = " + Joiner.on(" x ").join(decomposition);
        }

        public static boolean verify(String number) {
            return number.matches("[MDCLXVI]+");
        }
    }
}
