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
        if (!isInteger(input)) return new NotANumber(input);
        int number = parseInt(input);
        if (isTooBig(number)) return new NumberTooBig(number);
        return new Decomposition(number, PrimeFactors.of(number));
    }

    private boolean isInteger(String candidate) {
        return candidate.matches("\\d+");
    }

    private boolean isTooBig(int number) {
        return number > 1000000;
    }

    class Decomposition {
        private final int number;
        private final List<Integer> decomposition;

        public Decomposition(int number, List<Integer> primes) {
            this.number = number;
            this.decomposition = primes;
        }
    }

    class NumberTooBig {
        private final int number;
        private final String error = "too big number (>1e6)";

        public NumberTooBig(int number) {
            this.number = number;
        }
    }

    class NotANumber {
        private final String number;
        private final String error = "not a number";

        public NotANumber(String number) {
            this.number = number;
        }
    }
}
