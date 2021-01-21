package com.openwallet.api.data.services;

import com.openwallet.api.data.models.User;
import com.openwallet.api.data.models.UserLogin;
import com.openwallet.api.data.models.responses.SuccessResponse;
import com.openwallet.api.data.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserService extends CRUDService<User, UserRepository> {
}
