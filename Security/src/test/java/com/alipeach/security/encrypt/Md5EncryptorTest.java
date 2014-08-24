package com.alipeach.security.encrypt;


import org.junit.Assert;
import org.junit.Test;

/**
 * @author Chen Haoming
 */
public class Md5EncryptorTest {

    @Test
    public void testEncrypt () {
        Md5Encryptors md5Encryptor = new Md5Encryptors ();
        String alipeach = md5Encryptor.encrypt ("Alipeach");
        Assert.assertEquals("D2BC629C0BB359B8779A1FACE8362582", alipeach);
    }
}
