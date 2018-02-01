package main.java.application.web.security.jwt;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import main.java.application.web.exceptions.AuthorizationException;

public abstract class PasswordUtils {
    public static String encryptPassword(final String password) throws AuthorizationException {
        try {
            final  MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            final StringBuilder sb = new StringBuilder();
            for (final byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new AuthorizationException(ex.getMessage());
        }
    }
}
