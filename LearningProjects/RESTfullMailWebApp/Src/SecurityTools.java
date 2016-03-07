
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class SecurityTools {

    private SecurityTools() {
    }

    public static byte[] returnSecureSalt() {
        //generate random salt
        SecureRandom random = new SecureRandom();
        byte[] saltt = new byte[24];
        //this randomizes all the bytes in the salt array
        random.nextBytes(saltt);
        return saltt;
    }

    public static String passWordToHashWord(char[] password, byte[] salt) {
        byte[] hashh = null;
        try {
            //create PBE key spec params are password,salt,iterations,salt size * 8
            PBEKeySpec spec = new PBEKeySpec(password, salt, 64000, 192);
            //create secret key factory
            SecretKeyFactory sk = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            //get hash from sk and spec
            hashh = sk.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecurityTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(SecurityTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encode(hashh);
    }

    public static String encode(byte[] x) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(x);
    }

    public static byte[] decode(String x) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(x);
    }
}
