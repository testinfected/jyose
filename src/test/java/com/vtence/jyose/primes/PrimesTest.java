package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.jyose.MockView;
import com.vtence.molecule.Session;
import com.vtence.molecule.support.MockRequest;
import com.vtence.molecule.support.MockResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class PrimesTest {

    MockView<String> ui = new MockView<>();
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
        ui.assertRenderedWith(containsString("\"number\":42"));
    }

    @Test
    public void storesLastOfMultipleDecompositions() throws Exception {
        primes.list(first.addParameter("number", "42").addParameter("number", "alpha"), dummy);
        primes.ui(last, response);
        ui.assertRenderedWith(containsString("\"number\":\"alpha\""));
    }

    @Test
    public void gracefullyHandleAbsenceOfNumber() throws Exception {
        primes.list(first, dummy);
        primes.ui(last, response);
        ui.assertRenderedWith(equalTo("{}"));
    }
}