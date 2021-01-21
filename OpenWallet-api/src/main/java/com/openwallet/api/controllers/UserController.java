package com.openwallet.api.controllers;

import com.openwallet.api.data.models.User;
import com.openwallet.api.data.models.types.ChangePasswordRequest;
import com.openwallet.api.data.services.UserLoginService;
import com.openwallet.api.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends CRUDController<User, UserService> {
    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest body) {
        return ResponseEntity.ok(userLoginService.changeCurrentUsersPassword(body.getNewPassword()));
    }
}
