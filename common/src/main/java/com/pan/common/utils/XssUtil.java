package com.pan.common.utils;

public class XssUtil {

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * @param value
     * @return
     */
    public static String xssEncode(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
        value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
        value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
        value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
        value = value.replaceAll(">", "＞");
        value = value.replaceAll("<", "＜");
        value = value.replaceAll("&gt", "＞");
        value = value.replaceAll("&amp", "＆");
        value = value.replaceAll("&gt", "＞");
        value = value.replaceAll("&lt", "＜");
        value = value.replaceAll("&quot", "“");
        value = value.replaceAll("&apos", "‘");
        value = value.replaceAll("&", "＆");

        return value;
    }

}
