package com.lunf.lois.controller.transformer;

import com.lunf.lois.controller.request.RegisterUserRequest;
import com.lunf.lois.service.model.UserDTO;

public class UserRequestTransformer {

    public static UserDTO transform(RegisterUserRequest userRequest) {

        if (userRequest == null) {
            return null;
        }

        UserDTO userDTO = UserDTO.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .build();

        return userDTO;
    }
}
