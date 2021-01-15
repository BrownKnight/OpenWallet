package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.UserLogin;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginRepository extends CrudRepository<UserLogin, Long> {
    UserLogin findById(long id);
}
