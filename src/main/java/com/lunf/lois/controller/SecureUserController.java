package com.lunf.lois.controller;


import com.lunf.lois.controller.request.UserRequest;
import com.lunf.lois.controller.transformer.LoginRequestTransformer;
import com.lunf.lois.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;


@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class SecureUserController {

    private static final Logger logger = LoggerFactory.getLogger(SecureUserController.class);

    @NonNull
    UserAuthenticationService authentication;

    @GetMapping("/current")
    UserRequest getCurrent(@AuthenticationPrincipal final UserRequest user) {
        return user;
    }

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal final UserRequest user) {
        authentication.logout(LoginRequestTransformer.transform(user));
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "get/{userId}")
    public ResponseEntity<Void> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
