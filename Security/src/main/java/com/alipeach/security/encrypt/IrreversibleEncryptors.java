package com.alipeach.security.encrypt;

/**
 * @author Chen Haoming
 */
@FunctionalInterface
public interface IrreversibleEncryptors {

    String encrypt (String plaintext);

}
