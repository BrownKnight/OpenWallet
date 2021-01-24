package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findById(long id);
}
