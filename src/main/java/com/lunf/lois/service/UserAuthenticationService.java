package com.lunf.lois.service;

import com.lunf.lois.service.model.LoginDTO;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<String> login(String username, String password);

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<LoginDTO> findByToken(String token);

    /**
     * Logs out the given input {@code loginDTO}.
     *
     * @param loginDTO the user to logout
     */
    void logout(LoginDTO loginDTO);
}
