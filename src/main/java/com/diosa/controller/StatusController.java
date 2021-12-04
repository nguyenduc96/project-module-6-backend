package com.diosa.controller;


import com.diosa.model.status.Status;
import com.diosa.service.status.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/status")
@CrossOrigin("*")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<Iterable<Status>> showStatus(){
        return new ResponseEntity<>(statusService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> showStatusById(@PathVariable Long id){
        Optional<Status> optionalStatus = statusService.findById(id);
        if(!optionalStatus.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(statusService.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status){
        return new ResponseEntity<>(statusService.save(status), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable Long id, @RequestBody Status status){
        Optional<Status> statusOptional = statusService.findById(id);
        if(!statusOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        status.setId(id);
        return new ResponseEntity<>(statusService.save(status), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Status> removeStatus(@PathVariable Long id){
        Optional<Status> statusOptional = statusService.findById(id);
        if(!statusOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        statusService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
