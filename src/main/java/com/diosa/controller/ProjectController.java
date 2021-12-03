package com.diosa.controller;

import com.diosa.model.project.Project;
import com.diosa.service.project.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @GetMapping
    public ResponseEntity<Iterable<Project>> getAll() {
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Project> getById(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        return projectOptional.map(project -> new ResponseEntity<>(project, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project) {
            return new ResponseEntity<>(projectService.save(project), HttpStatus.OK);
    }

    @PutMapping("/id")
    public ResponseEntity<Project> edit(@PathVariable Long id, @RequestBody Project project) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (!projectOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        project.setId(id);
        projectService.save(project);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
    @DeleteMapping("/id")
    public ResponseEntity<Project> delete(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (!projectOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectService.remove(id);
        return new ResponseEntity<>(projectOptional.get(), HttpStatus.OK);
    }
}
