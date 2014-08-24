package com.alipeach.security.web;

import org.apache.commons.lang.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Chen Haoming
 *         <p>
 *         This filter should validate cookie,IP,port and Session ID at least .
 */
public class SecurityAccessFilter implements Filter {

    private CookieUtil cookieUtil = new CookieUtil () {

    };

    private String SecurityCookieKey = "alipeach.sid";

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession ();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie cookie = cookieUtil.getCookie (httpRequest, SecurityCookieKey);

        if (null == cookie || ! StringUtils.equals (cookie.getValue (), session.getId ()) || ! cookie.isHttpOnly ()) {
            sendUnauthorized (httpResponse, "Cookie not matched.");
        }

        Object ip = session.getAttribute ("user.login.ip");
        if (null == ip || ! StringUtils.equals (httpRequest.getRemoteAddr (), ip.toString ())) {
            sendUnauthorized (httpResponse, "IP not matched.");
        }

        Object port = session.getAttribute ("user.login.port");
        if ((null == port) || ! (port.equals (httpRequest.getRemotePort ()))) {
            sendUnauthorized (httpResponse, "Port not matched.");
        }

        if (httpResponse.getStatus () != HttpServletResponse.SC_OK) {
            return;
        }
        chain.doFilter (request, response);
    }

    private void sendUnauthorized (HttpServletResponse httpResponse, String msg) throws IOException {
        httpResponse.sendError (HttpServletResponse.SC_UNAUTHORIZED, msg);
    }

    @Override
    public void destroy () {

    }

    public void setCookieUtil (CookieUtil cookieUtil) {
        this.cookieUtil = cookieUtil;
    }

    public void setSecurityCookieKey (String securityCookieKey) {
        SecurityCookieKey = securityCookieKey;
    }
}
