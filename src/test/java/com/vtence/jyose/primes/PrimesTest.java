package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.jyose.MockView;
import com.vtence.molecule.Session;
import com.vtence.molecule.support.MockRequest;
import com.vtence.molecule.support.MockResponse;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class PrimesTest {

    MockView<Primes.Decomposition> ui = new MockView<>();
    MockRequest first = new MockRequest();
    MockResponse dummy = new MockResponse();
    MockRequest last = new MockRequest();
    MockResponse response = new MockResponse();

    Session session;

    Primes primes = new Primes(new Gson(), ui);

    @Before
    public void bindSession() throws Exception {
        session = new Session();
        Session.set(first, session);
        Session.set(last, session);
    }

    @Test
    public void storesAndRecallLastDecomposition() throws Exception {
        primes.list(first.addParameter("number", "42"), dummy);
        primes.ui(last, response);
        ui.assertRenderedTo(response);
        ui.assertRenderedWith(resultOf("42 = 2 x 3 x 7"));
    }

    @Test
    public void storesLastOfMultipleDecompositions() throws Exception {
        primes.list(first.addParameter("number", "42").addParameter("number", "alpha"), dummy);
        primes.ui(last, response);
        ui.assertRenderedWith(resultOf("alpha is not a number"));
    }

    @Test
    public void gracefullyHandleAbsenceOfNumber() throws Exception {
        primes.list(first, dummy);
        primes.ui(last, response);
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