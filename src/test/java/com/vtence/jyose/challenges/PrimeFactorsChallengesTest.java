package com.vtence.jyose.challenges;

import com.vtence.jyose.JYoseDriver;
import com.vtence.jyose.WebRoot;
import com.vtence.jyose.pages.PrimeFactorsPage;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.testing.http.Form;
import com.vtence.molecule.testing.http.HttpRequest;
import com.vtence.molecule.testing.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class PrimeFactorsChallengesTest {

    int PORT = 9999;
    WebServer server = WebServer.create(PORT);

    HttpRequest request = new HttpRequest(PORT);
    HttpResponse response;

    JYoseDriver yose = new JYoseDriver(PORT, WebRoot.locate());

    @Before
    public void
    startServer() throws Exception {
        yose.start();
    }

    @After
    public void
    stopServer() throws Exception {
        yose.stop();
    }

    @Test
    public void
    passesFactorsOfTwoChallenge() throws IOException {
        response = request.content(Form.urlEncoded().addField("number", "16"))
                          .get("/primeFactors");
        assertThat(response)
                .isOK()
                .hasContentType("application/json")
                .hasBodyText("{\"number\":16,\"decomposition\":[2,2,2,2]}");
    }

    @Test
    public void
    passesStringGuardChallenge() throws IOException {
        response = request.content(Form.urlEncoded().addField("number", "hello"))
                          .get("/primeFactors");
        assertThat(response)
                .isOK()
                .hasContentType("application/json")
                .hasBodyText("{\"number\":\"hello\",\"error\":\"not a number\"}");
    }

    @Test
    public void
    passesBigNumberGuardChallenge() throws IOException {
        response = request.content(Form.urlEncoded().addField("number", "1000001"))
                          .get("/primeFactors");
        assertThat(response)
                .isOK()
                .hasContentType("application/json")
                .hasBodyText("{\"number\":1000001,\"error\":\"too big number (\\u003e1e6)\"}");
    }

    @Test
    public void
    passesMultipleEntriesChallenge() throws IOException {
        response = request.content(Form.urlEncoded().addField("number", "300")
                                                    .addField("number", "120")
                                                    .addField("number", "hello")).get("/primeFactors");
        assertThat(response).isOK()
                            .hasBodyText( "[" +
                                          "{\"number\":300,\"decomposition\":[2,2,3,5,5]}," +
                                          "{\"number\":120,\"decomposition\":[2,2,2,3,5]}," +
                                          "{\"number\":\"hello\",\"error\":\"not a number\"}" +
                                          "]");
    }

    @Test
    public void
    passesFormChallenge() throws IOException {
        response = request.get("/primeFactors/ui");
        assertThat(response)
                .isOK()
                .hasBodyText(containsString("id=\"title\""))
                .hasBodyText(containsString("id=\"invitation\""))
                .hasBodyText(containsString("<input id=\"number\""))
                .hasBodyText(containsString("<button id=\"go\""));
    }

    @Test
    public void
    passesInputChallenge() throws IOException {
        PrimeFactorsPage page = yose.primeFactors();
        page.decompose("66");
        page.showsSingleResult("66 = 2 x 3 x 11");
    }

    @Test
    public void
    passesResistBigNumberChallenge() throws IOException {
        PrimeFactorsPage page = yose.primeFactors();
        page.decompose("123456789");
        page.showsSingleResult("too big number (>1e6)");
    }

    @Test
    public void
    passesResistStringsChallenge() throws IOException {
        PrimeFactorsPage page = yose.primeFactors();
        page.decompose("3hello");
        page.showsSingleResult("3hello is not a number");
    }

    @Test
    public void
    passesResistNegativeNumberChallenge() throws IOException {
        PrimeFactorsPage page = yose.primeFactors();
        page.decompose("-42");
        page.showsSingleResult("-42 is not an integer > 1");
    }

    @Test
    public void
    passesListOfDecompositionChallenge() throws IOException {
        PrimeFactorsPage page = yose.primeFactors();
        page.decompose("15, -42, hello, 123456789");
        page.showsResults(
                "15 = 3 x 5",
                "-42 is not an integer > 1",
                "hello is not a number",
                "too big number (>1e6)");
    }

    @Test
    public void
    passesLastDecompositionChallenge() throws IOException {
        PrimeFactorsPage page = yose.primeFactors();
        page.showsLastDecomposition("");
        page.decompose("15");
        refresh(page).showsLastDecomposition("15 = 3 x 5");
        page.decompose("42");
        refresh(page).showsLastDecomposition("42 = 2 x 3 x 7");
    }

    private PrimeFactorsPage refresh(PrimeFactorsPage page) {
        return yose.primeFactors();
    }

    @Test
    public void
    passesRomanNumeralsChallenge() throws IOException {
        PrimeFactorsPage page = yose.primeFactors();
        page.decompose("XLII");
        page.showsSingleResult("XLII = II x III x VII");
    }
}
