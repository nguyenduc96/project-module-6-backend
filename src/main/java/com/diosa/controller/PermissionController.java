package com.diosa.controller;

import com.diosa.model.permission.BoardPermission;
import com.diosa.model.permission.Permission;
import com.diosa.model.user.User;
import com.diosa.service.board.IBoardPermissionService;
import com.diosa.service.board.IPermissionService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IBoardPermissionService boardPermissionService;

    @Autowired
    private IUserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<Iterable<Permission>> getAllPermissions() {
        return new ResponseEntity<>(permissionService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add-board-permission")
    public ResponseEntity<BoardPermission> addBoardPermission(@RequestBody BoardPermission boardPermission) {
        Optional<User> user = userService.findByEmail(boardPermission.getUser().getEmail());
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boardPermission.setUser(user.get());
        List<User> users = userService.findAllByBoardId(boardPermission.getBoard().getId());
        for (User u : users) {
            if (u.getEmail().equals(boardPermission.getUser().getEmail())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(boardPermissionService.save(boardPermission), HttpStatus.OK);
    }
}
