package com.vtence.jyose.challenges;

import com.vtence.jyose.JYose;
import com.vtence.jyose.WebRoot;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.testing.http.HttpRequest;
import com.vtence.molecule.testing.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;

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
        response = request.get("/fire/geek?width=3&map=" +
                "..." +
                "P.." +
                ".WF");
        assertThat(response)
                .isOK()
                .hasContentType("application/json")
                .hasBodyText(
                "{" +
                    "\"map\":[" +
                        "\"...\"," +
                        "\"P..\"," +
                        "\".WF\"" +
                    "]," +
                    "\"moves\":[" +
                        "{\"dx\":1,\"dy\":0}," +
                        "{\"dx\":0,\"dy\":1}," +
                        "{\"dx\":1,\"dy\":0}" +
                    "]" +
                "}");
    }

    @Test
    public void passesSecondFireChallenge() throws Exception {
        response = request.get("/fire/geek?width=5&map=" +
                "W..P." +
                "....." +
                "W....");
        assertThat(response)
                .isOK()
                .hasContentType("application/json")
                .hasBodyText(
                "{" +
                    "\"map\":[" +
                        "\"W..P.\"," +
                        "\".....\"," +
                        "\"W....\"" +
                    "]," +
                    "\"moves\":[" +
                        "{\"dx\":-1,\"dy\":0}," +
                        "{\"dx\":-1,\"dy\":0}," +
                        "{\"dx\":-1,\"dy\":0}" +
                    "]" +
                "}");
    }

    @Test
    public void passesThirdFireChallenge() throws Exception {
        response = request.get("/fire/geek?width=5&map=" +
                "W...P" +
                "F...." +
                "....W");
        assertThat(response)
                .isOK()
                .hasContentType("application/json")
                .hasBodyText(
                "{" +
                    "\"map\":[" +
                        "\"W...P\"," +
                        "\"F....\"," +
                        "\"....W\"" +
                    "]," +
                    "\"moves\":[" +
                        "{\"dx\":-1,\"dy\":0}," +
                        "{\"dx\":-1,\"dy\":0}," +
                        "{\"dx\":-1,\"dy\":0}," +
                        "{\"dx\":-1,\"dy\":0}," +
                        "{\"dx\":0,\"dy\":1}" +
                    "]" +
                "}");
    }
}
