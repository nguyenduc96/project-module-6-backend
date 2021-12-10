package com.diosa.service.board;

import com.diosa.model.permission.Permission;
import com.diosa.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionService implements IPermissionService {
    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public Iterable<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void remove(Long id) {
        permissionRepository.deleteById(id);
    }
}
