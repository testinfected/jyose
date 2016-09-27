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

public class StartChallengesTest {

    static int PORT = 9999;

    JYose yose = new JYose(WebRoot.locate());
    WebServer server = WebServer.create("localhost", PORT);
    HttpRequest request = new HttpRequest(PORT);
    HttpResponse response;

    @Before public void
    startServer() throws Exception {
        yose.start(server);
    }

    @Test public void
    passesHelloChallenge() throws IOException {
        response = request.get("/");
        assertThat(response)
                .isOK()
                .hasBodyText(containsString("Hello Yose"));
    }

    @Test public void
    passesPingChallenge() throws IOException {
        response = request.get("/ping");
        assertThat(response)
                .isOK()
                .hasContentType("application/json")
                .hasBodyText("{\"alive\":true}");
    }

    @Test public void
    passesShareChallenge() throws IOException {
        response = request.get("/");
        assertThat(response)
                .isOK()
                .hasContentType("text/html")
                .hasBodyText(containsString(
                "<a id=\"repository-link\" href=\"https://github.com/testinfected/jyose\""
        ));
    }

    @After public void
    stopServer() throws Exception {
        server.stop();
    }
}
