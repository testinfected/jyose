package com.vtence.jyose.challenges;

import com.vtence.jyose.JYose;
import com.vtence.jyose.WebRoot;
import com.vtence.molecule.simple.SimpleServer;
import com.vtence.molecule.support.HttpRequest;
import com.vtence.molecule.support.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.vtence.molecule.support.HttpRequest.aRequest;

public class PrimeFactorsChallengeTest {

    static int PORT = 9999;

    JYose yose = new JYose(WebRoot.locate());
    SimpleServer server = new SimpleServer(PORT);
    HttpRequest request = aRequest().onPort(PORT);
    HttpResponse response;

    @Before public void
    startServer() throws Exception {
        yose.start(server);
    }

    @Test public void
    passesFactorsOfTwoChallenge() throws IOException {
        response = request.withParameter("number", "16").get("/primeFactors");
        response.assertOK();
        response.assertHasContentType("application/json");
        response.assertHasContent("{\"number\":16,\"decomposition\":[2,2,2,2]}");
    }

    @Test public void
    passesStringGuardChallenge() throws IOException {
        response = request.withParameter("number", "hello").get("/primeFactors");
        response.assertOK();
        response.assertHasContentType("application/json");
        response.assertHasContent("{\"number\":\"hello\",\"error\":\"not a number\"}");
    }

    @Test public void
    passesBigNumberGuardChallenge() throws IOException {
        response = request.withParameter("number", "1000001").get("/primeFactors");
        response.assertOK();
        response.assertHasContentType("application/json");
        response.assertHasContent("{\"number\":\"hello\",\"error\":\"too big number (>1e6)\"}");
    }

    @After public void
    stopServer() throws Exception {
        server.shutdown();
    }
}
