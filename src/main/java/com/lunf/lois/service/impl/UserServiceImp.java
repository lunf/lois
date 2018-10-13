package com.lunf.lois.service.impl;

import com.lunf.lois.data.primary.entity.LfLogin;
import com.lunf.lois.data.primary.entity.LfUser;
import com.lunf.lois.data.primary.mapper.LfLoginMapper;
import com.lunf.lois.data.primary.mapper.LfUserMapper;
import com.lunf.lois.data.primary.transformer.LfUserTransformer;
import com.lunf.lois.service.UserService;
import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.LoginDTO;
import com.lunf.lois.service.model.LoginType;
import com.lunf.lois.service.model.UserDTO;
import com.lunf.lois.utilities.PasswordHelper;
import com.lunf.lois.utilities.RandomTokenGenerator;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class UserServiceImp implements UserService {

    @NonNull
    LfUserMapper lfUserMapper;

    @NonNull
    LfLoginMapper lfLoginMapper;

    @Override
    public void register(UserDTO requestUser, LoginType loginType) throws DelilahException {

        boolean isExist = lfUserMapper.checkUserExists(requestUser.getPassword());

        if (isExist) {
            throw new DelilahException(ErrorCode.DUPLICATE_USER_NAME);
        }

        switch (loginType) {
            case STANDARD: {
                LfUser lfUser = LfUserTransformer.transform(requestUser);
                lfUserMapper.insert(lfUser);

                LfLogin lfLogin = new LfLogin();
                lfLogin.setLoginType(LoginType.STANDARD.ordinal());
                lfLogin.setUsername(requestUser.getUsername());
                lfLogin.setUser(lfUser);
                String hashPassword = PasswordHelper.hashPassword(requestUser.getPassword());
                lfLogin.setPasswordHash(hashPassword);
                String token = new RandomTokenGenerator().nextString();
                lfLogin.setToken(token);

                lfLoginMapper.insert(lfLogin);

                break;
            }
            case API_CALL: {
                break;
            }
        }
    }
}
