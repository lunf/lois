package com.lunf.delilah.controller.transformer;

import com.lunf.delilah.controller.request.UserRequest;
import com.lunf.delilah.service.model.LoginDTO;

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
