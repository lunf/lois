package com.lunf.lois.controller.transformer;

import com.lunf.lois.controller.request.UserRequest;
import com.lunf.lois.service.model.LoginDTO;

import java.util.Optional;

public class LoginRequestTransformer {

    public static Optional<UserRequest> transform(LoginDTO loginDTO) {
        if (loginDTO == null) {
            return Optional.empty();
        }


        UserRequest userRequest = UserRequest.builder()
                .id(loginDTO.getId().toString())
                .username(loginDTO.getUsername())
                .password(loginDTO.getPasswordHash()).build();

        return Optional.of(userRequest);
    }
}
