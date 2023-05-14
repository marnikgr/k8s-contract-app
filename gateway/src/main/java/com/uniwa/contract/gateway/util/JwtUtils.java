package com.uniwa.contract.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validHours}")
    private long validHours;

    public String generate(String username, Map<String, Object> claims) {

        LocalDateTime expiry = LocalDateTime.now().plusHours(validHours);
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public boolean isValid(String jwt) {
        boolean valid = false;

        try {
            if (StringUtils.isNotBlank(getSubjectFromToken(jwt))) {
                valid = true;
            }
        } catch (ExpiredJwtException e) {
            log.error(MessageFormat.format("Expired token: {0}", jwt), e);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error(MessageFormat.format("Unreadable token: {0}", jwt), e);
        } catch (SignatureException e) {
            log.error(MessageFormat.format("Unreadable signature: {0}", jwt), e);
        }
        return valid;
    }

    private String getSubjectFromToken(String jwt) {
        return getClaimFromToken(jwt, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String jwt) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(extractBearerToken(jwt))
                .getBody();
    }

    public static String extractBearerToken(String header) {

        return StringUtils.isNotBlank(header) && header.startsWith("Bearer ")
                ? header.substring(7) : null;

    }
}
