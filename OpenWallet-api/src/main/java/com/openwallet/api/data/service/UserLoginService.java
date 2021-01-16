package com.openwallet.api.data.service;

import com.openwallet.api.data.models.UserLogin;
import com.openwallet.api.data.repositories.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Component
public class UserLoginService {
    @Autowired
    private UserLoginRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserLogin> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public UserLogin registerNewUser(
            UserLogin userLogin) throws EntityExistsException, MissingRequiredPropertiesException {
        if (repository.findByUsername(userLogin.getUsername())
                .isPresent()) {
            throw new EntityExistsException();
        }

        if (userLogin.getUser() == null) {
            throw new MissingRequiredPropertiesException();
        }

        userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
        return repository.save(userLogin);
    }
}
