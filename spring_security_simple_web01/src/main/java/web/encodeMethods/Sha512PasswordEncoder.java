package web.encodeMethods;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Sha512PasswordEncoder implements PasswordEncoder {


    @Override
    public String encode(CharSequence rawPassword) {


        return hashWithSHA512(rawPassword.toString());
    }

    private String hashWithSHA512(String input) {


        StringBuilder result = new StringBuilder();
        try{


            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digested = md.digest(input.getBytes());
            for (byte b : digested) {


                result.append(Integer.toHexString(0xFF & b));
            }
        }catch (NoSuchAlgorithmException e){


            throw new RuntimeException("Bad algorithm");
        }
        return result.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {


        String hashPassword = encode(rawPassword);
        return encodedPassword.equals(hashPassword);
    }
}
