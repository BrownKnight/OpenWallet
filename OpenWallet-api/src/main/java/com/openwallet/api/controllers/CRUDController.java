package com.openwallet.api.controllers;

import com.openwallet.api.data.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class CRUDController<TService extends UserLoginService> {
    @Autowired
    protected TService service;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
