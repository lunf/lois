package com.lunf.lois.service;

import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.LoginType;
import com.lunf.lois.service.model.UserDTO;

public interface UserService {

    void register(UserDTO requestUser, LoginType loginType) throws DelilahException;
}
