package com.sudeca.impl;

import com.sudeca.services.IManageJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

import static com.sudeca.security.Constans.SECRET_KEY;

@Service
public class CreateJWTImpl implements IManageJWT {
    private static final Logger logger = LogManager.getLogger();

    //private String secretKey = getProperties().getProperty("prop.secret.key");

    @Override
    public String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //El algoritmo de firma JWT que usaremos para firmar el token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Se firma el JWT con nuestro secreto ApiKey
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Se establece los reclamos de JWT
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //Si se ha especificado agreguemos el vencimiento
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Compila el JWT y lo serializa en una cadena compacta y segura para URL
        return builder.compact();
    }

    @Override
	  public Claims decodeJWT(String jwt) {
		  //Esta línea generará una excepción si no es un JWS firmado (como se esperaba)
		  JwtParserBuilder jwtParserBuilder = Jwts.parser()
		  .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY));
		  Claims claims = jwtParserBuilder.build().parseClaimsJwt(jwt).getBody();
		  return claims; 
	  }	 

}
