package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.jyose.primes.Decomposition.*;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.http.MimeTypes;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

public class Primes {

    private final Gson gson;
    private Decomposition lastDecomposition = new None();

    public Primes(Gson gson) {
        this.gson = gson;
    }

    public void list(Request request, Response response) throws Exception {
        List<Decomposition> decompositions = decomposeNumbers(request);
        storeLastDecomposition(decompositions);
        respondWith(response, decompositions);
    }

    public void last(Request request, Response response) throws IOException {
        response.done(gson.toJson(lastDecomposition));
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
        response.done(toJson(decompositions));
    }

    private String toJson(List<Decomposition> decompositions) {
        return gson.toJson(resultOf(decompositions));
    }

    private Object resultOf(List<?> decompositions) {
        if (decompositions.isEmpty()) return new None();
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
}
