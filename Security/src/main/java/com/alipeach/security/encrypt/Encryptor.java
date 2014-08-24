package com.alipeach.security.encrypt;

/**
 * @author Chen Haoming
 */
public interface Encryptor {

    String decrypt (String ciphertext);

    String encrypt (String plaintext);
}
