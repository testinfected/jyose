package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.molecule.Application;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.http.MimeTypes;

import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

public class Primes implements Application {

    private final Gson gson;

    public Primes(Gson gson) {
        this.gson = gson;
    }

    public void handle(Request request, Response response) throws Exception {
        List<Object> decompositions = request.parameters("number").stream().map(this::decompose).collect(toList());
        response.contentType(MimeTypes.JSON);
        if (decompositions.size() == 1) {
            response.body(gson.toJson(decompositions.get(0)));
        } else {
            response.body(gson.toJson(decompositions));
        }
    }

    private Object decompose(String input) {
        if (NotANumber.verify(input)) return new NotANumber(input);
        if (NumberTooBig.verify(input)) return new NumberTooBig(input);
        if (NotGreaterThanOne.verify(input)) return new NotGreaterThanOne(input);
        return new ValidNumber(input, PrimeFactors.of(parseInt(input)));
    }

    static class ValidNumber {
        private final int number;
        private final List<Integer> decomposition;

        public ValidNumber(String number, List<Integer> primes) {
            this.number = parseInt(number);
            this.decomposition = primes;
        }
    }

    static class NumberTooBig {
        private final int number;
        private final String error = "too big number (>1e6)";

        public NumberTooBig(String number) {
            this.number = parseInt(number);
        }

        public static boolean verify(String number) {
            return parseInt(number) > 1000000;
        }
    }

    static class NotANumber {
        private final String number;
        private final String error = "not a number";

        public NotANumber(String number) {
            this.number = number;
        }

        public static boolean verify(String candidate) {
            return !candidate.matches("-?\\d+");
        }
    }

    static class NotGreaterThanOne {
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
}
