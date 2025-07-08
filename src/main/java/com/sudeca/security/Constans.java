package com.sudeca.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import static com.sudeca.utils.ReadProp.getProperties;

public class Constans {

    // Spring Security
    public static final String LOGIN_URL = "/api/login";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
    public static final String SECRET_KEY = getProperties().getProperty("prop.secret.key");
    public static final long JW_TIME_TO_LIVE = Long.parseLong(getProperties().getProperty("prop.jwt.time.to.live")); // used to calculate expiration (claim = exp)
    //VALIDATIONS
    public static final String EMAIL_WITH_FORMAT = getProperties().getProperty("prop.email.with.format");
    public static final String EMAIL_EXISTS_BD = getProperties().getProperty("prop.email.exists.bd");
    public static final String USER_EXISTS_BD = getProperties().getProperty("prop.user.exists.bd");
    public static final String ROL_EXISTS_BD = getProperties().getProperty("prop.rol.exists.bd");
    public static final String COD_EMP_EXISTS_BD = getProperties().getProperty("prop.cod.empresa.exists.bd");
    public static final String RIF_EXISTS_BD = getProperties().getProperty("prop.rif.exists.bd");
    public static final String RAZON_SOCIAL_EXISTS_BD = getProperties().getProperty("prop.razon.social.exists.bd");
    public static final String ERROR_INSERT_STATUS = getProperties().getProperty("prop.error.insert.status");
    public static final String INSERT_STATUS = getProperties().getProperty("prop.insert.status");
    public static final String ROL_STATUS = getProperties().getProperty("prop.insert.rol.status");
    public static final String COMPANY_STATUS = getProperties().getProperty("prop.insert.company.status");
    public static final String ERROR_EXCEPTION_THROWN = getProperties().getProperty("prop.error.exception.thrown");
    public static final String USER_NOT_EXISTS_BD = getProperties().getProperty("prop.user.not.exists.bd");

    public static final String AUXI_EXISTS_BD = getProperties().getProperty("prop.auxi.exists.bd");
    public static final String AUXI_STATUS = getProperties().getProperty("prop.auxi.insert.status");
    public static final String AUXI_RIF_EXISTS_BD = getProperties().getProperty("prop.auxi.rif.exists.bd");
    public static final String AUXI_TYPE_STATUS = getProperties().getProperty("prop.auxi.type.insert.status");

    // Spring Security
    //public static final String LOGIN_URL = "/login";
    //public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    //public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final String ISSUER_INFO = "https://www.autentia.com/";
    public static final String SUPER_SECRET_KEY = "ZnJhc2VzbGFyZ2FzcGFyYWNvbG9jYXJjb21vY2xhdmVlbnVucHJvamVjdG9kZWVtZXBsb3BhcmFqd3Rjb25zcHJpbmdzZWN1cml0eQ==bWlwcnVlYmFkZWVqbXBsb3BhcmFiYXNlNjQ=";
    //public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

    public static Key getSigningKeyB64(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Key getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
