package com.vtence.jyose;

import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.http.MimeTypes;
import com.vtence.molecule.templating.Template;

import java.io.IOException;

public class StaticPage {

    private static final Void NO_CONTEXT = null;

    private final Template<Void> view;

    public StaticPage(Template<Void> view) {
        this.view = view;
    }

    public void render(Request request, Response response) throws IOException {
        response.contentType(MimeTypes.HTML);
        response.done(view.render(NO_CONTEXT));
    }
}
