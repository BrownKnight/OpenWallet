package com.openwallet.api.data.service;

import com.openwallet.api.data.models.User;
import com.openwallet.api.data.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService extends CRUDService<User, UserRepository> {

}
