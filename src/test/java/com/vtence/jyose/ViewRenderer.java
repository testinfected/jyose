package com.vtence.jyose;

import com.vtence.molecule.support.TemplateRenderer;
import com.vtence.molecule.templating.JMustacheRenderer;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import static com.vtence.jyose.HTMLDocument.toElement;

public class ViewRenderer {

    private final JMustacheRenderer renderer =
            new JMustacheRenderer().encoding("utf-8").extension("html").defaultValue("");
    private final TemplateRenderer template;

    private ViewRenderer(String template) {
        this.template = new TemplateRenderer(template);
    }

    public static ViewRenderer render(String template) {
        return new ViewRenderer(template);
    }

    public ViewRenderer from(File location) {
        renderer.fromDir(location);
        return this;
    }

    public ViewRenderer with(Object context) {
        this.template.with(context);
        return this;
    }

    public String asString() {
        try {
            return template.asString(renderer);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public Element asDom() throws IOException, SAXException {
        return toElement(asString());
    }
}