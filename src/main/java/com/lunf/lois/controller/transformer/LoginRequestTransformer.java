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

    public static Optional<LoginDTO> transform(UserRequest userRequest) {

        if(userRequest == null) {
            return Optional.empty();
        }

        LoginDTO loginDTO = LoginDTO.builder()
                .id(Long.valueOf(userRequest.getId()))
                .username(userRequest.getUsername())
                .passwordHash(userRequest.getPassword()).build();

        return Optional.of(loginDTO);
    }
}
