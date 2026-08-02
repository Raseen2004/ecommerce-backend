package dev.raseen2004.e_commerce_learning;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

public class JwtSecretGenerator {

    public static void main(String[] args) {

        String secret = Encoders.BASE64.encode(
                Jwts.SIG.HS256.key().build().getEncoded()
        );

        System.out.println(secret);
    }
}