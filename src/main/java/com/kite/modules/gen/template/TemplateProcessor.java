package com.kite.modules.gen.template;

import freemarker.template.Configuration;

import java.io.StringWriter;
import java.util.Map;

public class TemplateProcessor {
    private static Configuration freemarkerConfig;

    static {
        freemarkerConfig = new Configuration();
        freemarkerConfig.setClassForTemplateLoading(TemplateProcessor.class, "/");
    }

    public TemplateProcessor() {
    }

    public static String process(String tplName, String encoding, Map<String, Object> paras) {
        try {
            StringWriter sWriter = new StringWriter();
            freemarkerConfig.getTemplate(tplName, encoding).process(paras, sWriter);
            return sWriter.toString();
        } catch (Exception var4) {
            var4.printStackTrace();
            return var4.toString();
        }
    }

    public final String process(String tplName, Map<String, Object> paras) {
        return process(tplName, "utf-8", paras);
    }
}
