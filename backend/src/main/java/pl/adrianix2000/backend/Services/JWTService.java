package pl.adrianix2000.backend.Services;

import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import pl.adrianix2000.backend.Models.Entities.User;

@Service
public class JWTService {

    private KeyPairGenerator keysGenerator;
    private KeyPair keys;


    public JWTService() throws NoSuchAlgorithmException {

        keysGenerator = KeyPairGenerator.getInstance("RSA");
        keysGenerator.initialize(2048);

        keys = keysGenerator.generateKeyPair();
    }

    private Date calculateExpirationDate(int minutes) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }


    public String generateToken(User user) {

        Date expirationDate = calculateExpirationDate(1);

        Builder tokenBuilder = JWT.create()
                .withClaim("firstName", user.getFirst_name())
                .withClaim("lastName", user.getLast_name())
                .withClaim("email", user.getEmail())
                .withExpiresAt(expirationDate)
                .withIssuedAt(Date.from(Instant.now()));

        return  tokenBuilder.sign(Algorithm.RSA256(((RSAPublicKey) keys.getPublic()),
                ((RSAPrivateKey) keys.getPrivate())));
    }
}
