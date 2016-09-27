package com.vtence.jyose.challenges;

import com.vtence.jyose.JYose;
import com.vtence.jyose.WebRoot;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.testing.http.HttpRequest;
import com.vtence.molecule.testing.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class PortfolioChallengesTest {

    static int PORT = 9999;

    JYose yose = new JYose(WebRoot.locate());
    WebServer server = WebServer.create(PORT);
    HttpRequest request = new HttpRequest(PORT);
    HttpResponse response;

    @Before public void
    startServer() throws Exception {
        yose.start(server);
    }

    @Test public void
    passesContactInformationChallenge() throws IOException {
        response = request.get("/");
        assertThat(response)
                .isOK()
                .hasBodyText(containsString("<a id=\"contact-me-link\" href=\"http://vtence.com\""));
    }

    @Test public void
    passesPingSourceChallenge() throws IOException {
        response = request.get("/");
        assertThat(response)
                .isOK()
                .hasBodyText(containsString("<a id=\"ping-challenge-link\" href=\"/ping\""));
    }

    @Test public void
    passesPrimesFactorsChallenge() throws IOException {
        response = request.get("/");
        assertThat(response)
                .isOK()
                .hasBodyText(containsString("<a id=\"prime-factors-decomposition-link\" href=\"/primeFactors/ui\""));
    }

    @Test public void
    passesMinesweeperChallenge() throws IOException {
        response = request.get("/");
        assertThat(response)
                .isOK()
                .hasBodyText(containsString("<a id=\"minesweeper-link\" href=\"/minesweeper\""));
    }

    @After public void
    stopServer() throws Exception {
        server.stop();
    }
}
