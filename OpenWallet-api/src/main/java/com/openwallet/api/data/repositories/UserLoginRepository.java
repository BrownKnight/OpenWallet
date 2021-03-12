package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.UserLogin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserLoginRepository extends EntityRepository<UserLogin> {
    Optional<UserLogin> findByUsername(String username);
}
