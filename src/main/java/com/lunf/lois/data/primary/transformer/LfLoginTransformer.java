package com.lunf.lois.data.primary.transformer;

import com.lunf.lois.service.model.LoginDTO;
import com.lunf.lois.service.model.LoginType;
import com.lunf.lois.data.primary.entity.LfLogin;

import java.util.*;

public class LfLoginTransformer {
    public static Optional<LoginDTO> transform(LfLogin lfLogin) {
        if (lfLogin == null) {
            return Optional.empty();
        }

        LoginDTO loginDTO = LoginDTO.builder()
                .id(lfLogin.getId())
                .passwordHash(lfLogin.getPasswordHash())
                .loginType(LoginType.parseValue(lfLogin.getLoginType()))
                .token(lfLogin.getToken())
                .username(lfLogin.getUsername()).build();


        return Optional.of(loginDTO);
    }

    public static Optional<LfLogin> transform(Optional<LoginDTO> loginDTO) {
        if (loginDTO == null || !loginDTO.isPresent()) {
            return Optional.empty();
        }

        return loginDTO.map(data -> {
            LfLogin lfLogin = new LfLogin();
            lfLogin.setToken(data.getToken());
            lfLogin.setPasswordHash(data.getPasswordHash());
            lfLogin.setLoginType(data.getLoginType().ordinal());
            lfLogin.setId(data.getId());
            lfLogin.setUsername(data.getUsername());

            return lfLogin;
        });

    }

    public static Collection<LoginDTO> transform(List<LfLogin> lfLoginCollection) {

        List<LoginDTO> loginDTOCollection = new ArrayList<>();
        if (lfLoginCollection == null || lfLoginCollection.isEmpty()) {
            return loginDTOCollection;
        }

        for (LfLogin lfLogin : lfLoginCollection) {
            Optional<LoginDTO> loginDTO = transform(lfLogin);

            if (loginDTO != null && loginDTO.isPresent()) {
                loginDTOCollection.add(loginDTO.get());
            }
        }
        return loginDTOCollection;
    }
}
