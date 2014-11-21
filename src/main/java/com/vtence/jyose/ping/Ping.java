package com.vtence.jyose.ping;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.http.MimeTypes;

import java.io.IOException;

public class Ping {

    private final Gson gson;

    public Ping(Gson gson) {
        this.gson = gson;
    }

    public void pong(Request request, Response response) throws IOException {
        response.contentType(MimeTypes.JSON);
        response.body(gson.toJson(new Pong()));
    }

    public static class Pong {
        public final boolean alive = true;
    }
}
