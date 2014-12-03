package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.molecule.Session;
import com.vtence.molecule.support.MockRequest;
import com.vtence.molecule.support.MockResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class PrimesTest {

    Primes primes = new Primes(new Gson());
    MockRequest first = new MockRequest();
    MockResponse dummy = new MockResponse();
    MockRequest last = new MockRequest();
    MockResponse response = new MockResponse();
    Session session;

    @Before
    public void bindSession() throws Exception {
        session = new Session();
        Session.set(first, session);
        Session.set(last, session);
    }

    @Test
    public void storesAndRecallLastDecomposition() throws Exception {
        primes.list(first.addParameter("number", "42"), dummy);
        primes.last(last, response);
        response.assertBody(containsString("\"number\":42"));
    }

    @Test
    public void storesLastOfMultipleDecompositions() throws Exception {
        primes.list(first.addParameter("number", "42").addParameter("number", "alpha"), dummy);
        primes.last(last, response);
        response.assertBody(containsString("\"number\":\"alpha\""));
    }

    @Test
    public void gracefullyHandleAbsenceOfNumber() throws Exception {
        primes.list(first, dummy);
        primes.last(last, response);
        response.assertBody(equalTo("{}"));
    }
}