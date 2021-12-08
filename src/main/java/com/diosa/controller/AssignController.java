package com.diosa.controller;

import com.diosa.model.assign.Assign;
import com.diosa.service.assign.IAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping("/assign")
@RestController
public class AssignController {

    @Autowired
    private IAssignService assignService;

    @PostMapping("/add-member")
    public ResponseEntity<Assign> addMember(@RequestBody Assign assign){
        return new ResponseEntity<>(assignService.save(assign), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long id){
        assignService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
