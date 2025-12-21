package utils.passwordUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    public static String hashPassword(String password,byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),salt,ITERATIONS,KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        String saltBase64=Base64.getEncoder().encodeToString(salt);
        return saltBase64 + ":"+Base64.getEncoder().encodeToString(skf.generateSecret(spec).getEncoded());
    }

    public static boolean verifyPassword(String passwordAttempt, String storedHash) throws Exception{
        String[] parts = storedHash.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        return hashPassword(passwordAttempt,salt).equals(storedHash);//better with MessageDigest.isEqual
    }
    private static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
}
