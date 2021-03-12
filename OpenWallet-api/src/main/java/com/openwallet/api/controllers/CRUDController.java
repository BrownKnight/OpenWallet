package com.openwallet.api.controllers;

import com.openwallet.api.data.models.BaseEntity;
import com.openwallet.api.data.services.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class CRUDController<TEntity extends BaseEntity, TService extends CRUDService<TEntity, ?>> {
    @Autowired
    protected TService service;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}
