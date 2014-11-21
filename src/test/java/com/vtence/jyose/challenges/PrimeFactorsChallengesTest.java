package com.vtence.jyose.challenges;

import com.vtence.jyose.JYose;
import com.vtence.jyose.WebRoot;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.support.HttpRequest;
import com.vtence.molecule.support.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;

public class PrimeFactorsChallengesTest {

    static final int PORT = 9999;
    JYose yose = new JYose(WebRoot.locate());
    WebServer server = WebServer.create(PORT);
    HttpRequest request = new HttpRequest(PORT);
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
        response.assertHasContent("{\"number\":1000001,\"error\":\"too big number (\\u003e1e6)\"}");
    }

    @Test public void
    passesMultipleEntriesChallenge() throws IOException {
        response = request.withParameters("number", "300", "120", "hello").get("/primeFactors");
        response.assertOK();
        response.assertHasContent(
                "[{\"number\":300,\"decomposition\":[2,2,3,5,5]}," +
                "{\"number\":120,\"decomposition\":[2,2,2,3,5]}," +
                "{\"number\":\"hello\",\"error\":\"not a number\"}]");
    }

    @Test public void
    passesFormChallenge() throws IOException {
        response = request.get("/primeFactors/ui");
        response.assertOK();
        response.assertHasContent(containsString("id=\"title\""));
        response.assertHasContent(containsString("id=\"invitation\""));
        response.assertHasContent(containsString("<input id=\"number\""));
        response.assertHasContent(containsString("<button id=\"go\""));
    }

    @After public void
    stopServer() throws Exception {
        server.stop();
    }
}
