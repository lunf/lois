package com.lunf.lois.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping(method = RequestMethod.POST, value = "authenticate")
    public ResponseEntity<Void> loginUser() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "create")
    public ResponseEntity<Void> createUser() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "get/{userId}")
    public ResponseEntity<Void> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
