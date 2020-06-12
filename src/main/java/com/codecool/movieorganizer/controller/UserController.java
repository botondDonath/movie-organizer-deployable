package com.codecool.movieorganizer.controller;

import com.codecool.movieorganizer.service.MovieAppUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


    @GetMapping("/whoisin")
    public MovieAppUserDTO getUser(Principal principal){
        String username = null;

        if(principal != null){
            username = principal.getName();
        }

        log.info(username + " is LOGGED IN");

        return new MovieAppUserDTO(username);
    }


}
