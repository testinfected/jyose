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
import static org.hamcrest.CoreMatchers.containsString;

public class PortfolioChallengeTest {

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
    passesContactInformationChallenge() throws IOException {
        response = request.get("/");
        response.assertOK();
        response.assertHasContent(
                containsString("<a id=\"contact-me-link\" href=\"http://vtence.com\""));
    }

    @Test public void
    passesPingSourceChallenge() throws IOException {
        response = request.get("/");
        response.assertOK();
        response.assertHasContent(
                containsString(
                        "<a id=\"ping-challenge-link\" " +
                        "href=\"https://github.com/testinfected/jyose/blob/master/src/main/java/com/vtence/jyose/JYose.java\""));
    }

    @After public void
    stopServer() throws Exception {
        server.shutdown();
    }
}
