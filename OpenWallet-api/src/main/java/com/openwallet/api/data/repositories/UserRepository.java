package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
}
