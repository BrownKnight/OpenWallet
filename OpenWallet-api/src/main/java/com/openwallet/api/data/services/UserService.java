package com.openwallet.api.data.services;

import com.openwallet.api.data.models.UserInfo;
import com.openwallet.api.data.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService extends CRUDService<UserInfo, UserRepository> {
}
