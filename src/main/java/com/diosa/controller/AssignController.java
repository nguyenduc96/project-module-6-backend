package com.diosa.controller;

import com.diosa.model.assign.Assign;
import com.diosa.model.user.User;
import com.diosa.service.assign.IAssignService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RequestMapping("/assign")
@RestController
public class AssignController {

    @Autowired
    private IAssignService assignService;

    @Autowired
    private IUserService userService;

    @PostMapping("/add-member")
    public ResponseEntity<Assign> addMember(@RequestBody Assign assign){
        Optional<User> user = userService.findByEmail(assign.getUser().getEmail());
        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        assign.setUser(user.get());
        List<User> users = userService.findAllByTaskId(assign.getTask().getId());
        for(User u : users){
            if (u.getEmail().equals(user.get().getEmail())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(assignService.save(assign), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long id){
        assignService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/get-members")
    public ResponseEntity<List<User>> getMembers(@PathVariable("id") Long id){
        List<User> users = userService.findAllByTaskId(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
