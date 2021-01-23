package com.openwallet.api.controllers;

import com.openwallet.api.configuration.JwtTokenUtil;
import com.openwallet.api.data.models.UserLogin;
import com.openwallet.api.data.models.responses.ErrorResponse;
import com.openwallet.api.data.models.responses.LoginResponse;
import com.openwallet.api.data.models.types.AuthRequest;
import com.openwallet.api.data.services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping("/api/login")
public class UserLoginController extends BaseController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserLoginService userLoginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserLogin user = (UserLogin) authenticate.getPrincipal();

        String token = jwtTokenUtil.generateAccessToken(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserLogin userLogin) {
        try {
            UserLogin body = userLoginService.registerNewUser(userLogin);
            return ResponseEntity.ok(body);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("User already exists"));
        } catch (MissingRequiredPropertiesException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Properties missing in request body. Likely missing User information"));
        }
    }

}
