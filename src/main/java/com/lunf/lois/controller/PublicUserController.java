package com.lunf.lois.controller;

import com.lunf.lois.controller.request.RegisterUserRequest;
import com.lunf.lois.controller.transformer.UserRequestTransformer;
import com.lunf.lois.service.UserAuthenticationService;
import com.lunf.lois.service.UserService;
import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.LoginType;
import com.lunf.lois.service.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;


@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class PublicUserController {

    @NonNull
    UserAuthenticationService authentication;

    @NonNull
    UserService userService;


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> loginUser(@RequestParam("username") final String username,
                                       @RequestParam("password") final String password) {


        String token = authentication.login(username, password).get();

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterUserRequest userRequest) {

        if (!validateParams(userRequest.getUsername(), userRequest.getPassword(),
                userRequest.getFirstName(), userRequest.getLastName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorCode.FAIL_VALIDATION);
        }

        UserDTO userDTO = UserRequestTransformer.transform(userRequest);

        try {
            userService.register(userDTO, LoginType.STANDARD);
        } catch (DelilahException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorCode());
        }

        String token = authentication.login(userRequest.getUsername(), userRequest.getPassword())
                .get();

        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    private boolean validateParams(String... args) {

        for(String value: args) {
            if (value == null || value.trim().length() < 1) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

}
