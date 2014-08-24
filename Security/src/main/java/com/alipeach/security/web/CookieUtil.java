package com.alipeach.security.web;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Chen Haoming
 */
public interface CookieUtil {

    default Cookie getCookie (@NotNull HttpServletRequest request, @NotNull String name) {
        Cookie[] cookies = request.getCookies ();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals (cookie.getName (), name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
