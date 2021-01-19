package com.openwallet.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openwallet.api.data.models.BaseEntity;
import com.openwallet.api.data.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CRUDController<TEntity extends BaseEntity, TService extends CRUDService<TEntity, ?>> {
    @Autowired
    protected TService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        long parsedId = Integer.parseInt(id, 10);
        return ResponseEntity.ok(service.findById(parsedId));
    }

    @PutMapping()
    public ResponseEntity<?> save(@RequestBody TEntity body) {
        TEntity result = service.save(body);
        return ResponseEntity.ok(result);
    }

    @PutMapping("batch")
    public ResponseEntity<?> save(@RequestBody Iterable<TEntity> body) {
        return ResponseEntity.ok(service.save(body));
    }
}
