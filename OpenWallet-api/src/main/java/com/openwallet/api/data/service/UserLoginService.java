package com.openwallet.api.data.service;

import com.openwallet.api.data.models.UserLogin;
import com.openwallet.api.data.repositories.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLoginService extends CRUDService<UserLogin, UserLoginRepository> {

}
