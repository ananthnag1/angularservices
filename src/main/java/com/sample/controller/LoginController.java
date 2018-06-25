package com.sample.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.service.ILoginService;

@RestController
@RequestMapping(value = "/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    @Autowired
    ILoginService loginService;

    @RequestMapping(value = "/validateUser", method = GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<String> validateUser(@RequestParam("userName") String userName,
            @RequestParam("userPassword") String userPassword) throws IOException {
        return new ResponseEntity<>(loginService.validateUser(userName, userPassword), HttpStatus.OK);
    }
}