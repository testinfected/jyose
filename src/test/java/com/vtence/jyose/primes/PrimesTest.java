package com.vtence.jyose.primes;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import org.junit.Test;

import static com.vtence.molecule.testing.ResponseAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PrimesTest {

    Request request = new Request();
    Response response = new Response();

    Primes primes = new Primes(new Gson());

    @Test
    public void storesAndRecallLastDecomposition() throws Exception {
        primes.list(request.addParameter("number", "42"), response);
        primes.last(request, response);
        assertThat(response).isDone().hasBodyText(containsString("[2,3,7]"));
    }

    @Test
    public void storesLastOfMultipleDecompositions() throws Exception {
        primes.list(request.addParameter("number", "42").addParameter("number", "alpha"), response);
        primes.last(request, response);
        assertThat(response).isDone().hasBodyText(containsString("not a number"));
    }

    @Test
    public void gracefullyHandleAbsenceOfNumber() throws Exception {
        primes.list(request, response);
        primes.last(request, response);
        assertThat(response).isDone().hasBodyText(equalTo("{}"));
    }

    @Test
    public void handlesVeryLargeNumbers() throws Exception {
        primes.list(request.addParameter("number", "6469693230"), response);
        assertThat(response).isDone().hasBodyText(containsString("too big number"));
    }
}