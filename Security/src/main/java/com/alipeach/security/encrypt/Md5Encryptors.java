package com.alipeach.security.encrypt;

import org.jetbrains.annotations.NotNull;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Chen Haoming
 *         <p>
 *         It's NOT thread-safe.
 */
public class Md5Encryptors implements IrreversibleEncryptors {

    private MessageDigest messageDigest;

    private String charset = "iso-8859-1";

    static String SALT = "fgshgsdHGhdg4%$4F$R$";

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

}
