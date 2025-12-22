package utils.passwords;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    public static byte[] hashPassword(String password,byte[] salt)  {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(String passwordAttempt, String storedHash) throws Exception{
        String[] parts = storedHash.split(":");
        byte[] salt = base64ToByte(parts[0]);
        byte[] hash = base64ToByte(parts[1]);
        return MessageDigest.isEqual(hashPassword(passwordAttempt,salt),hash);
    }
    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
    public static String byteToBase64(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
    private static byte[] base64ToByte(String string){
        return Base64.getDecoder().decode(string);
    }
}
