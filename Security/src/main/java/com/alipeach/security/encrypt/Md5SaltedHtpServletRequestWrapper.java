package com.alipeach.security.encrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chen Haoming
 */
class Md5SaltedHtpServletRequestWrapper extends HttpServletRequestWrapper {

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
        String password = request.getParameter (name);
        return (J_PASSWORD.equals (name) && null != password) ? password + Md5Encryptors.SALT : password;
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
            newPasswords[i] = (null == passwords[i]) ? null : passwords[i] + Md5Encryptors.SALT;
        }
        return newPasswords;
    }
}
