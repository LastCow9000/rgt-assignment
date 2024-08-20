package com.rgt.assignment.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
    static final int TWENTY_FOUR_HOURS = 60 * 60 * 24;

    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(TWENTY_FOUR_HOURS);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
