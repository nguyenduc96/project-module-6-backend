package com.diosa.controller;

import com.diosa.model.label.Label;
import com.diosa.model.status.Status;
import com.diosa.model.task.Task;
import com.diosa.service.label.ILabelService;
import com.diosa.service.status.IStatusService;
import com.diosa.service.task.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    @Autowired
    private ILabelService labelService;

    @GetMapping
    public ResponseEntity<Iterable<Task>> findAll() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<Task>> findByStatusId(@PathVariable Long statusId,
                                                     @RequestParam(required = false) String title){
        List<Task> tasks;
        if(title != null) {
            tasks = taskService.findAllByStatusIdAndTitleContainsOrderByPositionAsc(statusId, title);
        } else {
            tasks = taskService.findAllByStatusIdOrderByPositionAsc(statusId);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
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
        Set<Label> labels = new HashSet<>();
        labels.addAll(taskOptional.get().getLabels());
        labels.addAll(task.getLabels());
        task.setLabels(labels);
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

    @GetMapping("/{id}/get-label")
    public ResponseEntity<List<Label>> getLabel(@PathVariable Long id) {
        List<Label> labels = labelService.findAllByTaskId(id);
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

}
