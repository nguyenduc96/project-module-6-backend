package com.diosa.controller;

import com.diosa.model.status.Status;
import com.diosa.model.task.Task;
import com.diosa.service.status.IStatusService;
import com.diosa.service.task.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    @Autowired
    private IStatusService statusService;

    @GetMapping
    public ResponseEntity<Iterable<Task>> findAll() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<Task>> findByStatusId(@PathVariable Long statusId){
        return new ResponseEntity<>(taskService.findAllByStatusIdOrderByPositionAsc(statusId), HttpStatus.OK);
    }

    @PostMapping("/status/{statusId}")
    public ResponseEntity<Task> dropTask(@PathVariable Long statusId, @RequestBody Task task) {
        Optional<Status> status = statusService.findById(statusId);
        if(!status.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        task.setStatus(status.get());
        Task task1 = taskService.save(task);
        return new ResponseEntity<>(task1, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if(taskOptional.isPresent()) {
            return new ResponseEntity<>(taskService.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.save(task), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> editTask(@RequestBody Task task, @PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if(!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        task.setId(id);
        return new ResponseEntity<>(taskService.save(task), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<Task> taskOptional = taskService.findById(id);
        if(!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
