package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.molecule.support.MockRequest;
import com.vtence.molecule.support.MockResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class PrimesTest {

    MockRequest request = new MockRequest();
    MockResponse response = new MockResponse();

    Primes primes = new Primes(new Gson());

    @Test
    public void storesAndRecallLastDecomposition() throws Exception {
        primes.list(request.addParameter("number", "42"), response);
        primes.last(request, response);
        response.assertBody(containsString("[2,3,7]"));
    }

    @Test
    public void storesLastOfMultipleDecompositions() throws Exception {
        primes.list(request.addParameter("number", "42").addParameter("number", "alpha"), response);
        primes.last(request, response);
        response.assertBody(containsString("not a number"));
    }

    @Test
    public void gracefullyHandleAbsenceOfNumber() throws Exception {
        primes.list(request, response);
        primes.last(request, response);
        response.assertBody(equalTo("{}"));
    }

    @Test
    public void handlesVeryLargeNumbers() throws Exception {
        primes.list(request.addParameter("number", "6469693230"), response);
        response.assertBody(containsString("too big number"));
    }
}