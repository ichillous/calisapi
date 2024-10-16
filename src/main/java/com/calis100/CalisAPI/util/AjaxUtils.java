package com.calis100.CalisAPI.util;

public class AjaxUtils {
    public static boolean isAjaxRequest(String requestedWith) {
        return "XMLHttpRequest".equals(requestedWith);
    }
}
