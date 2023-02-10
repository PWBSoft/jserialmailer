package com.pwbsoft.jserialmailer.service;

import com.github.jknack.handlebars.Handlebars;
import lombok.SneakyThrows;

import java.util.Map;

public class TemplateService {

    @SneakyThrows
    public static String parseUrl(String templateUrl, Map<String, String> variables) {
        var hb = new Handlebars();
        var tmpl = hb.compile(templateUrl);
        return tmpl.apply(variables);
    }
    @SneakyThrows
    public static String parse(String template, Map<String, String> variables) {
        var hb = new Handlebars();
        var tmpl = hb.compileInline(template);
        return tmpl.apply(variables);
    }
}
