package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.jyose.MockView;
import com.vtence.molecule.support.MockRequest;
import com.vtence.molecule.support.MockResponse;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class PrimesTest {

    MockView<Primes.Decomposition> ui = new MockView<>();
    MockRequest request = new MockRequest();
    MockResponse response = new MockResponse();

    Primes primes = new Primes(new Gson(), ui);

    @Test
    public void storesAndRecallLastDecomposition() throws Exception {
        primes.list(request.addParameter("number", "42"), response);
        primes.ui(request, response);
        ui.assertRenderedTo(response);
        ui.assertRenderedWith(resultOf("42 = 2 x 3 x 7"));
    }

    @Test
    public void storesLastOfMultipleDecompositions() throws Exception {
        primes.list(request.addParameter("number", "42").addParameter("number", "alpha"), response);
        primes.ui(request, response);
        ui.assertRenderedWith(resultOf("alpha is not a number"));
    }

    @Test
    public void gracefullyHandleAbsenceOfNumber() throws Exception {
        primes.list(request, response);
        primes.ui(request, response);
        ui.assertRenderedWith(resultOf(""));
    }

    private Matcher<? super Primes.Decomposition> resultOf(String expected) {
        return new FeatureMatcher<Primes.Decomposition, String>(equalTo(expected), "result of", "result") {
            protected String featureValueOf(Primes.Decomposition actual) {
                return actual.result();
            }
        };
    }
}