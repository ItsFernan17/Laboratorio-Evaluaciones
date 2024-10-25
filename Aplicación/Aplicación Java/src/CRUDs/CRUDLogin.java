/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRUDs;

import java.security.MessageDigest;

/**
 *
 * @author ferna
 */
public class CRUDLogin {

    public static String encryptPassword(String password) {
        return sha1(password);
    }

    public static String sha1(String message) {
        try {
            return encrypt(message, "SHA1");
        } catch (Exception e) {
            return null;
        }
    }

    public static String md5(String message) {
        return encrypt(message, "MD5");
    }

    private static String encrypt(String message, String type) {
        try {
            MessageDigest md;
            byte[] buffer, digest;
            StringBuilder hash = new StringBuilder();
            try {
                buffer = message.getBytes("UTF-8");
                md = MessageDigest.getInstance(type);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            md.update(buffer);
            digest = md.digest();
            for (byte b : digest) {
                hash.append(String.format("%02x", b & 0xff));
            }
            return hash.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
