package com.sudeca.config;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.sudeca.security.Constans.*;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private static final Logger logger = LogManager.getLogger();

	private Claims setSigningKey(HttpServletRequest request) {
		String jwtToken = request.
				getHeader(HEADER_AUTHORIZACION_KEY).
				replace(TOKEN_BEARER_PREFIX, "");
		logger.info("jwtToken: " +jwtToken);
		return Jwts.parser()
				.setSigningKey(getSigningKey(SUPER_SECRET_KEY))
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
	}

	private void setAuthentication(Claims claims) {

		List<String> authorities = (List<String>) claims.get("authorities");
		logger.info("authorities: " +authorities);
		UsernamePasswordAuthenticationToken auth =
				new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		logger.info("auth: " +auth);
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	private boolean isJWTValid(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER_AUTHORIZACION_KEY);
		logger.info("isJWTValid / authenticationHeader: " +authenticationHeader);
		if (authenticationHeader == null || !authenticationHeader.startsWith(TOKEN_BEARER_PREFIX)) {
			logger.info("authenticationHeader False");
			return false;
		}
		return true;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			if (isJWTValid(request, response)) {
				Claims claims = setSigningKey(request);
				logger.info("claims: " +claims);
				logger.info("claims.get(authorities): " +claims.get("authorities"));
				if (claims.get("authorities") != null) {
					setAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}

}
