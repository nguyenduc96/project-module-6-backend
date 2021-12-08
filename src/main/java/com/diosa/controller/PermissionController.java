package com.diosa.controller;

import com.diosa.model.permission.Permission;
import com.diosa.service.board.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/get-all")
    public ResponseEntity<Iterable<Permission>> getAllPermissions() {
        return new ResponseEntity<>(permissionService.findAll(), HttpStatus.OK);
    }

}
