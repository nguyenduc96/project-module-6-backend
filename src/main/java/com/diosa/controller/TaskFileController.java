package com.diosa.controller;


import com.diosa.model.file.TaskFile;
import com.diosa.model.user.User;
import com.diosa.model.user.UserPrinciple;
import com.diosa.service.file.ITaskFileService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/task-files")
public class TaskFileController {

    @Autowired
    private ITaskFileService taskFileService;

    @Autowired
    private IUserService userService;


    @GetMapping
    public ResponseEntity<Iterable<TaskFile>> showTaskFile(){
        return new ResponseEntity<>(taskFileService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskFile>> showByTaskId(@PathVariable Long taskId){
        return new ResponseEntity<>(taskFileService.findAllByTaskId(taskId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskFile> addTaskFile(@RequestBody TaskFile taskFile, Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = new User();
        user.setId(userPrinciple.getId());
        taskFile.setUser(user);
        return new ResponseEntity<>(taskFileService.save(taskFile), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<TaskFile> deleteTaskFile(@PathVariable Long id) {
        Optional<TaskFile> taskFileOptional = taskFileService.findById(id);
        if(!taskFileOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskFileService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
