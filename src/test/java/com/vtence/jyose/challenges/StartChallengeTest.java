package com.vtence.jyose.challenges;

import com.vtence.jyose.JYose;
import com.vtence.jyose.WebRoot;
import com.vtence.molecule.http.MimeTypes;
import com.vtence.molecule.servers.SimpleServer;
import com.vtence.molecule.support.HttpRequest;
import com.vtence.molecule.support.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;

public class StartChallengeTest {

    static int PORT = 9999;

    JYose yose = new JYose(WebRoot.locate());
    SimpleServer server = new SimpleServer("localhost", PORT);
    HttpRequest request = new HttpRequest(server.port());
    HttpResponse response;

    @Before public void
    startServer() throws Exception {
        yose.start(server);
    }

    @Test public void
    passesHelloChallenge() throws IOException {
        response = request.get("/");
        response.assertOK();
        response.assertHasContent(containsString("Hello Yose"));
    }

    @Test public void
    passesPingChallenge() throws IOException {
        response = request.get("/ping");
        response.assertOK();
        response.assertHasContentType("application/json");
        response.assertHasContent("{\"alive\":true}");
    }

    @Test public void
    passesShareChallenge() throws IOException {
        response = request.get("/");
        response.assertOK();
        response.assertHasContentType(containsString(MimeTypes.HTML));
        response.assertHasContent(containsString(
                "<a id=\"repository-link\" href=\"https://github.com/testinfected/jyose\""
        ));
    }

    @After public void
    stopServer() throws Exception {
        server.shutdown();
    }
}
