package com.openwallet.api.controllers;

import com.openwallet.api.data.models.User;
import com.openwallet.api.data.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends CRUDController<User, UserService> {

}
