package com.lunf.delilah.data.transformer;

import com.lunf.delilah.data.entity.LfLogin;
import com.lunf.delilah.service.model.LoginDTO;
import com.lunf.delilah.service.model.LoginType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class LfLoginTransformer {
    public static Optional<LoginDTO> transform(LfLogin lfLogin) {
        if (lfLogin == null) {
            return Optional.empty();
        }

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(lfLogin.getId());
        loginDTO.setPasswordHash(lfLogin.getPasswordHash());
        loginDTO.setToken(lfLogin.getToken());
        loginDTO.setUsername(lfLogin.getUsername());

        loginDTO.setLoginType(LoginType.parseValue(lfLogin.getLoginType()));


        return Optional.of(loginDTO);
    }

    public static Optional<LfLogin> transform(LoginDTO loginDTO) {
        if (loginDTO == null) {
            return Optional.empty();
        }

        LfLogin lfLogin = new LfLogin();
        lfLogin.setToken(loginDTO.getToken());
        lfLogin.setPasswordHash(loginDTO.getPasswordHash());
        lfLogin.setLoginType(loginDTO.getLoginType().ordinal());
        lfLogin.setId(loginDTO.getId());
        lfLogin.setUsername(loginDTO.getUsername());

        //TODO: we need to map LfUser in someway

        return Optional.of(lfLogin);
    }

    public static Collection<LoginDTO> transform(Collection<LfLogin> lfLoginCollection) {

        Collection<LoginDTO> loginDTOCollection = Arrays.asList();
        if (lfLoginCollection == null || lfLoginCollection.isEmpty()) {
            return loginDTOCollection;
        }

        for (LfLogin lfLogin : lfLoginCollection) {
            Optional<LoginDTO> loginDTO = transform(lfLogin);

            if (loginDTO != null) {
                loginDTOCollection.add(loginDTO.get());
            }
        }
        return loginDTOCollection;
    }
}
