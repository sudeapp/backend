package com.sudeca.services;

import io.jsonwebtoken.Claims;

/**
 * * Author: Francisco Hernandez
 **/
public interface IManageJWT {
    String createJWT(String id, String issuer, String subject, long ttlMillis);
    Claims decodeJWT(String jwt);
}
