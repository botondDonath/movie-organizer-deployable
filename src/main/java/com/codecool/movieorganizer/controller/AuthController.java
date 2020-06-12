package com.codecool.movieorganizer.controller;

import com.codecool.movieorganizer.model.LoginResponseDto;
import com.codecool.movieorganizer.model.MovieAppUser;
import com.codecool.movieorganizer.model.UserCredentials;
import com.codecool.movieorganizer.security.JwtService;
import com.codecool.movieorganizer.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final Logger logger;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserService userService,
                          @Qualifier("authControllerLogger") Logger logger)
    {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.logger = logger;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserCredentials userCredentials) {
        try {
            String username = userCredentials.getUsername();
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, userCredentials.getPassword())
            );
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            String token = jwtService.createToken(username, roles);
            HttpHeaders headers = userService.constructTokenCookieHeader(token);
            return ResponseEntity.ok().headers(headers).body(new LoginResponseDto(username, null));
        } catch (AuthenticationException e) {
            return ResponseEntity.ok().body(new LoginResponseDto(null, "Invalid Username or Password"));
//            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        logger.info("JWT cookie: " + Arrays.stream(req.getCookies())
            .filter(c -> "JWT".equals(c.getName())).findFirst().map(Cookie::getValue).orElse(null)
        );
        jwtService.invalidateTokenCookie(req, res);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(@RequestBody UserCredentials userCredentials) {
        MovieAppUser user = userService.register(userCredentials);
        if (user == null) return ResponseEntity.ok(new LoginResponseDto(null, "Username already exists"));

        String token = jwtService.createToken(user.getUsername(),user.getRoles());
        HttpHeaders headers = userService.constructTokenCookieHeader(token);
        LoginResponseDto dto = new LoginResponseDto(user.getUsername());
        return ResponseEntity.ok().headers(headers).body(dto);
    }
}