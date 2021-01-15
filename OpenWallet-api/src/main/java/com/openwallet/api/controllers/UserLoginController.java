package com.openwallet.api.controllers;

import com.openwallet.api.data.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-login")
public class UserLoginController extends CRUDController<UserLoginService> {

}
