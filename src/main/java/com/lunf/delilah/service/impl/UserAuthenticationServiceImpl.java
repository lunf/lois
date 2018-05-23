package com.lunf.delilah.service.impl;

import com.lunf.delilah.data.entity.LfLogin;
import com.lunf.delilah.data.mapper.LfLoginMapper;
import com.lunf.delilah.data.transformer.LfLoginTransformer;
import com.lunf.delilah.service.UserAuthenticationService;
import com.lunf.delilah.service.model.LoginDTO;
import com.lunf.delilah.utilities.PasswordHelper;
import com.lunf.delilah.utilities.RandomString;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final LfLoginMapper lfLoginMapper;

    @Override
    public Optional<String> login(String username, String password) {

        Optional<String> token = Optional.empty();
        Collection<LfLogin> lfLoginCollection = lfLoginMapper.findByUsername(username);

        if (lfLoginCollection == null) {
            return token;
        }

        Collection<LoginDTO> loginDTOCollection = LfLoginTransformer.transform(lfLoginCollection);

        for (LoginDTO loginDTO : loginDTOCollection) {

            switch (loginDTO.getLoginType()) {
                case STANDARD: {
                    boolean isValid = PasswordHelper.checkPassword(password, loginDTO.getPasswordHash());

                    if (isValid) {

                        if (loginDTO.getToken() == null || loginDTO.getToken().isEmpty()) {
                            String tokenValue = new RandomString.Builder().nextString();
                            token = Optional.of(tokenValue);

                            // Update database with new token
                            lfLoginMapper.updateTokenByUsernameAndLoginType(tokenValue, username, loginDTO.getLoginType().ordinal());
                        } else {
                            token = Optional.of(loginDTO.getToken());
                        }
                    }

                    break;
                }
                case API_CALL: {
                    if (password.equals(loginDTO.getToken())) {
                        token = Optional.of(password);
                    }

                    break;
                }
            }
        }

        return token;
    }

    @Override
    public Optional<LoginDTO> findByToken(String token) {
        LfLogin lfLogin = lfLoginMapper.findByToken(token);

        if (lfLogin == null) {
            return Optional.empty();
        }

        return LfLoginTransformer.transform(lfLogin);
    }

    @Override
    public void logout(LoginDTO loginDTO) {

        Optional<LfLogin> lfLogin = LfLoginTransformer.transform(loginDTO);

        lfLogin.ifPresent(data -> lfLoginMapper.deleteByToken(data.getToken()));
    }
}
