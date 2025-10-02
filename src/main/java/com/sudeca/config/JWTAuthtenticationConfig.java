package com.sudeca.config;


import com.sudeca.security.JwtConstans;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.sudeca.security.JwtConstans.*;

@EnableWebSecurity
@Configuration
public class JWTAuthtenticationConfig {
    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf((csrf) -> csrf
                        .disable())
                .authorizeHttpRequests( authz -> authz
                        .requestMatchers(HttpMethod.POST, JwtConstans.LOGIN_URL).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cajas-ahorro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cajas-ahorro/lista-pplan-contable").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cajas-ahorro/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/comprobante/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/comprobante/lista-por-filtro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/comprobante/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/comprobante/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/comprobante/10").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuario/getEmail").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuario/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuario/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/usuario/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cajas-ahorro/cierre").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/rol/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/cajas-ahorro/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/vpc/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        /*public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")  // Aplica a todos los endpoints
                    .allowedOriginPatterns("*")  // Permite cualquier origen
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);  // Cache de preflight por 1 hora
        }*/
        // Orígenes permitidos (ajustar para producción)
        /*config.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "https://tu-frontend.com"
        ));*/

        config.setAllowedOriginPatterns(List.of("*"));

        // Métodos y Headers
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept"
        ));

        // Headers expuestos (incluye Authorization para JWT)
        config.setExposedHeaders(List.of("Authorization"));

        // Cookies/credenciales
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    public String generarJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId(username)
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(SUPER_SECRET_KEY),  SignatureAlgorithm.HS512).compact();

        return "Bearer " + token;
    }

    public String extraerUsername(String token){
        Claims jwtToken = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey(SUPER_SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return jwtToken.getSubject();
    }

}
