package com.codecool.movieorganizer.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtService {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:1800000}")
    private final long validityInMilliSeconds = 1800000;

    private final String rolesFieldName = "roles";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(rolesFieldName, roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliSeconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    String getTokenFromRequest(HttpServletRequest req) {
        if (req.getCookies() == null) return null;
        return Arrays.stream(req.getCookies())
            .filter(cookie -> cookie.getName().equals("JWT"))
            .findFirst().map(Cookie::getValue).orElse(null);
    }

    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("JWT invalid " + e);
        }
        return false;
    }

    /**
     * Parses the username and roles from the token. Since the token is signed we can be sure its valid information.
     * Note that it does not make a DB call to be super fast!
     * This could result in returning false data (e.g. the user was deleted, but their token has not expired yet)
     * To prevent errors because of this make sure to check the user in the database for more important calls!
     */
    Authentication parseUserFromTokenInfo(String token) throws UsernameNotFoundException {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String username = body.getSubject();
        @SuppressWarnings("unchecked")
        List<String> roles = ((List<String>) body.get(rolesFieldName));
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
            .collect(Collectors.toCollection(LinkedList::new));
        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }

    /**
     * Gets the cookie containing the JWT from the request, sets its max age to 0, so as
     * to make it expire at once, and adds it to the response.
     *
     * Additional attributes, which were originally set for this cookie, i.e. "Path" and "HttpOnly"
     * also need to be set, so as not to add a new cookie, but to affect the existing one.
     *
     * @param req HttpServletRequest containing the cookie with the token
     * @param res HttpServletResponse in which the cookie should be already expired
     */
    public void invalidateTokenCookie(HttpServletRequest req, HttpServletResponse res) {
        Cookie tokenCookie = Arrays.stream(req.getCookies())
            .filter(cookie -> "JWT".equals(cookie.getName())).findFirst()
            .orElseThrow(() -> new AuthenticationCredentialsNotFoundException(
                "Token cookie not present, invalid logout request.")
            );
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(0);
        res.addCookie(tokenCookie);
    }
}
