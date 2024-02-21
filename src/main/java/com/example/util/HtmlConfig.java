package com.example.util;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlConfig {

    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }


    public static String unEscapeHtml(String html) {
        return StringEscapeUtils.unescapeHtml4(html);
    }

}
