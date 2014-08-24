package com.alipeach.security.web.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Chen Haoming
 */
public class JEESessionFilter implements Filter {

    static ThreadLocal<HttpSession> threadLocal;

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {
        threadLocal = new ThreadLocal<> ();
    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        threadLocal.set (((HttpServletRequest) request).getSession ());
        try {
            chain.doFilter (request, response);
        } finally {
            threadLocal.remove ();
        }
    }

    @Override
    public void destroy () {
        threadLocal = null;
    }
}
