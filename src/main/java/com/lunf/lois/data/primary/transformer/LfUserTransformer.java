package com.lunf.lois.data.primary.transformer;

import com.lunf.lois.data.primary.entity.LfUser;
import com.lunf.lois.service.model.UserDTO;

public class LfUserTransformer {

    public static LfUser transform(UserDTO userDTO) {
        LfUser lfUser = new LfUser();
        lfUser.setFirstName(userDTO.getFirstName());
        lfUser.setLastName(userDTO.getLastName());
        lfUser.setUsername(userDTO.getUsername());

        return lfUser;
    }
}
