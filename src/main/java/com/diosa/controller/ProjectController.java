package com.diosa.controller;

import com.diosa.model.project.Project;
import com.diosa.service.project.IProjectService;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @GetMapping
    public ResponseEntity<Iterable<Project>> getAll() {
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        return projectOptional.map(project -> new ResponseEntity<>(project, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Project> editProject(@RequestBody Project project) {
            return new ResponseEntity<>(projectService.save(project), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Project> delete(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (!projectOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectService.remove(id);
        return new ResponseEntity<>(projectOptional.get(), HttpStatus.OK);
    }
}
