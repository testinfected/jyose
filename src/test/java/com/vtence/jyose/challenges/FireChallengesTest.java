package com.vtence.jyose.challenges;

import com.vtence.jyose.JYose;
import com.vtence.jyose.WebRoot;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.support.HttpRequest;
import com.vtence.molecule.support.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FireChallengesTest {

    static int PORT = 9999;

    JYose yose = new JYose(WebRoot.locate());
    WebServer server = WebServer.create(PORT);
    HttpRequest request = new HttpRequest(PORT);
    HttpResponse response;

    @Before
    public void
    startServer() throws Exception {
        yose.start(server);
    }

    @After
    public void
    stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void passesFirstFireChallenge() throws Exception {
        response = request.get("/fire/geek?width=3&map=...P...WF");
        response.assertOK();
        response.assertHasContentType("application/json");
        response.assertHasContent(
                "{" +
                    "map:[" +
                        "\"...\"," +
                        "\"P..\"," +
                        "\".WF\"" +
                    "]," +
                    "moves:[" +
                        "{dx:0,dy:1}," +
                        "{dx:1,dy:0}," +
                        "{dx:1,dy:0}," +
                    "]" +
                "}");
    }
}
