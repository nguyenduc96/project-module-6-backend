package com.diosa.controller;

import com.diosa.model.assign.Assign;
import com.diosa.model.board.BoardResponse;
import com.diosa.model.permission.BoardPermission;
import com.diosa.model.permission.Permission;
import com.diosa.model.status.StatusResponse;
import com.diosa.model.task.Task;
import com.diosa.model.user.User;
import com.diosa.service.assign.IAssignService;
import com.diosa.service.board.IBoardPermissionService;
import com.diosa.service.board.IBoardService;
import com.diosa.service.board.IPermissionService;
import com.diosa.service.task.ITaskService;
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

    @Autowired
    private IAssignService assignService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IBoardService boardService;

    @GetMapping("/get-all")
    public ResponseEntity<Iterable<Permission>> getAllPermissions() {
        return new ResponseEntity<>(permissionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/board/{boardId}")
    public ResponseEntity<BoardPermission> findByUserIdAndBoardId(@PathVariable Long userId, @PathVariable Long boardId) {
        return new ResponseEntity<>(boardPermissionService.findByUserIdAndBoardId(userId,boardId), HttpStatus.OK );
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

    @DeleteMapping("/{userId}/{boardId}")
    public ResponseEntity<BoardPermission> deleteBoardPermission(@PathVariable("userId") Long userId, @PathVariable("boardId") Long boardId) {
        Iterable<Assign> assigns = assignService.findAll();
        BoardResponse board = boardService.findBoardById(boardId);
        for (Assign assign : assigns) {
            for (StatusResponse stt: board.getStatuses()) {
                for (Task task: stt.getTasks()) {
                    if (assign.getTask().equals(task)) {
                        assignService.deleteAssign(userId, task.getId());
                    }
                }
            }
        }
        boardPermissionService.deleteByUserIdAndBoardId(userId, boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
