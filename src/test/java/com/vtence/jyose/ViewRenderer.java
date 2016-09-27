package com.vtence.jyose;

import com.vtence.molecule.templating.JMustacheRenderer;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ViewRenderer {

    private final JMustacheRenderer renderer =
            new JMustacheRenderer().encoding(UTF_8).extension("html").defaultValue("");

    private final String template;
    private Object context = new Object();

    private ViewRenderer(String template) {
        this.template = template;
    }

    public static ViewRenderer render(String template) {
        return new ViewRenderer(template);
    }

    public ViewRenderer with(Object context){
        this.context = context;
        return this;
    }

    public ViewRenderer from(File location) {
        renderer.fromDir(location);
        return this;
    }

    public String asString() {
        try {
            StringWriter buffer = new StringWriter();
            renderer.render(buffer, template, context);
            return buffer.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public Element asDom() {
        try {
            return HTMLDocument.toElement(asString());
        } catch (IOException | SAXException e) {
            throw new AssertionError(e);
        }
    }
}