package com.vtence.jyose;

import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.http.MimeTypes;
import com.vtence.molecule.templating.Template;

import java.io.IOException;

public class StaticPage {

    private static final Object NO_CONTEXT = null;

    private final Template view;

    public StaticPage(Template view) {
        this.view = view;
    }

    public void render(Request request, Response response) throws IOException {
        response.contentType(MimeTypes.HTML);
        response.body(view.render(NO_CONTEXT));
    }
}
