package com.alipeach.security.encrypt;

import org.jetbrains.annotations.NotNull;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chen Haoming
 *         <p>
 *         It's NOT thread-safe.
 */
public class Md5Encryptors implements IrreversibleEncryptors {

    private MessageDigest messageDigest;

    private String charset = "iso-8859-1";

    private static final String SALT = "fgshgsdHGhdg4%$4F$R$";

    public Md5Encryptors () {
        try {
            messageDigest = MessageDigest.getInstance ("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException (e);
        }
    }

    @Override
    public String encrypt (String plaintext) {

        try {
            messageDigest.reset ();
            messageDigest.update ((plaintext + SALT).getBytes (charset));
            byte[] result = messageDigest.digest ();

            StringBuilder stringBuilder = new StringBuilder ();

            for (byte b : result) {
                stringBuilder.append (String.format ("%02X", b));
            }

            return stringBuilder.toString ();
        } catch (Throwable e) {
            throw new RuntimeException (e);
        }
    }

    public void setCharset (@NotNull String charset) {
        this.charset = charset;
    }

    public static class Md5EncryptFilter implements Filter {

        @Override
        public void init (FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            chain.doFilter (new Md5SaltedHtpServletRequestWrapper ((HttpServletRequest) request), response);
        }

        @Override
        public void destroy () {

        }
    }

    /**
     * @author Chen Haoming
     */
    static class Md5SaltedHtpServletRequestWrapper extends HttpServletRequestWrapper {

        private static final String J_PASSWORD = "j_password";

        private HttpServletRequest request;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request
         *
         * @throws IllegalArgumentException if the request is null
         */
        public Md5SaltedHtpServletRequestWrapper (HttpServletRequest request) {
            super (request);
            this.request = request;
        }

        @Override
        public String getParameter (String name) {
            String parameter = request.getParameter (name);
            return (J_PASSWORD.equals (name) && null != parameter) ? parameter + SALT : parameter;
        }

        @Override
        public Map<String, String[]> getParameterMap () {
            Map<String, String[]> map = request.getParameterMap ();
            if (map.containsKey (J_PASSWORD) && null != map.get (J_PASSWORD)) {
                Map<String, String[]> newMap = new HashMap<> (map);
                newMap.put (J_PASSWORD, copyStrings (map.get (J_PASSWORD)));
                return Collections.unmodifiableMap (newMap);
            }
            return map;
        }

        @Override
        public String[] getParameterValues (String name) {
            String[] passwords = request.getParameterValues (name);

            if (J_PASSWORD.equals (name)) {
                if (null != passwords && passwords.length > 0) {
                    return copyStrings (passwords);
                }
            }

            return passwords;
        }

        private String[] copyStrings (String[] passwords) {
            String[] newPasswords = new String[passwords.length];
            for (int i = 0; i < passwords.length; ++ i) {
                newPasswords[i] = (null == passwords[i]) ? null : passwords[i] + SALT;
            }
            return newPasswords;
        }
    }
}
