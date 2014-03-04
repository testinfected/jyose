package com.vtence.jyose.challenges.start;

import com.vtence.jyose.JYose;
import com.vtence.molecule.simple.SimpleServer;
import com.vtence.molecule.support.HttpRequest;
import com.vtence.molecule.support.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.vtence.molecule.support.HttpRequest.aRequest;

public class HelloYoseTest {

    static int PORT = 9999;

    JYose yose = new JYose();
    SimpleServer server = new SimpleServer(PORT);
    HttpRequest request = aRequest().onPort(PORT);
    HttpResponse response;

    @Before public void
    startServer() throws Exception {
        yose.start(server);
    }

    @Test
    public void passesChallenge() throws IOException {
        response = request.get("/");
        response.assertOK();
        response.assertHasContent("Hello Yose");
    }

    @After public void
    stopServer() throws Exception {
        server.shutdown();
    }
}