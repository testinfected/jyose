package com.vtence.jyose.challenges;

import com.vtence.jyose.JYose;
import com.vtence.jyose.WebRoot;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.support.HttpRequest;
import com.vtence.molecule.support.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;

public class AstroportChallengesTest {

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
    public void passesAstroportNameChallenge() throws Exception {
        response = request.get("/astroport");
        response.assertOK();
        response.assertHasContentType("text/html");
        response.assertHasContent(containsString("<h3 id=\"astroport-name\">Molecule</h3>"));
    }
}
